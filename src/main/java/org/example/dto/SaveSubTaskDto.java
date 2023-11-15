package org.example.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
@Getter
@Setter
public class SaveSubTaskDto extends AbstractSaveTaskDto {
    private Integer epicId;

    @Override
    public String toString() {
        return "SaveSubtaskTaskDto{" +
                "name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                '}';
    }
}
