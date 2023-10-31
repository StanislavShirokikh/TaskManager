package org.example.service;

import org.example.dto.*;

public class Converter {
    public static Task convertToTask(SaveTaskDto saveTaskDto) {
        Task task = new Task();
        task.setName(saveTaskDto.getName());
        task.setDescription(saveTaskDto.getDescription());
        return task;
    }
    public static Epic converterToEpic(SaveEpicDto saveEpicDto) {
        Epic epic = new Epic();
        epic.setName(saveEpicDto.getName());
        epic.setDescription(saveEpicDto.getDescription());
        return epic;
    }

    public static SubTask converterToSubTask(SaveSubTaskDto saveSubTaskDto) {
        SubTask subTask = new SubTask();
        subTask.setName(saveSubTaskDto.getName());
        subTask.setDescription(subTask.getDescription());
        return subTask;
    }
}
