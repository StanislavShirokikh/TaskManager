package org.example.controller.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateSubtaskRequest extends AbstractCreateTaskRequest{
    private Integer epicId;

    @Override
    public String toString() {
        return "CreateSubtaskRequest{" +
                "name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                '}';
    }
}
