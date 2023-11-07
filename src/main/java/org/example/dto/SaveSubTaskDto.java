package org.example.dto;

import org.springframework.stereotype.Component;

public class SaveSubTaskDto extends AbstractSaveTaskDto {
    private Integer epicId;

    public Integer getEpicId() {
        return epicId;
    }

    public void setEpicId(Integer epicId) {
        this.epicId = epicId;
    }

    @Override
    public String toString() {
        return "SaveSubtaskTaskDto{" +
                "name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                '}';
    }
}
