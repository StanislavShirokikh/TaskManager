package org.example.dto;

public class SaveSubTaskDto extends AbstractSaveTaskDto {
    @Override
    public String toString() {
        return "SaveSubtaskTaskDto{" +
                "name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                '}';
    }
}
