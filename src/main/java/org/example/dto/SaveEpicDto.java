package org.example.dto;

import org.springframework.stereotype.Component;


public class SaveEpicDto extends AbstractSaveTaskDto {
    @Override
    public String toString() {
        return "SaveEpicDto{" +
                "name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                '}';
    }
}
