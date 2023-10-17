package org.example.working_on_tasks;

import org.example.all_tasks.Task;
import org.example.all_tasks.Epic;
import org.example.all_tasks.SubTask;

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
