package org.example.all_tasks;

import org.example.Status;
import org.example.all_tasks.AbstractTask;

public class Task extends AbstractTask {
    @Override
    public String toString() {
        return "Task{" +
                "name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", id=" + getId() +
                ", status=" + getStatus() +
                '}';
    }
}
