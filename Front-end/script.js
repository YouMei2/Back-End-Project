const API_URL = 'http://localhost:8080/tasks';

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
                            <b>${task.name || 'БЕЗ КАТЕГОРИИ'}</b><br>
                            ${task.description}
                        </div>
                    </div>
                    <button class="btn-delete" onclick="deleteTask(${task.id})">Удалить</button>
                `;
            list.appendChild(li);
        });
    } catch (err) {
        console.error("Ошибка загрузки:", err);
    }
}

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

async function toggleTask(id, currentStatus) {
    await fetch(`${API_URL}/${id}`, {
        method: 'PUT',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({ done: !currentStatus })
    });
    loadTasks();
}

async function deleteTask(id) {
    if (confirm('Удалить эту задачу?')) {
        await fetch(`${API_URL}/${id}`, { method: 'DELETE' });
        loadTasks();
    }
}

const taskForm = document.getElementById('taskForm');
taskForm.addEventListener('submit', function(event) {
    event.preventDefault();
    addTask();
});

loadTasks();