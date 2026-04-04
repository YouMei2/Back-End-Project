const API_URL = 'http://localhost:8080/tasks';

/**
 * Fetches tasks from the server and renders them in the list
 */
async function loadTasks() {
    const userId = localStorage.getItem('userId'); // Достаем ID юзера
    const list = document.getElementById('taskList');

    if (!list || !userId) return; // Если нет списка или юзер не залогинен — выходим

    try {
        // Запрашиваем задачи конкретного юзера: /tasks?userId=1
        const response = await fetch(`${API_URL}?userId=${userId}`);
        const tasks = await response.json();

        list.innerHTML = '';

        tasks.forEach(task => {
            const li = document.createElement('li');
            if (task.done) li.classList.add('completed-task');

            li.innerHTML = `
                    <div class="task-content">
                        <input type="checkbox" ${task.done ? 'checked' : ''}
                               onchange="toggleTask(${task.id}, ${task.done})">
                        <div class="task-text ${task.done ? 'completed-text' : ''}">
                            <b>${task.name || 'NO CATEGORY'}</b><br>
                            ${task.description}
                        </div>
                    </div>
                    <button class="btn-delete" onclick="deleteTask(${task.id})">Delete</button>
                `;
            list.appendChild(li);
        });
    } catch (err) {
        console.error("Loading error:", err);
    }
}

/**
 * Adds a new task to the database
 */
async function addTask() {
    const descInput = document.getElementById('taskInput');
    const nameInput = document.getElementById('nameInput');
    const userId = localStorage.getItem('userId'); // Берем ID для привязки

    if (!userId) {
        alert("Please log in first!");
        return;
    }

    await fetch(API_URL, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({
            description: descInput.value,
            name: nameInput.value,
            done: false,
            userId: parseInt(userId) // Отправляем ID на сервер
        })
    });

    descInput.value = '';
    nameInput.value = '';
    loadTasks();
}

/**
 * Toggles task status (completed/pending)
 */
async function toggleTask(id, currentStatus) {
    await fetch(`${API_URL}/${id}`, {
        method: 'PUT',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({ done: !currentStatus })
    });
    loadTasks();
}

/**
 * Deletes a task by its ID
 */
async function deleteTask(id) {
    if (confirm('Delete this task?')) {
        await fetch(`${API_URL}/${id}`, { method: 'DELETE' });
        loadTasks();
    }
}

// Event listener for task form submission
const taskForm = document.getElementById('taskForm');
if (taskForm) {
    taskForm.addEventListener('submit', function(event) {
        event.preventDefault();
        addTask();
    });
}

// Initial task load
loadTasks();

/**
 * Manages the visibility of navigation buttons in the header
 */
function checkAuth() {
    const userId = localStorage.getItem('userId');
    const userName = localStorage.getItem('userName');

    const guestBtns = document.getElementById('guest-btns');
    const userProfile = document.getElementById('user-profile');
    const welcomeText = document.getElementById('welcome-text');

    if (userId) {
        // If logged in: hide Register/Login, show Profile
        guestBtns.style.display = 'none';
        userProfile.style.display = 'flex';
        welcomeText.textContent = `Hello, ${userName || 'User'}!`;
    } else {
        // If not logged in: show guest buttons
        guestBtns.style.display = 'block';
        userProfile.style.display = 'none';
    }
}

/**
 * System logout function
 */
function logout() {
    localStorage.removeItem('userId');
    localStorage.removeItem('userName');
    window.location.reload(); // Refresh the page to update the UI
}

// Execute auth check as soon as the DOM is fully loaded
document.addEventListener('DOMContentLoaded', checkAuth);