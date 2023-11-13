package org.example.controller.requests;

public class UpdateEpicRequest extends AbstractUpdateTaskRequest{
    @Override
    public String toString() {
        return "UpdateEpicRequest{" +
                "name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() +
                ", id=" + getId() +
                '}';
    }
}
