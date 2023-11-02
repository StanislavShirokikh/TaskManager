package org.example.dto;

public class SaveEpicDto extends AbstractSaveTaskDto {
    @Override
    public String toString() {
        return "SaveEpicDto{" +
                "name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                '}';
    }
}
