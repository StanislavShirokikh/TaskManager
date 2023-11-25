package org.example.controller.requests;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.example.dto.Status;
@Getter
@Setter
public class UpdateTaskRequest extends AbstractUpdateTaskRequest {
    private Status status;

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
