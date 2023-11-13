package org.example.controller;

import org.example.controller.converter.TaskDtoConverter;
import org.example.controller.requests.CreateTaskRequest;
import org.example.controller.requests.UpdateTaskRequest;
import org.example.dto.SaveTaskDto;
import org.example.dto.Task;
import org.example.dto.UpdateTaskDto;
import org.example.service.manager.Manager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/task-manager")
public class TaskManagerController {
    private final Manager manager;
    @Autowired
    public TaskManagerController(Manager manager) {
        this.manager = manager;
    }

    @PostMapping("/create")
    public int createTask(@RequestBody CreateTaskRequest createTaskRequest) {
        SaveTaskDto saveTaskDto = TaskDtoConverter.convert(createTaskRequest);
        return manager.saveTask(saveTaskDto);
    }

    @GetMapping("/get/{id}")
    public Task getTask(@PathVariable int id) {
        return manager.getTaskById(id);
    }

    @PutMapping("/update")
    public void updateTask(@RequestBody UpdateTaskRequest updateTaskRequest) {
        UpdateTaskDto updateTaskDto = TaskDtoConverter.convert(updateTaskRequest);
        manager.updateTask(updateTaskDto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteTask(@PathVariable int id) {
        manager.removeTaskById(id);
    }
}
