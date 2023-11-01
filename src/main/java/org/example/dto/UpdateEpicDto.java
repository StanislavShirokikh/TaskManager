package org.example.dto;


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
