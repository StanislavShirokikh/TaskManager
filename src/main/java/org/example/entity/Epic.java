package org.example.entity;

import lombok.Getter;
import lombok.Setter;
import org.example.entity.AbstractTask;

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
