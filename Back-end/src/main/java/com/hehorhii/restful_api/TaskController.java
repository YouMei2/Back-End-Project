package com.hehorhii.restful_api;


import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping({"/tasks"})
public class TaskController {
    private final TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping
    public List<Task> getAllTasks(@RequestParam(name = "userId", required = false) Long userId) {
        if (userId != null) {
            return taskRepository.findByUserId(userId);
        }
        return taskRepository.findAll();
    }

    @PostMapping
    public Task createTask(@RequestBody Task task){
        return taskRepository.save(task);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable(name = "id") Long id) {
        taskRepository.deleteById(id);
    }
    @PutMapping("/{id}")
    public Task updateTask(@PathVariable(name = "id") Long id, @RequestBody Task taskDetails) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        task.setDone(taskDetails.isDone());
        return taskRepository.save(task);
    }
}
