package org.example.service;

import org.example.dto.Epic;
import org.example.dto.SaveEpicDto;
import org.example.dto.SaveSubTaskDto;
import org.example.dto.SaveTaskDto;
import org.example.dto.Status;
import org.example.dto.SubTask;
import org.example.dto.Task;
import org.example.dto.UpdateEpicDto;
import org.example.dto.UpdateSubTaskDto;
import org.example.dto.UpdateTaskDto;
import org.springframework.context.annotation.Bean;

public class Converter {

    public static Task convertToTask(SaveTaskDto saveTaskDto) {
        Task task = new Task();
        task.setName(saveTaskDto.getName());
        task.setDescription(saveTaskDto.getDescription());
        task.setStatus(Status.NEW);
        return task;
    }

    public static Task convertToTask(UpdateTaskDto updateTaskDto) {
        Task task = new Task();
        task.setName(updateTaskDto.getName());
        task.setDescription(updateTaskDto.getDescription());
        task.setId(updateTaskDto.getId());
        task.setStatus(updateTaskDto.getStatus());
        return task;
    }

    public static Epic converterToEpic(SaveEpicDto saveEpicDto) {
        Epic epic = new Epic();
        epic.setName(saveEpicDto.getName());
        epic.setDescription(saveEpicDto.getDescription());
        epic.setStatus(Status.NEW);
        return epic;
    }

    public static Epic convertToEpic(UpdateEpicDto updateEpicDto) {
        Epic epic = new Epic();
        epic.setName(updateEpicDto.getName());
        epic.setDescription(updateEpicDto.getDescription());
        epic.setId(updateEpicDto.getId());
        return epic;
    }

    public static SubTask converterToSubTask(SaveSubTaskDto saveSubTaskDto) {
        SubTask subTask = new SubTask();
        subTask.setName(saveSubTaskDto.getName());
        subTask.setDescription(saveSubTaskDto.getDescription());
        subTask.setEpicId(saveSubTaskDto.getEpicId());
        subTask.setStatus(Status.NEW);
        return subTask;
    }

    public static SubTask converterToSubTask(UpdateSubTaskDto updateSubTaskDto) {
        SubTask subTask = new SubTask();
        subTask.setName(updateSubTaskDto.getName());
        subTask.setDescription(updateSubTaskDto.getDescription());
        subTask.setId(updateSubTaskDto.getId());
        subTask.setEpicId(updateSubTaskDto.getEpicId());
        subTask.setStatus(updateSubTaskDto.getStatus());
        return subTask;
    }
}
