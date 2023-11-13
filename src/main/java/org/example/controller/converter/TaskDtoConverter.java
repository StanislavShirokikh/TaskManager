package org.example.controller.converter;

import org.example.controller.requests.CreateTaskRequest;
import org.example.controller.requests.UpdateTaskRequest;
import org.example.dto.SaveTaskDto;
import org.example.dto.UpdateTaskDto;

public class TaskDtoConverter {
    public static SaveTaskDto convert(CreateTaskRequest createTaskRequest) {
        SaveTaskDto saveTaskDto = new SaveTaskDto();
        saveTaskDto.setName(createTaskRequest.getName());
        saveTaskDto.setDescription(createTaskRequest.getDescription());
        return saveTaskDto;
    }

    public static UpdateTaskDto convert(UpdateTaskRequest updateTaskRequest) {
        UpdateTaskDto updateTaskDto = new UpdateTaskDto();
        updateTaskDto.setName(updateTaskRequest.getName());
        updateTaskDto.setDescription(updateTaskDto.getDescription());
        updateTaskDto.setId(updateTaskRequest.getId());
        updateTaskDto.setStatus(updateTaskRequest.getStatus());
        return updateTaskDto;
    }
}
