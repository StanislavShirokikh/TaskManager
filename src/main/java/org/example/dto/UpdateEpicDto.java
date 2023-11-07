package org.example.dto;

import org.springframework.stereotype.Component;

public class UpdateEpicDto extends AbstractUpdateTaskDto {
    @Override
    public String toString() {
        return "UpdateEpicDto{" +
                "name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() +
                '}';
    }
}
