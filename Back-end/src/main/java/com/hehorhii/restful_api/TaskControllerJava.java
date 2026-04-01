package com.hehorhii.restful_api;


import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping({"/tasks", "/tasks"})
public class TaskControllerJava {
    private final TaskRepository taskRepository;

    public TaskControllerJava(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping
    public List<Task> getAllTasks(){
        return taskRepository.findAll();
    }
    @PostMapping
    public Task createTask(@RequestBody Task task){
        return taskRepository.save(task);
    }
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskRepository.deleteById(id);
    }
    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task taskDetails) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        task.setDone(taskDetails.isDone()); // Обновляем только статус
        return taskRepository.save(task);
    }
}
