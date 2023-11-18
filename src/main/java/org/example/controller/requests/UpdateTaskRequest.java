package org.example.controller.requests;

import org.example.dto.Status;

public class UpdateTaskRequest extends AbstractUpdateTaskRequest {

    @Override
    public String toString() {
        return "UpdateTaskRequest{" +
                "name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() +
                ", id=" + getId() +
                '}';
    }
}
