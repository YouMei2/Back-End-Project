const API_URL = 'http://localhost:8080/tasks';

/**
 * Fetches tasks from the server and renders them in the list
 */
async function loadTasks() {
    try {
        const response = await fetch(API_URL);
        const tasks = await response.json();
        const list = document.getElementById('taskList');
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

    await fetch(API_URL, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({
            description: descInput.value,
            name: nameInput.value,
            done: false
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
