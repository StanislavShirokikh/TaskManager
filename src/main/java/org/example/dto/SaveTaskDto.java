package org.example.dto;

import org.springframework.stereotype.Component;

public class SaveTaskDto extends AbstractSaveTaskDto {
    @Override
    public String toString() {
        return "SaveTaskDto{" +
                "name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                '}';
    }
}
