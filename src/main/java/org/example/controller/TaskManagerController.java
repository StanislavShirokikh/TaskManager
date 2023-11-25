package org.example.controller;

import jakarta.validation.Valid;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/task-manager")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Validated
public class TaskManagerController {
    private final Manager manager;

    @PostMapping("/task/create")
    @ResponseStatus(HttpStatus.CREATED)
    public int createTask(@RequestBody  @Valid CreateTaskRequest createTaskRequest) {
        SaveTaskDto saveTaskDto = TaskDtoConverter.convert(createTaskRequest);
        return manager.saveTask(saveTaskDto);
    }

    @GetMapping("/task/get/")
    public ResponseEntity<Task> getTask(@RequestParam("id") int id) {
        return ResponseEntity.ok(manager.getTaskById(id));
    }

    @PutMapping("/task/update")
    @ResponseStatus(HttpStatus.CREATED)
    public void updateTask(@RequestBody @Valid UpdateTaskRequest updateTaskRequest) {
        UpdateTaskDto updateTaskDto = TaskDtoConverter.convert(updateTaskRequest);
        manager.updateTask(updateTaskDto);
    }

    @DeleteMapping("/task/delete/")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTask(@RequestParam("id") int id) {
        manager.removeTaskById(id);
    }

    @PostMapping("/epic/create")
    @ResponseStatus(HttpStatus.CREATED)
    public int createEpic(@RequestBody @Valid CreateEpicRequest createEpicRequest) {
        SaveEpicDto saveEpicDto = TaskDtoConverter.convert(createEpicRequest);
        return manager.saveEpic(saveEpicDto);
    }

    @GetMapping("/epic/get/")
    public ResponseEntity<Epic> getEpic(@RequestParam("id") int id) {
        return ResponseEntity.ok(manager.getEpicById(id));
    }

    @PutMapping("/epic/update")
    @ResponseStatus(HttpStatus.CREATED)
    public void updateEpic(@RequestBody @Valid UpdateEpicRequest updateEpicRequest) {
        UpdateEpicDto updateEpicDto = TaskDtoConverter.convert(updateEpicRequest);
        manager.updateEpic(updateEpicDto);
    }

    @DeleteMapping("/epic/delete/")
    @ResponseStatus(HttpStatus.OK)
    public void deleteEpic(@RequestParam("id") int id) {
        manager.removeEpicById(id);
    }

    @PostMapping("/subtask/create")
    @ResponseStatus(HttpStatus.CREATED)
    public int createSubtask(@RequestBody @Valid CreateSubtaskRequest createSubtaskRequest) {
        SaveSubTaskDto saveSubTaskDto = TaskDtoConverter.convert(createSubtaskRequest);
        return manager.saveSubtask(saveSubTaskDto);
    }

    @GetMapping("/subtask/get/")
    public ResponseEntity<SubTask> getSubtask(@RequestParam("id") int id) {
        return ResponseEntity.ok(manager.getSubTasksById(id));
    }

    @PutMapping("/subtask/update")
    @ResponseStatus(HttpStatus.CREATED)
    public void updateSubtask(@RequestBody @Valid UpdateSubtaskRequest updateSubtaskRequest) {
        UpdateSubTaskDto updateSubTaskDto = TaskDtoConverter.convert(updateSubtaskRequest);
        manager.updateSubTask(updateSubTaskDto);
    }

    @DeleteMapping("/subtask/delete/")
    @ResponseStatus(HttpStatus.OK)
    public void deleteSubtask(@RequestParam("id") int id) {
        manager.removeSubTaskById(id);
    }
}
