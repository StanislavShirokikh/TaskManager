package org.example.dto;

import java.util.List;

public class Epic extends AbstractTask {
    private List<Integer> subtasksId;

    public void setSubtasksId(List<Integer> subtasksId) {
        this.subtasksId = subtasksId;
    }

    public List<Integer> getSubtasksId() {
        return subtasksId;
    }

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
