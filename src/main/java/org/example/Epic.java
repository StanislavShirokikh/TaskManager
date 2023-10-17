package org.example;

import java.util.ArrayList;
import java.util.List;

public class Epic extends AbstractTask{
    private List<SubTask> list = new ArrayList<>();

    public Epic(String name, String description, int id) {
        super(name, description, id);
    }

    private Status changeStatus() {
        if (isTasksNew() || list.isEmpty()) {
            return Status.NEW;
        }
        if (isTasksDone()) {
            return Status.DONE;
        } else {
            return Status.IN_PROGRESS;
        }
    }
    private boolean isTasksDone() {
        return list.stream()
                .map(AbstractTask::getStatus)
                .allMatch(status -> status == Status.DONE);
    }
    private boolean isTasksNew() {
        return list.stream()
                .map(AbstractTask::getStatus)
                .allMatch(status -> status == Status.NEW);
    }
    public void addToList(SubTask subTask) {
        list.add(subTask);
    }

    public List<SubTask> getList() {
        return list;
    }

    @Override
    public Status getStatus() {
        return changeStatus();
    }
}
