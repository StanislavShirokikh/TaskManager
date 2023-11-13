package org.example.controller.requests;

public class UpdateSubtaskRequest extends AbstractUpdateTaskRequest{
    @Override
    public String toString() {
        return "UpdateSubtaskRequest{" +
                "name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() +
                ", id=" + getId() +
                '}';
    }
}
