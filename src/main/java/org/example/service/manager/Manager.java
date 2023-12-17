package org.example.service.manager;

import org.example.dto.*;
import org.example.entity.Epic;
import org.example.entity.SubTask;
import org.example.entity.Task;

public interface Manager {
    int saveTask(SaveTaskDto saveTaskDto);
    int saveEpic(SaveEpicDto saveEpicDto);
    int saveSubtask(SaveSubTaskDto saveSubTaskDto);
    Task getTaskById(int id);
    Epic getEpicById(int id);
    SubTask getSubTasksById(int id);
    void updateTask(UpdateTaskDto updateTaskDto);
    void updateEpic(UpdateEpicDto updateEpicDto);
    void updateSubTask(UpdateSubTaskDto updateSubTaskDto);
    void removeTaskById(int id);
    void removeEpicById(int id);
    void removeSubTaskById(int id);
}
