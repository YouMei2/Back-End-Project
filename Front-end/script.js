const API_URL = 'http://localhost:8080/tasks';

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

async function loadTasks() {
    const userId = localStorage.getItem('userId');
    const list = document.getElementById('taskList');

    if (!list || !userId) return;

    try {
        const response = await fetch(`${API_URL}?userId=${userId}`);

        if (!response.ok) {
            console.error("Ошибка сервера:", response.status);
            return;
        }

        const tasks = await response.json();

        list.innerHTML = '';

        if (Array.isArray(tasks)) {
            tasks.forEach(task => {
                const li = document.createElement('li');
                // Учитываем, что поле может называться done или isDone
                const isDone = task.done || task.isDone;

                if (isDone) li.classList.add('completed-task');

                li.innerHTML = `
                    <div class="task-content">
                        <input type="checkbox" ${isDone ? 'checked' : ''} 
                               onchange="toggleTask(${task.id}, ${isDone})">
                        <div class="task-text ${isDone ? 'completed-text' : ''}">
                            <b>${task.name || 'БЕЗ КАТЕГОРИИ'}</b><br>
                            ${task.description || 'Нет описания'}
                        </div>
                    </div>
                    <button class="btn-delete" onclick="deleteTask(${task.id})">Delete</button>
                `;
                list.appendChild(li);
            });
        }
    } catch (err) {
        console.error("Ошибка загрузки задач:", err);
    }
}

async function addTask() {
    const descInput = document.getElementById('taskInput');
    const nameInput = document.getElementById('nameInput');
    const userId = localStorage.getItem('userId');

    if (!userId) {
        alert("Please log in first!");
        return;
    }

    try {
        const response = await fetch(API_URL, {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({
                description: descInput.value,
                name: nameInput.value,
                done: false,
                userId: parseInt(userId)
            })
        });

        if (response.ok) {
            descInput.value = '';
            nameInput.value = '';
            // Вызываем загрузку только ОДИН раз после успешного добавления
            await loadTasks();
        }
    } catch (err) {
        console.error("Ошибка добавления:", err);
    }
}

async function toggleTask(id, currentStatus) {
    try {
        const response = await fetch(`${API_URL}/${id}`, {
            method: 'PUT',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({ done: !currentStatus })
        });
        if (response.ok) await loadTasks();
    } catch (err) {
        console.error("Ошибка переключения:", err);
    }
}

async function deleteTask(id) {
    if (confirm('Are you sure?')) {
        try {
            const response = await fetch(`${API_URL}/${id}`, { method: 'DELETE' });
            if (response.ok) await loadTasks();
        } catch (err) {
            console.error("Ошибка удаления:", err);
        }
    }
}

function logout() {
    localStorage.clear();
    window.location.reload();
}

document.addEventListener('DOMContentLoaded', () => {
    checkAuth();
    loadTasks();

    const taskForm = document.getElementById('taskForm');
    if (taskForm) {
        taskForm.addEventListener('submit', function(event) {
            event.preventDefault();
            addTask();
        });
    }
});