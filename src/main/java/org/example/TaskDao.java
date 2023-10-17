package org.example;

import java.util.List;

public interface TaskDao {
    void saveTaskInMemory(Task task);
    void saveEpicInMemory(Epic epic);
    void saveSubtaskInMemory(SubTask subTask);

    void removeAllTasks();
    void removeAllEpics();
    void removeAllSubtasks();
    List<Task> getListOfTasks();
    List<Epic> getListOfEpics();
    List<SubTask> getListOfSubTasks();
    Task getTaskById(int id);
    Epic getEpicById(int id);
    SubTask getSubTasksById(int id);
    Task createTask(Task task);
    Epic createEpic(Epic epic);
    SubTask createSubTask(SubTask subTask);
    void updateTask(Task task);
    void updateEpic(Epic epic);
    void updateSubTask(SubTask subTask);
    void removeTaskById(int id);
    void removeEpicById(int id);
    void removeSubTaskById(int id);
    List<SubTask> getSubTaskListOfEpic(Epic epic);
}
