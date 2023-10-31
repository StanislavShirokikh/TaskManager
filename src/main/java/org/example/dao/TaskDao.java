package org.example.dao;

import org.example.dto.*;

import java.util.Map;

public interface TaskDao {
    int saveTask(Task task);
    int saveEpic(Epic epic);
    int saveSubtask(SubTask subTask);
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
