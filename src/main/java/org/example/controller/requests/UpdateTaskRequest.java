package org.example.controller.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.example.entity.Status;
@Getter
@Setter
public class UpdateTaskRequest extends AbstractUpdateTaskRequest {
    @NotNull
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
