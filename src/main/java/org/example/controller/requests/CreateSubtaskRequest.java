package org.example.controller.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateSubtaskRequest extends AbstractCreateTaskRequest{
    @NotNull
    private Integer epicId;

    @Override
    public String toString() {
        return "CreateSubtaskRequest{" +
                "name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                '}';
    }
}
