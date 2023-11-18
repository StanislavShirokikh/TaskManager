package org.example.controller.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateSubtaskRequest extends AbstractUpdateTaskRequest{
    private Integer epicId;

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
