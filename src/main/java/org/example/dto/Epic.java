package org.example.dto;

import java.util.ArrayList;
import java.util.List;

public class Epic extends AbstractTask {
    private final List<Integer> subtasksId = new ArrayList<>();
    public void addSubtaskId(int id) {
        subtasksId.add(id);
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
