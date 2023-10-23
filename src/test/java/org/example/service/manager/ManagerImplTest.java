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

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ManagerImplTest {
    private Manager manager = new ManagerImpl();


    @Test
    @DisplayName("Успешное сохранение Task")
    void saveTask() {
        Task task = new Task();
        task.setName("name");
        task.setDescription("description");
        task.setStatus(Status.NEW);
        int actualId = manager.saveTask(task);
        Task actualTask = manager.getTaskById(actualId);

        assertEquals(task.getName(), actualTask.getName());
        assertEquals(task.getDescription(), actualTask.getDescription());
        assertEquals(task.getStatus(), actualTask.getStatus());
        assertEquals(task.getId(), actualTask.getId());
    }

    @Test
    void getTaskWithBadID() {
        Task actualTask = manager.getTaskById(-5);
        assertNull(actualTask);
    }
    @Test
    void saveEpic() {
        Epic epic = new Epic();
        epic.setName("name");
        epic.setDescription("description");
        int actualId = manager.saveEpic(epic);

        SubTask subTask1 = new SubTask();
        subTask1.setName("subtask1");
        subTask1.setDescription("subtask description");
        subTask1.setStatus(Status.NEW);
        subTask1.setEpicId(epic.getId());
        manager.saveSubtask(subTask1);

        SubTask subTask2 = new SubTask();
        subTask2.setName("subtask2");
        subTask2.setDescription("subtask2 description");
        subTask2.setStatus(Status.NEW);
        subTask2.setEpicId(epic.getId());
        manager.saveSubtask(subTask2);

        epic.addSubtaskId(subTask1.getId());
        epic.addSubtaskId(subTask2.getId());

        Epic actualEpic = manager.getEpicById(actualId);

        List<Integer> expectedList = epic.getSubtasksId();
        List<Integer> actualList = actualEpic.getSubtasksId();

        Collections.sort(expectedList);
        Collections.sort(actualList);

        assertEquals(epic.getName(), actualEpic.getName());
        assertEquals(epic.getDescription(), actualEpic.getDescription());
        assertEquals(epic.getId(), actualEpic.getId());
        assertEquals(Status.NEW, actualEpic.getStatus());
        assertEquals(expectedList, actualList);
    }

    @Test
    void saveSubtask() {
        Epic epic = new Epic();
        epic.setName("epic");
        epic.setDescription("epic description");
        manager.saveEpic(epic);

        SubTask subTask = new SubTask();
        subTask.setName("subtask");
        subTask.setDescription("description");
        subTask.setStatus(Status.NEW);
        subTask.setEpicId(epic.getId());
        int actualId = manager.saveSubtask(subTask);

        SubTask actualSubtask = manager.getSubTasksById(actualId);

        assertEquals(subTask.getName(), actualSubtask.getName());
        assertEquals(subTask.getDescription(), actualSubtask.getDescription());
        assertEquals(subTask.getStatus(), actualSubtask.getStatus());
        assertEquals(subTask.getEpicId(), actualSubtask.getEpicId());
    }
    @Test
    void updateTask() {
        Task task = new Task();
        task.setName("name");
        task.setDescription("description");
        task.setStatus(Status.IN_PROGRESS);
        int taskId = manager.saveTask(task);

        Task task1 = new Task();
        task1.setName("new name");
        task1.setDescription("new description");
        task1.setStatus(Status.NEW);
        task1.setId(taskId);

        manager.updateTask(task1);

        Task actualTask = manager.getTaskById(taskId);
        assertEquals(task1.getName(), actualTask.getName());
        assertEquals(task1.getDescription(), actualTask.getDescription());
        assertEquals(task1.getStatus(), actualTask.getStatus());
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