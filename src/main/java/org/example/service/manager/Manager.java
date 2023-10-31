package org.example.service.manager;

import org.example.dto.*;

import java.util.Map;

public interface Manager {
    int saveTask(SaveTaskDto saveTaskDto);
    int saveEpic(SaveEpicDto saveEpicDto);
    int saveSubtask(SaveSubTaskDto saveSubTaskDto);
    Task getTaskById(int id);
    Epic getEpicById(int id);
    SubTask getSubTasksById(int id);
    void updateTask(Task task);
    void updateEpic(Epic epic);
    void updateSubTask(SubTask subTask);
    void removeTaskById(int id);
    void removeEpicById(int id);
    void removeSubTaskById(int id);
}
