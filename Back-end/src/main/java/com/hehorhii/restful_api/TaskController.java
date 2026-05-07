package com.hehorhii.restful_api;


import org.springframework.web.bind.annotation.*;

import java.util.List;

// TaskController handles task-related operations.
// This REST controller provides endpoints for managing user tasks.
@RestController
@CrossOrigin(origins = "http://localhost:63342")
@RequestMapping({"/tasks"})
public class TaskController {
    private final TaskRepository taskRepository;

    // Constructor injecting TaskRepository dependency
    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // Get all tasks, optionally filtered by userId
    @GetMapping
    public List<Task> getAllTasks(@RequestParam(name = "userId", required = false) Long userId) {
        if (userId != null) {
            return taskRepository.findByUserId(userId);
        }
        return taskRepository.findAll();
    }

    // Create a new task
    @PostMapping
    public Task createTask(@RequestBody Task task){
        return taskRepository.save(task);
    }

    // Delete a task by ID
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable(name = "id") Long id) {
        taskRepository.deleteById(id);
    }

    // Update a task's done status by ID
    @PutMapping("/{id}")
    public Task updateTask(@PathVariable(name = "id") Long id, @RequestBody Task taskDetails) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        task.setDone(taskDetails.isDone());
        return taskRepository.save(task);
    }
}
