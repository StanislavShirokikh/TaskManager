package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.controller.converter.TaskDtoConverter;
import org.example.controller.requests.CreateEpicRequest;
import org.example.controller.requests.CreateSubtaskRequest;
import org.example.controller.requests.CreateTaskRequest;
import org.example.controller.requests.UpdateEpicRequest;
import org.example.controller.requests.UpdateSubtaskRequest;
import org.example.controller.requests.UpdateTaskRequest;
import org.example.dto.Epic;
import org.example.dto.SaveEpicDto;
import org.example.dto.SaveSubTaskDto;
import org.example.dto.SaveTaskDto;
import org.example.dto.SubTask;
import org.example.dto.Task;
import org.example.dto.UpdateEpicDto;
import org.example.dto.UpdateSubTaskDto;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/task-manager")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TaskManagerController {
    private final Manager manager;

    @PostMapping("/task/create")
    public int createTask(@RequestBody CreateTaskRequest createTaskRequest) {
        SaveTaskDto saveTaskDto = TaskDtoConverter.convert(createTaskRequest);
        return manager.saveTask(saveTaskDto);
    }

    @GetMapping("/task/get/{id}")
    public Task getTask(@RequestParam int id) {
        return manager.getTaskById(id);
    }

    @PutMapping("/task/update")
    public void updateTask(@RequestBody UpdateTaskRequest updateTaskRequest) {
        UpdateTaskDto updateTaskDto = TaskDtoConverter.convert(updateTaskRequest);
        manager.updateTask(updateTaskDto);
    }

    @DeleteMapping("/task/delete/{id}")
    public void deleteTask(@RequestParam int id) {
        manager.removeTaskById(id);
    }

    @PostMapping("/epic/create")
    public int createEpic(@RequestBody CreateEpicRequest createEpicRequest) {
        SaveEpicDto saveEpicDto = TaskDtoConverter.convert(createEpicRequest);
        return manager.saveEpic(saveEpicDto);
    }

    @GetMapping("/epic/get/{id}")
    public Epic getEpic(@RequestParam int id) {
        return manager.getEpicById(id);
    }

    @PutMapping("/epic/update")
    public void updateEpic(@RequestBody UpdateEpicRequest updateEpicRequest) {
        UpdateEpicDto updateEpicDto = TaskDtoConverter.convert(updateEpicRequest);
        manager.updateEpic(updateEpicDto);
    }

    @DeleteMapping("/epic/delete/{id}")
    public void deleteEpic(@RequestParam int id) {
        manager.removeEpicById(id);
    }

    @PostMapping("/subtask/create")
    public int createSubtask(@RequestBody CreateSubtaskRequest createSubtaskRequest) {
        SaveSubTaskDto saveSubTaskDto = TaskDtoConverter.convert(createSubtaskRequest);
        return manager.saveSubtask(saveSubTaskDto);
    }

    @GetMapping("/subtask/get/{id}")
    public SubTask getSubtask(@RequestParam int id) {
        return manager.getSubTasksById(id);
    }

    @PutMapping("/subtask/update")
    public void updateSubtask(@RequestBody UpdateSubtaskRequest updateSubtaskRequest) {
        UpdateSubTaskDto updateSubTaskDto = TaskDtoConverter.convert(updateSubtaskRequest);
        manager.updateSubTask(updateSubTaskDto);
    }

    @DeleteMapping("/subtask/delete/{id}")
    public void deleteSubtask(@RequestParam int id) {
        manager.removeSubTaskById(id);
    }
}
