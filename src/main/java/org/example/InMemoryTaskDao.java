package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskDao implements TaskDao{
    private Map<Integer, Task> tasks = new HashMap<>();
    private Map<Integer, Epic> epics = new HashMap<>();
    private Map<Integer, SubTask> subTasks = new HashMap<>();

    @Override
    public void saveTaskInMemory(Task task) {
        if (!tasks.containsKey(task.getId())) {
            tasks.put(task.getId(), task);
        } else {
            throw new RuntimeException("Пытаетесь сохранить существующую задачу");
        }
    }

    @Override
    public void saveEpicInMemory(Epic epic) {
        if (!epics.containsKey(epic.getId())) {
            epics.put(epic.getId(), epic);
        } else {
            throw new RuntimeException("Пытаетесь сохранить существующую задачу");
        }
    }

    @Override
    public void saveSubtaskInMemory(SubTask subTask) {
        if (!subTasks.containsKey(subTask.getId())) {
            subTasks.put(subTask.getId(), subTask);
        } else {
            throw new RuntimeException("Пытаетесь сохранить существующую подзадачу");
        }
    }

    @Override
    public void removeAllTasks() {
        tasks.clear();
    }

    @Override
    public void removeAllEpics() {
        epics.clear();
    }

    @Override
    public void removeAllSubtasks() {
        subTasks.clear();
    }

    @Override
    public List<Task> getListOfTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public List<Epic> getListOfEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public List<SubTask> getListOfSubTasks() {
        return new ArrayList<>(subTasks.values());
    }

    @Override
    public Task getTaskById(int id) {
        if (!tasks.containsKey(id)) {
            throw new RuntimeException("Задачи с таким id не существует");
        } else {
            return tasks.get(id);
        }
    }

    @Override
    public Epic getEpicById(int id) {
        if (!epics.containsKey(id)) {
            throw new RuntimeException("Эпика с таким id не существует");
        } else {
            return epics.get(id);
        }
    }

    @Override
    public SubTask getSubTasksById(int id) {
        if (!subTasks.containsKey(id)) {
            throw new RuntimeException("Подзадачи с таким id не существует");
        } else {
            return subTasks.get(id);
        }
    }

    @Override
    public Task createTask(Task task) {
        return task;
    }

    @Override
    public Epic createEpic(Epic epic) {
        return epic;
    }

    @Override
    public SubTask createSubTask(SubTask subTask) {
        return subTask;
    }

    @Override
    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    @Override
    public void updateEpic(Epic epic) {
        epics.put(epic.getId(), epic);
    }

    @Override
    public void updateSubTask(SubTask subTask) {
        subTasks.put(subTask.getId(), subTask);
    }

    @Override
    public void removeTaskById(int id) {
        if (tasks.containsKey(id)) {
            tasks.remove(id);
        } else {
            throw new RuntimeException("Пытаетесь удалить несуществующую задачу");
        }
    }

    @Override
    public void removeEpicById(int id) {
        if (epics.containsKey(id)) {
            epics.remove(id);
        } else {
            throw new RuntimeException("Пытаетесь удалить несуществующий эпик");
        }
    }

    @Override
    public void removeSubTaskById(int id) {
        if (epics.containsKey(id)) {
            epics.remove(id);
        } else {
            throw new RuntimeException("Пытаетесь удалить несуществующую подзадачу");
        }
    }

    @Override
    public List<SubTask> getSubTaskListOfEpic(Epic epic) {
        return epic.getList();
    }
}
