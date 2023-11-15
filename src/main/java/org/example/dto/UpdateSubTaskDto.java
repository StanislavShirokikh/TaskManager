package org.example.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
@Getter
@Setter
public class UpdateSubTaskDto extends AbstractUpdateTaskDto{
    private int epicId;

    @Override
    public String toString() {
        return "UpdateSubTaskDto{" +
                "name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() +
                '}';
    }
}
