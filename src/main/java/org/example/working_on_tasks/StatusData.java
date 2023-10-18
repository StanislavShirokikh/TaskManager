package org.example.working_on_tasks;

import org.example.Status;
import org.example.all_tasks.Epic;
import org.example.all_tasks.SubTask;
import org.example.all_tasks.Task;

public interface StatusData {
    Status getTaskStatus(Task task);

    Status getEpicStatus(Epic epic);

    Status getSubTaskStatus(SubTask subTask);

    void setTaskStatus(Task task, Status status);

    void setSubTaskStatus(SubTask subTask, Status status);
}
