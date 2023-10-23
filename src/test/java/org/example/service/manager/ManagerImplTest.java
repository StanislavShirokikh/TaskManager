package org.example.service.manager;

import org.example.dto.Epic;
import org.example.dto.Status;
import org.example.dto.SubTask;
import org.example.dto.Task;
import org.example.service.IdGenerator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ManagerImplTest {
    private Manager manager;
    private Map<Integer, Task> tasks;
    private Map<Integer, Epic> epics;
    private Map<Integer, SubTask> subtasks;
    private Task task;
    private Epic epic;
    private SubTask subTask;
    private IdGenerator taskIdGenerator;
    private IdGenerator epicIdGenerator;
    private IdGenerator subtaskIdGenerator;

    @BeforeEach
    void setUp() {
        manager = new ManagerImpl();

        tasks = manager.getTasks();
        epics = manager.getEpics();
        subtasks = manager.getSubTasks();

        task = new Task();
        task.setName("task");
        task.setDescription("task description");
        task.setStatus(Status.NEW);


        epic = new Epic();
        epic.setName("epic");
        epic.setDescription("epic description");

        subTask = new SubTask();
        subTask.setName("subtask");
        subTask.setDescription("subtask description");
        subTask.setStatus(Status.NEW);

        taskIdGenerator = new IdGenerator();
        epicIdGenerator = new IdGenerator();
        subtaskIdGenerator = new IdGenerator();

//        task.setId(taskIdGenerator.getSequence());
//        epic.setId(epicIdGenerator.getSequence());
//        subTask.setId(subtaskIdGenerator.getSequence());
    }
    @Test
    @DisplayName("Успешное сохранение Task")
    void saveTask() {
        Task task = new Task();
        int actual = manager.saveTask(task);
        int actualSizeMap = this.tasks.size();

        Map<Integer, Task> tasks = new HashMap<>();
        IdGenerator taskIdGenerator = new IdGenerator();
        task.setId(taskIdGenerator.getSequence());
        tasks.put(task.getId(), task);
        int expected = task.getId();
        int expectedSizeMap = tasks.size();

        assertEquals(expected, actual);
        assertEquals(expectedSizeMap, actualSizeMap);
    }



    @Test
    void saveEpic() {
        Epic epic = new Epic();
        int actual = manager.saveEpic(epic);


        Map<Integer, Epic> epics = new HashMap<>();
        IdGenerator epicIdGenerator = new IdGenerator();
        epic.setId(epicIdGenerator.getSequence());
        epics.put(epic.getId(), epic);
        int expected = epic.getId();
        assertEquals(expected, actual);
    }

    @Test
    void saveSubtask() {
        SubTask subTask = new SubTask();
        int actual = manager.saveSubtask(subTask);

        Map<Integer, SubTask> subTasks = new HashMap<>();
        IdGenerator subTaskIdGenerator = new IdGenerator();
        subTask.setId(subTaskIdGenerator.getSequence());
        subTasks.put(subTask.getId(), subTask);
        int expected = subTask.getId();
        assertEquals(expected, actual);
    }

    @Test
    void getTaskById() {
        Task task = new Task();
        manager.saveTask(task);
        Task actual = manager.getTaskById(task.getId());

        Map<Integer, Task> tasks = new HashMap<>();
        IdGenerator taskIdGenerator = new IdGenerator();
        task.setId(taskIdGenerator.getSequence());
        tasks.put(task.getId(), task);
        Task expected = tasks.get(task.getId());

        assertEquals(expected, actual);
    }
    @Test
    void getEpicById() {
        Epic epic = new Epic();
        manager.saveEpic(epic);
        Epic actual = manager.getEpicById(epic.getId());

        Map<Integer, Epic> epics = new HashMap<>();
        IdGenerator epicIdGenerator = new IdGenerator();
        epic.setId(epicIdGenerator.getSequence());
        epics.put(epic.getId(), epic);
        Epic expected = epics.get(epic.getId());

        assertEquals(expected, actual);
    }

    @Test
    void getSubTasksById() {
        SubTask subTask = new SubTask();
        manager.saveSubtask(subTask);
        SubTask actual  = manager.getSubTasksById(subTask.getId());

        Map<Integer, SubTask> subTasks = new HashMap<>();
        IdGenerator subTaskIdGenerator = new IdGenerator();
        subTask.setId(subTaskIdGenerator.getSequence());
        subTasks.put(subTask.getId(), subTask);
        SubTask expected = subTasks.get(subTask.getId());
        assertEquals(expected, actual);
    }

    @Test
    void updateTask() {
        manager.saveTask(this.task);
        Task task = new Task();
        task.setId(this.task.getId());
        int actualSizeMap = tasks.size();
        manager.updateTask(task);
        int expectedSizeMap = tasks.size();
        assertEquals(expectedSizeMap, actualSizeMap);
    }

    @Test
    void updateEpic() {
    }

    @Test
    void updateSubTask() {
    }

    @Test
    void removeTaskById() {
    }

    @Test
    void removeEpicById() {
    }

    @Test
    void removeSubTaskById() {
    }
}