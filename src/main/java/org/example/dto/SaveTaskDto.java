package org.example.dto;

public class SaveTaskDto extends AbstractSaveTaskDto {
    @Override
    public String toString() {
        return "SaveTaskDto{" +
                "name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                '}';
    }
}
