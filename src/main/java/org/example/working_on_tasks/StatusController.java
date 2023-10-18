package org.example.working_on_tasks;

import org.example.Status;
import org.example.all_tasks.Epic;
import org.example.all_tasks.SubTask;
import org.example.all_tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class StatusController implements StatusData {
    private final InMemoryTaskDao inMemoryTaskDao;

    public StatusController(InMemoryTaskDao inMemoryTaskDao) {
        this.inMemoryTaskDao = inMemoryTaskDao;
    }

    @Override
    public Status getTaskStatus(Task task) {
        return task.getStatus();
    }

    @Override
    public Status getEpicStatus(Epic epic) {
        if (isSubTasksNew(epic) || epic.getSubtasksId().isEmpty()) {
            return Status.NEW;
        }
        if (isSubTasksDone(epic)) {
            return Status.DONE;
        } else {
            return Status.IN_PROGRESS;
        }
    }

    @Override
    public Status getSubTaskStatus(SubTask subTask) {
        return subTask.getStatus();
    }

    @Override
    public void setTaskStatus(Task task, Status status) {
        task.setStatus(status);
    }

    @Override
    public void setSubTaskStatus(SubTask subTask, Status status) {
        subTask.setStatus(status);
    }

    private List<SubTask> getList(Epic epic) {
        List<Integer> list = epic.getSubtasksId();
        List<SubTask> subTasks = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            SubTask subTask = inMemoryTaskDao.getSubTasksById(list.get(i));
            subTasks.add(subTask);
        }
        return subTasks;
    }

    private boolean isSubTasksDone(Epic epic) {
        List<SubTask> subTasks = getList(epic);
        return subTasks.stream()
                .map(SubTask::getStatus)
                .allMatch(status -> status == Status.DONE);
    }

    private boolean isSubTasksNew(Epic epic) {
        List<SubTask> subTasks = getList(epic);
        return subTasks.stream()
                .map(SubTask::getStatus)
                .allMatch(status -> status == Status.NEW);
    }
}
