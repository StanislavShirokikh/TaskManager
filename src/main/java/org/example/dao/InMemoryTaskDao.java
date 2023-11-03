package org.example.dao;

import org.example.dto.Epic;
import org.example.dto.SubTask;
import org.example.dto.Task;
import org.example.service.IdGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
        Epic epic = epics.get(id);
        if (epic != null) {
            List<Integer> list = subTasks.values().stream()
                    .filter(subTask -> epic.getId().equals(subTask.getEpicId()))
                    .map(SubTask::getId)
                    .toList();
            epic.setSubtasksId(list);
            return epic;
        }
        return null;
    }

    @Override
    public SubTask getSubTasksById(int id) {
        return subTasks.get(id);
    }

    @Override
    public void updateTask(Task task) {
        if (tasks.containsKey(task.getId())) {
            tasks.put(task.getId(), task);
        } else {
            throw new RuntimeException("Вы не можете обновить Task с несуществующим ID");
        }
    }

    @Override
    public void updateEpic(Epic epic) {
        if (epics.containsKey(epic.getId())) {
            epics.put(epic.getId(), epic);
        } else {
            throw new RuntimeException("Вы не можете обновить Epic с несуществующим ID");
        }
    }

    @Override
    public void updateSubTask(SubTask subTask) {
        if (subTasks.containsKey(subTask.getId())) {
            subTasks.put(subTask.getId(), subTask);
        } else {
            throw new RuntimeException("Вы не можете обновить Subtask с несуществующим ID");
        }
    }

    @Override
    public void removeTaskById(int id) {
        tasks.remove(id);
    }

    @Override
    public void removeEpicById(int id) {
        Epic epic = getEpicById(id);
        List<Integer> subTasksId = epic.getSubtasksId();
        if (!subTasksId.isEmpty()) {
            for (Integer integer : subTasksId) {
                subTasks.remove(integer);
            }
        }
        epics.remove(id);
    }
    @Override
    public void removeSubTaskById(int id) {
        SubTask subTask = getSubTasksById(id);
        int epicId = subTask.getEpicId();
        Epic epic = getEpicById(epicId);
        Set<Integer> set = new HashSet<>(epic.getSubtasksId());
        set.remove(id);
        List<Integer> list = new ArrayList<>(set);
        epic.setSubtasksId(list);
        subTasks.remove(id);
    }
}
