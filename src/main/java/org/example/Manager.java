package org.example;

import java.util.List;

public class Manager implements TaskDao {
    private final InMemoryTaskDao inMemoryTaskDao;


    public Manager(InMemoryTaskDao inMemoryTaskDao) {
        this.inMemoryTaskDao = inMemoryTaskDao;
    }

    @Override
    public void saveTaskInMemory(Task task) {
        inMemoryTaskDao.saveTaskInMemory(task);
    }

    @Override
    public void saveEpicInMemory(Epic epic) {
        inMemoryTaskDao.saveEpicInMemory(epic);
    }

    @Override
    public void saveSubtaskInMemory(SubTask subTask) {
        inMemoryTaskDao.saveSubtaskInMemory(subTask);
    }

    @Override
    public void removeAllTasks() {
        inMemoryTaskDao.removeAllTasks();
    }

    @Override
    public void removeAllEpics() {
        inMemoryTaskDao.removeAllEpics();
    }

    @Override
    public void removeAllSubtasks() {
        inMemoryTaskDao.removeAllSubtasks();
    }

    @Override
    public List<Task> getListOfTasks() {
        return inMemoryTaskDao.getListOfTasks();
    }

    @Override
    public List<Epic> getListOfEpics() {
        return inMemoryTaskDao.getListOfEpics();
    }

    @Override
    public List<SubTask> getListOfSubTasks() {
        return inMemoryTaskDao.getListOfSubTasks();
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
    public Task createTask(Task task) {
        return inMemoryTaskDao.createTask(task);
    }

    @Override
    public Epic createEpic(Epic epic) {
        return inMemoryTaskDao.createEpic(epic);
    }
    @Override
    public SubTask createSubTask(SubTask subTask) {
        return inMemoryTaskDao.createSubTask(subTask);
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
        inMemoryTaskDao.createSubTask(subTask);
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
    public List<SubTask> getSubTaskListOfEpic(Epic epic) {
        return inMemoryTaskDao.getSubTaskListOfEpic(epic);
    }
}
