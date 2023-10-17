package org.example.all_tasks;

import org.example.Status;

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
    public Status getStatus() {
        throw new RuntimeException("Эпик не должен рассчитывать статус");
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
