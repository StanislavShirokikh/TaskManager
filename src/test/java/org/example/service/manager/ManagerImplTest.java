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
    private final Manager manager = new ManagerImpl();

    @Test
    @DisplayName("Успешное сохранение Task")
    void saveTask() {
        Task task = new Task();
        task.setName("task");
        task.setDescription("task description");
        task.setStatus(Status.NEW);
        int actualId = manager.saveTask(task);
        Task actualTask = manager.getTaskById(actualId);

        assertNotEquals(task.getName(), actualTask.getName());
        assertEquals(task.getDescription(), actualTask.getDescription());
        assertEquals(task.getStatus(), actualTask.getStatus());
        assertEquals(task.getId(), actualTask.getId());
    }

    @Test
    @DisplayName("Запрос Task с несуществующим идентификатором")
    void getTaskWithBadID() {
        Task actualTask = manager.getTaskById(-5);
        assertNull(actualTask);
    }

    @Test
    @DisplayName("Успешное сохранение Epic")
    void saveEpic() {
        Epic epic = new Epic();
        epic.setName("epic");
        epic.setDescription("epic description");
        int actualId = manager.saveEpic(epic);

        SubTask subTask1 = new SubTask();
        subTask1.setName("subtask1");
        subTask1.setDescription("subtask1 description");
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
    @DisplayName("Запрос Epic с несуществующим идентификатором")
    void getEpicWithBadId() {
        Epic actualEpic = manager.getEpicById(-7);
        assertNull(actualEpic);
    }

    @Test
    @DisplayName("Успешное сохранение Subtask")
    void saveSubtask() {
        Epic epic = new Epic();
        epic.setName("epic");
        epic.setDescription("epic description");
        manager.saveEpic(epic);

        SubTask subTask = new SubTask();
        subTask.setName("subtask");
        subTask.setDescription("subtask description");
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
    @DisplayName("Запрос Subtask с несуществующим идентификатором")
    void getSubtaskWithBadId() {
        SubTask subTask = manager.getSubTasksById(-7);
        assertNull(subTask);
    }

    @Test
    @DisplayName("Успешное обновление Task")
    void updateTask() {
        Task task = new Task();
        task.setName("task");
        task.setDescription("task description");
        task.setStatus(Status.IN_PROGRESS);
        int taskId = manager.saveTask(task);

        Task updateTask = new Task();
        updateTask.setName("updateTask");
        updateTask.setDescription("updateTask description");
        updateTask.setStatus(Status.NEW);
        updateTask.setId(taskId);

        manager.updateTask(updateTask);

        Task actualTask = manager.getTaskById(taskId);
        assertEquals(updateTask.getName(), actualTask.getName());
        assertEquals(updateTask.getDescription(), actualTask.getDescription());
        assertEquals(updateTask.getStatus(), actualTask.getStatus());
    }

    @Test
    @DisplayName("Успешное обновление Epic")
    void updateEpic() {
        Epic epic = new Epic();
        epic.setName("epic");
        epic.setDescription("description");
        int epicId = manager.saveEpic(epic);

        SubTask subTask1 = new SubTask();
        subTask1.setName("subtask1");
        subTask1.setDescription("subtask1 description");
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

        Epic updateEpic = new Epic();
        updateEpic.setName("updateEpic name");
        updateEpic.setDescription("updateEpic description");
        updateEpic.setId(epicId);

        SubTask subTask3 = new SubTask();
        subTask3.setName("subtask3 name");
        subTask3.setDescription("subtask3 description");
        subTask3.setStatus(Status.IN_PROGRESS);
        subTask3.setEpicId(updateEpic.getId());
        manager.saveSubtask(subTask3);

        updateEpic.addSubtaskId(subTask3.getId());

        manager.updateEpic(updateEpic);
        Epic actualEpic = manager.getEpicById(epicId);

        assertEquals(updateEpic.getName(), actualEpic.getName());
        assertEquals(updateEpic.getDescription(), actualEpic.getDescription());
        assertEquals(Status.IN_PROGRESS, actualEpic.getStatus());
        assertEquals(updateEpic.getSubtasksId(), actualEpic.getSubtasksId());
    }

    @Test
    @DisplayName("Успешное обновление Subtask")
    void updateSubTask() {
        Epic epic = new Epic();
        epic.setName("epic");
        epic.setDescription("epic description");
        manager.saveEpic(epic);

        SubTask subTask = new SubTask();
        subTask.setName("subtask");
        subTask.setDescription("subtask description");
        subTask.setStatus(Status.DONE);
        subTask.setEpicId(epic.getId());
        int subTaskId = manager.saveSubtask(subTask);

        SubTask updateSubtask = new SubTask();
        updateSubtask.setName("updateSubTask name");
        updateSubtask.setDescription("updateSubtask description");
        updateSubtask.setStatus(Status.NEW);
        updateSubtask.setId(subTaskId);
        updateSubtask.setEpicId(epic.getId());

        manager.updateSubTask(updateSubtask);
        SubTask actualSubtask = manager.getSubTasksById(subTaskId);

        assertEquals(updateSubtask.getName(), actualSubtask.getName());
        assertEquals(updateSubtask.getDescription(), actualSubtask.getDescription());
        assertEquals(updateSubtask.getStatus(), actualSubtask.getStatus());
        assertEquals(updateSubtask.getEpicId(), actualSubtask.getEpicId());
    }

    @Test
    @DisplayName("Успешное удаление Task")
    void removeTaskById() {
        Task task = new Task();
        task.setName("task");
        task.setDescription("description");
        task.setStatus(Status.DONE);
        int taskId = manager.saveTask(task);

        manager.removeTaskById(taskId);

        Task actualTask = manager.getTaskById(taskId);
        assertNull(actualTask);
    }

    @Test
    @DisplayName("Успешное удаление Epic")
    void removeEpicById() {
        Epic epic = new Epic();
        epic.setName("epic");
        epic.setDescription("epic description");
        int epicId = manager.saveEpic(epic);

        SubTask subTask1 = new SubTask();
        subTask1.setName("subtask1");
        subTask1.setDescription("subtask1 description");
        subTask1.setStatus(Status.NEW);
        subTask1.setEpicId(epic.getId());
        int subtask1Id = manager.saveSubtask(subTask1);

        SubTask subTask2 = new SubTask();
        subTask2.setName("subtask2");
        subTask2.setDescription("subtask2 description");
        subTask2.setStatus(Status.NEW);
        subTask2.setEpicId(epic.getId());
        int subtask2Id = manager.saveSubtask(subTask2);

        epic.addSubtaskId(subTask1.getId());
        epic.addSubtaskId(subTask2.getId());

        manager.removeEpicById(epicId);

        Epic actualEpic = manager.getEpicById(epicId);
        assertNull(actualEpic);
        SubTask actualSubTask1 = manager.getSubTasksById(subtask1Id);
        SubTask actualSubtask2 = manager.getSubTasksById(subtask2Id);
        assertNull(actualSubTask1);
        assertNull(actualSubtask2);
    }

    @Test
    @DisplayName("Успешное удаление Subtask")
    void removeSubTaskById() {
        Epic epic = new Epic();
        epic.setName("name");
        epic.setDescription("description");
        int epicId = manager.saveEpic(epic);

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
        int subtask2Id = manager.saveSubtask(subTask2);

        epic.addSubtaskId(subTask1.getId());
        epic.addSubtaskId(subTask2.getId());

        manager.removeSubTaskById(subtask2Id);

        SubTask actualSubTask = manager.getSubTasksById(subtask2Id);
        assertNull(actualSubTask);
        Epic actualEpic = manager.getEpicById(epicId);
        assertNotNull(actualEpic);
        assertFalse(actualEpic.getSubtasksId().contains(subtask2Id));
    }

    @Test
    @DisplayName("Статус Epic рассчитывается верно")
    void getStatusOfEpicMustBeNew() {
        Epic epic = new Epic();
        epic.setName("epic");
        epic.setDescription("epic description");
        int actualId = manager.saveEpic(epic);

        SubTask subTask1 = new SubTask();
        subTask1.setName("subtask1");
        subTask1.setDescription("subtask1 description");
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

        Epic actualEpic1 = manager.getEpicById(actualId);
        assertEquals(Status.NEW, actualEpic1.getStatus());
    }

    @Test
    void getEpicWithoutSubTaskMustBeNew() {
        Epic epicWithoutSubtasks = new Epic();
        epicWithoutSubtasks.setName("epicWithoutSubtask");
        epicWithoutSubtasks.setDescription("epicWithoutSubtask description");
        int epicId = manager.saveEpic(epicWithoutSubtasks);
        Epic newEpic = manager.getEpicById(epicId);
        assertEquals(Status.NEW, newEpic.getStatus());
    }

    @Test
    void getStatusOfEpicMustBeInProgress() {
        Epic epic = new Epic();
        epic.setName("epic");
        epic.setDescription("epic description");
        int actualId = manager.saveEpic(epic);

        SubTask subTask1 = new SubTask();
        subTask1.setName("subtask1");
        subTask1.setDescription("subtask1 description");
        subTask1.setStatus(Status.IN_PROGRESS);
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

        Epic actualEpic1 = manager.getEpicById(actualId);
        assertEquals(Status.IN_PROGRESS, actualEpic1.getStatus());
    }

    @Test
    void getStatusOfEpicMustBeDone() {
        Epic epic = new Epic();
        epic.setName("epic");
        epic.setDescription("epic description");
        int actualId = manager.saveEpic(epic);

        SubTask subTask1 = new SubTask();
        subTask1.setName("subtask1");
        subTask1.setDescription("subtask1 description");
        subTask1.setStatus(Status.DONE);
        subTask1.setEpicId(epic.getId());
        manager.saveSubtask(subTask1);

        SubTask subTask2 = new SubTask();
        subTask2.setName("subtask2");
        subTask2.setDescription("subtask2 description");
        subTask2.setStatus(Status.DONE);
        subTask2.setEpicId(epic.getId());
        manager.saveSubtask(subTask2);

        epic.addSubtaskId(subTask1.getId());
        epic.addSubtaskId(subTask2.getId());

        Epic actualEpic1 = manager.getEpicById(actualId);
        assertEquals(Status.DONE, actualEpic1.getStatus());
    }
}