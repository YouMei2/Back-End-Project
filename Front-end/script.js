const API_URL = 'http://localhost:8080/tasks';
const WHEEL_API = 'http://localhost:8080/wheel';

// --- СИСТЕМНЫЕ ФУНКЦИИ ---
function checkAuth() {
    const userId = localStorage.getItem('userId');
    const userName = localStorage.getItem('userName');
    const guestBtns = document.getElementById('guest-btns');
    const userProfile = document.getElementById('user-profile');
    const welcomeText = document.getElementById('welcome-text');

    if (userId && userId !== "null") {
        if (guestBtns) guestBtns.style.display = 'none';
        if (userProfile) {
            userProfile.style.display = 'flex';
            welcomeText.textContent = `Hello, ${userName || 'User'}!`;
        }
    } else {
        if (guestBtns) guestBtns.style.display = 'flex';
        if (userProfile) userProfile.style.display = 'none';
    }
}

function logout() {
    localStorage.clear();
    window.location.reload();
}

// --- ЛОГИКА ЗАДАЧ ---
async function loadTasks() {
    const userId = localStorage.getItem('userId');
    const list = document.getElementById('taskList');
    if (!list || !userId) return;

    try {
        const response = await fetch(`${API_URL}?userId=${userId}`);
        if (!response.ok) return;
        const tasks = await response.json();
        list.innerHTML = '';

        tasks.forEach(task => {
            const li = document.createElement('li');
            const isDone = task.done || task.isDone;
            if (isDone) li.classList.add('completed-task');

            // Создаем объект с текстом для бейджиков, чтобы красиво отображать ключи
            const priorityLabels = {
                'low': '🌱 Простая',
                'medium': '⚡ Средняя',
                'high': '🔥 Важная',
                'goal': '🏆 Цель'
            };

            // Добавляем HTML бейджика перед текстом задачи
            li.innerHTML = `
                <div class="task-content">
                    <input type="checkbox" ${isDone ? 'checked' : ''} 
                           onchange="toggleTask(${task.id}, ${isDone})">
                    <div class="task-text ${isDone ? 'completed-text' : ''}">
                        <span class="priority-badge prio-${task.priority || 'low'}">
                            ${priorityLabels[task.priority] || '🌱 Простая'}
                        </span><br>
                        <b>${task.name || 'БЕЗ КАТЕГОРИИ'}</b><br>
                        ${task.description || 'Нет описания'}
                    </div>
                </div>
                <button class="btn-delete" onclick="deleteTask(${task.id})">Delete</button>
            `;
            list.appendChild(li);
        });

        // Обновляем прогресс-бар после загрузки
        updateProgressBar(tasks);
    } catch (err) { console.error("Ошибка задач:", err); }
}

function updateProgressBar(tasks) {
    const progressBar = document.getElementById('progressBar');
    const percentText = document.getElementById('progressPercent'); // Находим текст
    if (!progressBar) return;

    if (!tasks || tasks.length === 0) {
        progressBar.style.width = '0%';
        if (percentText) percentText.textContent = '0%';
        return;
    }

    const completed = tasks.filter(t => t.done || t.isDone).length;
    const percent = Math.round((completed / tasks.length) * 100);

    progressBar.style.width = percent + '%';
    if (percentText) percentText.textContent = percent + '%'; // Обновляем текст
}

async function addTask() {
    const descInput = document.getElementById('taskInput');
    const nameInput = document.getElementById('nameInput');
    const priorityInput = document.getElementById('priorityInput');
    const userId = localStorage.getItem('userId');

    try {
        await fetch(API_URL, {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({
                description: descInput.value,
                name: nameInput.value,
                priority: priorityInput.value,
                done: false,
                userId: parseInt(userId)
            })
        });
        descInput.value = ''; nameInput.value = '';
        await loadTasks();
    } catch (err) { console.error(err); }
}

async function toggleTask(id, currentStatus) {
    try {
        await fetch(`${API_URL}/${id}`, {
            method: 'PUT',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({ done: !currentStatus })
        });
        await loadTasks();
    } catch (err) { console.error(err); }
}

async function deleteTask(id) {
    if (confirm('Are you sure?')) {
        await fetch(`${API_URL}/${id}`, { method: 'DELETE' });
        await loadTasks();
    }
}

async function loadWheelData() {
    const userId = localStorage.getItem('userId');
    if (!userId) return;

    try {
        const response = await fetch(`${WHEEL_API}/${userId}`);
        const data = await response.json();

        const wheelData = [
            { label: 'Здоровье и спорт', key: 'health', score: data.health || 0 },
            { label: 'Друзья и окружение', key: 'friends', score: data.friends || 0 },
            { label: 'Отношения', key: 'family', score: data.family || 0 },
            { label: 'Карьера и бизнес', key: 'work', score: data.work || 0 },
            { label: 'Финансы', key: 'finance', score: data.finance || 0 },
            { label: 'Духовность и творчество', key: 'spiritual', score: data.spiritual || 0 },
            { label: 'Личностный рост', key: 'learning', score: data.learning || 0 },
            { label: 'Яркость жизни', key: 'rest', score: data.rest || 0 }
        ];

        renderPerfectWheel('balanceChart', wheelData);
    } catch (err) { console.error("Ошибка колеса:", err); }
}

// 2. Обработка клика: расчет сектора и оценки
function setupWheelClick() {
    const canvas = document.getElementById('balanceChart');
    if (!canvas) return;

    canvas.addEventListener('click', async (event) => {
        const userId = localStorage.getItem('userId');
        if (!userId) return alert("Please login!");

        const rect = canvas.getBoundingClientRect();
        const x = event.clientX - rect.left - canvas.width / 2;
        const y = event.clientY - rect.top - canvas.height / 2;

        // Угол клика
        let angle = Math.atan2(y, x) + Math.PI / 2;
        if (angle < 0) angle += Math.PI * 2;

        const sectorIndex = Math.floor(angle / (Math.PI / 4)) % 8;
        const distance = Math.sqrt(x*x + y*y);
        const maxRadius = 200; // Половина squareSize

        let score = Math.ceil((distance / maxRadius) * 10);
        if (score > 10) score = 10;
        if (score < 1) score = 1;

        // Список ключей (в том же порядке, что в loadWheelData)
        const keys = ['health', 'friends', 'family', 'work', 'finance', 'spiritual', 'learning', 'rest'];
        const clickedKey = keys[sectorIndex];

        // Сохранение
        try {
            const res = await fetch(`${WHEEL_API}/${userId}`);
            let currentData = await res.json();
            currentData[clickedKey] = score;
            currentData.userId = parseInt(userId);

            await fetch(`${WHEEL_API}/${userId}`, {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify(currentData)
            });
            await loadWheelData();
        } catch (err) { console.error(err); }
    });
}

// --- ТВОЯ ФУНКЦИЯ (БЕЗ ИЗМЕНЕНИЙ) ---
function renderPerfectWheel(canvasId, data) {
    const canvas = document.getElementById(canvasId);
    if (!canvas) return;
    const ctx = canvas.getContext('2d');

    const width = canvas.width;
    const height = canvas.height;
    const centerX = width / 2;
    const centerY = height / 2;

    const squareSize = 400;
    const maxRadius = squareSize / 2;
    const labelRadius = maxRadius + 50;

    const gridColor = 'rgba(0, 0, 0, 0.08)';
    const fillColor = 'rgba(46, 125, 50, 0.7)';
    const textColor = '#1a1a1a';

    ctx.clearRect(0, 0, width, height);

    ctx.strokeStyle = '#000000';
    ctx.lineWidth = 1.5;

    for (let i = 1; i <= 10; i++) {
        const radius = (maxRadius / 10) * i;
        ctx.beginPath();
        ctx.arc(centerX, centerY, radius, 0, Math.PI * 2);
        ctx.stroke();
    }

    ctx.strokeStyle = '#000000';
    ctx.lineWidth = 2;
    for (let i = 0; i < 8; i++) {
        const angle = (Math.PI / 4) * i - Math.PI / 2;
        const xEnd = centerX + Math.cos(angle) * (squareSize / 2 * 1.41);
        const yEnd = centerY + Math.sin(angle) * (squareSize / 2 * 1.41);
        ctx.beginPath();
        ctx.moveTo(centerX, centerY);
        ctx.lineTo(xEnd, yEnd);
        ctx.stroke();
    }

    for (let i = 0; i < 8; i++) {
        const item = data[i];
        const startAngle = (Math.PI / 4) * i - Math.PI / 2;
        const endAngle = startAngle + (Math.PI / 4);
        const currentRadius = (maxRadius / 10) * item.score;

        ctx.fillStyle = fillColor;
        ctx.beginPath();
        ctx.moveTo(centerX, centerY);
        ctx.arc(centerX, centerY, currentRadius, startAngle, endAngle);
        ctx.closePath();
        ctx.fill();

        ctx.strokeStyle = '#000000';
        ctx.lineWidth = 3;
        ctx.stroke();
    }

    ctx.fillStyle = textColor;
    ctx.font = 'bold 13px "Inter", sans-serif';
    ctx.textBaseline = 'middle';
    ctx.textAlign = 'center';

    for (let i = 0; i < 8; i++) {
        const item = data[i];
        const axisAngle = (Math.PI / 4) * i - Math.PI / 2;
        const textAngle = axisAngle + (Math.PI / 8);
        const xText = centerX + Math.cos(textAngle) * labelRadius;
        const yText = centerY + Math.sin(textAngle) * labelRadius;

        ctx.save();
        ctx.translate(xText, yText);
        let rotationAngle = textAngle + Math.PI / 2;
        if (textAngle > 0 && textAngle < Math.PI) rotationAngle += Math.PI;
        ctx.rotate(rotationAngle);
        ctx.fillText(item.label.toUpperCase(), 0, 0);
        ctx.restore();
    }
}

document.addEventListener('DOMContentLoaded', () => {
    checkAuth();
    loadTasks();
    loadWheelData(); // Тянет из базы и рисует
    setupWheelClick(); // Включает клики

    const taskForm = document.getElementById('taskForm');
    if (taskForm) {
        taskForm.addEventListener('submit', function(event) {
            event.preventDefault();
            addTask();
        });
    }
});