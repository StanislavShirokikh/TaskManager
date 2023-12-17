package org.example.entity;

import org.example.entity.AbstractTask;

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
