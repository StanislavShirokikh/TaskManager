package org.example.dto;

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
