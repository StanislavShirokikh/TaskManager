package org.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class Epic extends AbstractTask {
    private List<Integer> subtasksId;

    @Override
    public String toString() {
        return "Epic{" +
                "name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", id=" + getId() +
                ", status=" + getStatus() +
                ", listEpicId=" + getSubtasksId() +
                '}';
    }
}
