package org.example.dto;

import org.springframework.stereotype.Component;

public class UpdateTaskDto extends AbstractUpdateTaskDto {
    @Override
    public String toString() {
        return "UpdateTaskDto{" +
                "name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() +
                '}';
    }
}
