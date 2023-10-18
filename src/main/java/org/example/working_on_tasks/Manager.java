package org.example.working_on_tasks;

import org.example.Status;
import org.example.all_tasks.Task;
import org.example.all_tasks.Epic;
import org.example.all_tasks.SubTask;

public class Manager implements TaskDao, StatusData {
    private final InMemoryTaskDao inMemoryTaskDao = new InMemoryTaskDao();
    private final StatusController statusController = new StatusController(inMemoryTaskDao);

    @Override
    public int saveTask(Task task) {
        return inMemoryTaskDao.saveTask(task);
    }

    @Override
    public int saveEpic(Epic epic) {
        return inMemoryTaskDao.saveEpic(epic);
    }

    @Override
    public int saveSubtask(SubTask subTask) {
        return inMemoryTaskDao.saveSubtask(subTask);
    }


    @Override
    public Task getTaskById(int id) {
        return inMemoryTaskDao.getTaskById(id);
    }

    @Override
    public Epic getEpicById(int id) {
        return inMemoryTaskDao.getEpicById(id);
    }

    @Override
    public SubTask getSubTasksById(int id) {
        return inMemoryTaskDao.getSubTasksById(id);
    }

    @Override
    public void updateTask(Task task) {
        inMemoryTaskDao.updateTask(task);
    }

    @Override
    public void updateEpic(Epic epic) {
        inMemoryTaskDao.updateEpic(epic);
    }

    @Override
    public void updateSubTask(SubTask subTask) {
        inMemoryTaskDao.updateSubTask(subTask);
    }

    @Override
    public void removeTaskById(int id) {
        inMemoryTaskDao.removeTaskById(id);
    }

    @Override
    public void removeEpicById(int id) {
        inMemoryTaskDao.removeEpicById(id);
    }

    @Override
    public void removeSubTaskById(int id) {
        inMemoryTaskDao.removeSubTaskById(id);
    }

    @Override
    public Status getTaskStatus(Task task) {
        return statusController.getTaskStatus(task);
    }

    @Override
    public Status getEpicStatus(Epic epic) {
        return statusController.getEpicStatus(epic);
    }

    @Override
    public Status getSubTaskStatus(SubTask subTask) {
        return statusController.getSubTaskStatus(subTask);
    }

    @Override
    public void setTaskStatus(Task task, Status status) {
        statusController.setTaskStatus(task, status);
    }

    @Override
    public void setSubTaskStatus(SubTask subTask, Status status) {
        statusController.setSubTaskStatus(subTask, status);
    }
}
