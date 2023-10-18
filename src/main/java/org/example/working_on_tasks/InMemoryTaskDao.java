package org.example.working_on_tasks;

import org.example.all_tasks.Task;
import org.example.all_tasks.Epic;
import org.example.all_tasks.SubTask;

import java.util.HashMap;
import java.util.Map;

public class InMemoryTaskDao implements TaskDao {
    private final Map<Integer, Task> tasks = new HashMap<>();
    private final Map<Integer, Epic> epics = new HashMap<>();
    private final Map<Integer, SubTask> subTasks = new HashMap<>();
    private final IdGenerator taskIdGenerator = new IdGenerator();
    private final IdGenerator epicIdGenerator = new IdGenerator();
    private final IdGenerator subtaskIdGenerator = new IdGenerator();


    @Override
    public int saveTask(Task task) {
        task.setId(taskIdGenerator.getSequence());
        tasks.put(task.getId(), task);
        return task.getId();
    }

    @Override
    public int saveEpic(Epic epic) {
        epic.setId(epicIdGenerator.getSequence());
        epics.put(epic.getId(), epic);
        return epic.getId();
    }

    @Override
    public int saveSubtask(SubTask subTask) {
        subTask.setId(subtaskIdGenerator.getSequence());
        subTasks.put(subTask.getId(), subTask);
        return subTask.getId();
    }

    @Override
    public Task getTaskById(int id) {
        return tasks.get(id);
    }

    @Override
    public Epic getEpicById(int id) {
        return epics.get(id);
    }

    @Override
    public SubTask getSubTasksById(int id) {
        return subTasks.get(id);
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
        tasks.remove(id);
    }

    @Override
    public void removeEpicById(int id) {
        epics.remove(id);
    }

    @Override
    public void removeSubTaskById(int id) {
        epics.remove(id);
    }
}
