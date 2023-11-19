package org.example.service.manager;

import org.example.dto.Epic;
import org.example.dto.SaveEpicDto;
import org.example.dto.SaveSubTaskDto;
import org.example.dto.SaveTaskDto;
import org.example.dto.Status;
import org.example.dto.SubTask;
import org.example.dto.Task;
import org.example.dto.UpdateEpicDto;
import org.example.dto.UpdateSubTaskDto;
import org.example.dto.UpdateTaskDto;
import org.example.dao.exceptions.EpicNotFoundException;
import org.example.dao.exceptions.SubTaskNotFoundException;
import org.example.dao.exceptions.TaskNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ManagerImplTest {
    @Autowired
    private Manager manager;

    @Test
    @DisplayName("Успешное сохранение Task")
    void saveTask() {
        SaveTaskDto saveTaskDto = new SaveTaskDto();
        saveTaskDto.setName("task");
        saveTaskDto.setDescription("task description");
        int actualId = manager.saveTask(saveTaskDto);
        Task actualTask = manager.getTaskById(actualId);

        assertEquals(saveTaskDto.getName(), actualTask.getName());
        assertEquals(saveTaskDto.getDescription(), actualTask.getDescription());
        assertEquals(Status.NEW, actualTask.getStatus());
        assertEquals(actualId, actualTask.getId());
    }

    @Test
    @DisplayName("Запрос Task с несуществующим идентификатором")
    void getTaskWithBadID() {
        assertThrows(TaskNotFoundException.class, () -> manager.getTaskById(-8));
    }

    @Test
    @DisplayName("Успешное сохранение Epic")
    void saveEpic() {
        SaveEpicDto saveEpicDto = new SaveEpicDto();
        saveEpicDto.setName("epic");
        saveEpicDto.setDescription("epic description");
        int epicId = manager.saveEpic(saveEpicDto);

        SaveSubTaskDto saveSubTaskDto = new SaveSubTaskDto();
        saveSubTaskDto.setName("subtask1");
        saveSubTaskDto.setDescription("subtask1 description");
        saveSubTaskDto.setEpicId(epicId);
        int subTask1Id = manager.saveSubtask(saveSubTaskDto);


        SaveSubTaskDto saveSubTaskDto2 = new SaveSubTaskDto();
        saveSubTaskDto.setName("subtask2");
        saveSubTaskDto.setDescription("subtask2 description");
        saveSubTaskDto2.setEpicId(epicId);
        int subtask2Id = manager.saveSubtask(saveSubTaskDto2);

        Epic actualEpic = manager.getEpicById(epicId);

        List<Integer> actualList = actualEpic.getSubtasksId();
        assertTrue(actualList.contains(subTask1Id));
        assertTrue(actualList.contains(subtask2Id));
        assertEquals(2, actualList.size());

        assertEquals(saveEpicDto.getName(), actualEpic.getName());
        assertEquals(saveEpicDto.getDescription(), actualEpic.getDescription());
        assertEquals(epicId, actualEpic.getId());
        assertEquals(Status.NEW, actualEpic.getStatus());
    }

    @Test
    @DisplayName("Запрос Epic с несуществующим идентификатором")
    void getEpicWithBadId() {
        assertThrows(EpicNotFoundException.class, () -> manager.getEpicById(-9));
    }

    @Test
    @DisplayName("Успешное сохранение Subtask")
    void saveSubtask() {
        SaveEpicDto saveEpicDto = new SaveEpicDto();
        saveEpicDto.setName("epic");
        saveEpicDto.setDescription("epic description");
        int epicId = manager.saveEpic(saveEpicDto);

        SaveSubTaskDto saveSubTaskDto = new SaveSubTaskDto();
        saveSubTaskDto.setName("subtask1");
        saveSubTaskDto.setDescription("subtask1 description");
        saveSubTaskDto.setEpicId(epicId);
        int subTaskId = manager.saveSubtask(saveSubTaskDto);

        SubTask actualSubTask = manager.getSubTasksById(subTaskId);

        assertEquals(saveSubTaskDto.getName(), actualSubTask.getName());
        assertEquals(saveSubTaskDto.getDescription(), actualSubTask.getDescription());
        assertEquals(Status.NEW, actualSubTask.getStatus());
        assertEquals(saveSubTaskDto.getEpicId(), actualSubTask.getEpicId());
    }

    @Test
    @DisplayName("Запрос Subtask с несуществующим идентификатором")
    void getSubtaskWithBadId() {
        assertThrows(SubTaskNotFoundException.class, () -> manager.getSubTasksById(-7));
    }

    @Test
    @DisplayName("Успешное обновление Task")
    void updateTask() {
        SaveTaskDto saveTaskDto = new SaveTaskDto();
        saveTaskDto.setName("task");
        saveTaskDto.setDescription("task description");
        int actualId = manager.saveTask(saveTaskDto);

        UpdateTaskDto updateTaskDto = new UpdateTaskDto();
        updateTaskDto.setName("updateTask");
        updateTaskDto.setDescription("updateTask description");
        updateTaskDto.setStatus(Status.IN_PROGRESS);
        updateTaskDto.setId(actualId);

        manager.updateTask(updateTaskDto);

        Task actualTask = manager.getTaskById(actualId);
        assertEquals(updateTaskDto.getName(), actualTask.getName());
        assertEquals(updateTaskDto.getDescription(), actualTask.getDescription());
        assertEquals(updateTaskDto.getStatus(), actualTask.getStatus());
        assertEquals(updateTaskDto.getId(), actualTask.getId());
    }

    @Test
    void updateTaskWithBadID() {
        SaveTaskDto saveTaskDto = new SaveTaskDto();
        saveTaskDto.setName("task");
        saveTaskDto.setDescription("task description");
        manager.saveTask(saveTaskDto);

        UpdateTaskDto updateTaskDto = new UpdateTaskDto();
        updateTaskDto.setName("updateTask");
        updateTaskDto.setDescription("updateTask description");
        updateTaskDto.setStatus(Status.IN_PROGRESS);
        updateTaskDto.setId(-3);

        assertThrows(TaskNotFoundException.class, () -> {
            manager.updateTask(updateTaskDto);
        });
    }

    @Test
    @DisplayName("Успешное обновление Epic")
    void updateEpic() {
        SaveEpicDto saveEpicDto = new SaveEpicDto();
        saveEpicDto.setName("epic");
        saveEpicDto.setDescription("epic description");
        int epicId = manager.saveEpic(saveEpicDto);

        SaveSubTaskDto saveSubTaskDto = new SaveSubTaskDto();
        saveSubTaskDto.setName("subtask1");
        saveSubTaskDto.setDescription("subtask1 description");
        saveSubTaskDto.setEpicId(epicId);
        int subTaskId = manager.saveSubtask(saveSubTaskDto);

        SaveSubTaskDto saveSubTaskDto2 = new SaveSubTaskDto();
        saveSubTaskDto.setName("subtask2");
        saveSubTaskDto.setDescription("subtask2 description");
        saveSubTaskDto2.setEpicId(epicId);
        int subtask2Id = manager.saveSubtask(saveSubTaskDto2);


        UpdateEpicDto updateEpicDto = new UpdateEpicDto();
        updateEpicDto.setName("UpdateEpic");
        updateEpicDto.setDescription("UpdateEpic Description");
        updateEpicDto.setId(epicId);

        manager.updateEpic(updateEpicDto);

        SubTask subTask = manager.getSubTasksById(subTaskId);
        SubTask subTask2 = manager.getSubTasksById(subtask2Id);
        assertNotNull(subTask);
        assertNotNull(subTask2);

        Epic actualEpic = manager.getEpicById(epicId);
        assertEquals(updateEpicDto.getName(), actualEpic.getName());
        assertEquals(updateEpicDto.getDescription(), actualEpic.getDescription());
        assertEquals(Status.NEW, actualEpic.getStatus());
        assertEquals(2, actualEpic.getSubtasksId().size());
    }

    @Test
    void updateEpicWithBadId() {
        SaveEpicDto saveEpicDto = new SaveEpicDto();
        saveEpicDto.setName("epic");
        saveEpicDto.setDescription("epic description");
        manager.saveEpic(saveEpicDto);

        UpdateEpicDto updateEpicDto = new UpdateEpicDto();
        updateEpicDto.setName("UpdateEpic");
        updateEpicDto.setDescription("UpdateEpic Description");
        updateEpicDto.setId(-2);
        assertThrows(EpicNotFoundException.class, () -> {
            manager.updateEpic(updateEpicDto);
        });
    }

    @Test
    @DisplayName("Успешное обновление Subtask")
    void updateSubTask() {
        SaveEpicDto saveEpicDto = new SaveEpicDto();
        saveEpicDto.setName("epic");
        saveEpicDto.setDescription("epic description");
        int epicId = manager.saveEpic(saveEpicDto);

        SaveSubTaskDto saveSubTaskDto = new SaveSubTaskDto();
        saveSubTaskDto.setName("subtask1");
        saveSubTaskDto.setDescription("subtask1 description");
        saveSubTaskDto.setEpicId(epicId);
        int subTaskId = manager.saveSubtask(saveSubTaskDto);

        UpdateSubTaskDto updateSubTaskDto = new UpdateSubTaskDto();
        updateSubTaskDto.setName("updateSubTask");
        updateSubTaskDto.setDescription("updateSubTask description");
        updateSubTaskDto.setId(subTaskId);
        updateSubTaskDto.setEpicId(epicId);
        updateSubTaskDto.setStatus(Status.IN_PROGRESS);

        manager.updateSubTask(updateSubTaskDto);

        SubTask actualSubTask = manager.getSubTasksById(subTaskId);
        assertEquals(updateSubTaskDto.getName(), actualSubTask.getName());
        assertEquals(updateSubTaskDto.getDescription(), actualSubTask.getDescription());
        assertEquals(updateSubTaskDto.getStatus(), actualSubTask.getStatus());
        assertEquals(updateSubTaskDto.getEpicId(), actualSubTask.getEpicId());
    }

    @Test
    void updateSubTaskWithBadID() {
        SaveEpicDto saveEpicDto = new SaveEpicDto();
        saveEpicDto.setName("epic");
        saveEpicDto.setDescription("epic description");
        int epicId = manager.saveEpic(saveEpicDto);

        SaveSubTaskDto saveSubTaskDto = new SaveSubTaskDto();
        saveSubTaskDto.setName("subtask1");
        saveSubTaskDto.setDescription("subtask1 description");
        saveSubTaskDto.setEpicId(epicId);
        manager.saveSubtask(saveSubTaskDto);

        UpdateSubTaskDto updateSubTaskDto = new UpdateSubTaskDto();
        updateSubTaskDto.setName("updateSubTask");
        updateSubTaskDto.setDescription("updateSubTask description");
        updateSubTaskDto.setId(-2);
        updateSubTaskDto.setEpicId(epicId);
        updateSubTaskDto.setStatus(Status.IN_PROGRESS);

        assertThrows(SubTaskNotFoundException.class, () -> {
            manager.updateSubTask(updateSubTaskDto);
        });
    }

    @Test
    @DisplayName("Успешное удаление Task")
    void removeTaskById() {
        SaveTaskDto saveTaskDto = new SaveTaskDto();
        saveTaskDto.setName("task");
        saveTaskDto.setDescription("task description");
        int taskId = manager.saveTask(saveTaskDto);

        manager.removeTaskById(taskId);

        assertThrows(TaskNotFoundException.class, () -> manager.getTaskById(taskId));
    }

    @Test
    void removeTaskWithBadID() {
        assertThrows(TaskNotFoundException.class, () -> {
            manager.removeTaskById(-2);
        });

    }

    @Test
    @DisplayName("Успешное удаление Epic")
    void removeEpicById() {
        SaveEpicDto saveEpicDto = new SaveEpicDto();
        saveEpicDto.setName("epic");
        saveEpicDto.setDescription("epic description");
        int epicId = manager.saveEpic(saveEpicDto);

        SaveSubTaskDto saveSubTaskDto = new SaveSubTaskDto();
        saveSubTaskDto.setName("subtask1");
        saveSubTaskDto.setDescription("subtask1 description");
        saveSubTaskDto.setEpicId(epicId);
        int subTask1Id = manager.saveSubtask(saveSubTaskDto);


        SaveSubTaskDto saveSubTaskDto2 = new SaveSubTaskDto();
        saveSubTaskDto.setName("subtask2");
        saveSubTaskDto.setDescription("subtask2 description");
        saveSubTaskDto2.setEpicId(epicId);
        int subTask2Id = manager.saveSubtask(saveSubTaskDto2);

        manager.removeEpicById(epicId);

        assertThrows(EpicNotFoundException.class, () -> manager.getEpicById(epicId));
        assertThrows(SubTaskNotFoundException.class, () -> manager.getSubTasksById(subTask1Id));
        assertThrows(SubTaskNotFoundException.class, () -> manager.getSubTasksById(subTask2Id));
    }

    @Test
    void removeEpicWithBadID() {
        assertThrows(EpicNotFoundException.class, () -> {
            manager.removeEpicById(-3);
        });
    }

    @Test
    @DisplayName("Успешное удаление Subtask")
    void removeSubTaskById() {
        SaveEpicDto saveEpicDto = new SaveEpicDto();
        saveEpicDto.setName("epic");
        saveEpicDto.setDescription("epic description");
        int epicId = manager.saveEpic(saveEpicDto);

        SaveSubTaskDto saveSubTaskDto = new SaveSubTaskDto();
        saveSubTaskDto.setName("subtask1");
        saveSubTaskDto.setDescription("subtask1 description");
        saveSubTaskDto.setEpicId(epicId);
        int subTask1Id = manager.saveSubtask(saveSubTaskDto);


        SaveSubTaskDto saveSubTaskDto2 = new SaveSubTaskDto();
        saveSubTaskDto.setName("subtask2");
        saveSubTaskDto.setDescription("subtask2 description");
        saveSubTaskDto2.setEpicId(epicId);
        int subTask2Id = manager.saveSubtask(saveSubTaskDto2);

        manager.removeSubTaskById(subTask2Id);

        assertThrows(SubTaskNotFoundException.class, () -> manager.getSubTasksById(subTask2Id));
        Epic actualEpic = manager.getEpicById(epicId);
        assertNotNull(actualEpic);
        assertTrue(actualEpic.getSubtasksId().contains(subTask1Id));
        assertFalse(actualEpic.getSubtasksId().contains(subTask2Id));
        assertEquals(1, actualEpic.getSubtasksId().size());
    }

    @Test
    void removeSubTaskWithBadID() {
        assertThrows(SubTaskNotFoundException.class, () -> {
            manager.removeSubTaskById(-9);
        });
    }

    @Test
    @DisplayName("Статус Epic рассчитывается верно")
    void getStatusOfEpicMustBeNew() {
        SaveEpicDto saveEpicDto = new SaveEpicDto();
        saveEpicDto.setName("epic");
        saveEpicDto.setDescription("epic description");
        int epicId = manager.saveEpic(saveEpicDto);

        SaveSubTaskDto saveSubTaskDto = new SaveSubTaskDto();
        saveSubTaskDto.setName("subtask1");
        saveSubTaskDto.setDescription("subtask1 description");
        saveSubTaskDto.setEpicId(epicId);
        manager.saveSubtask(saveSubTaskDto);


        SaveSubTaskDto saveSubTaskDto2 = new SaveSubTaskDto();
        saveSubTaskDto.setName("subtask2");
        saveSubTaskDto.setDescription("subtask2 description");
        saveSubTaskDto2.setEpicId(epicId);
        manager.saveSubtask(saveSubTaskDto2);

        Epic actualEpic = manager.getEpicById(epicId);
        assertEquals(Status.NEW, actualEpic.getStatus());
    }

    @Test
    void getEpicWithoutSubTaskMustBeNew() {
        SaveEpicDto saveEpicDto = new SaveEpicDto();
        saveEpicDto.setName("epicWithoutSubtask");
        saveEpicDto.setDescription("epicWithoutSubtask");
        int epicId = manager.saveEpic(saveEpicDto);

        Epic newEpic = manager.getEpicById(epicId);
        assertEquals(Status.NEW, newEpic.getStatus());
    }

    @Test
    void getStatusOfEpicMustBeInProgress() {
        SaveEpicDto saveEpicDto = new SaveEpicDto();
        saveEpicDto.setName("epic");
        saveEpicDto.setDescription("epic description");
        int epicId = manager.saveEpic(saveEpicDto);

        SaveSubTaskDto saveSubTaskDto = new SaveSubTaskDto();
        saveSubTaskDto.setName("subtask1");
        saveSubTaskDto.setDescription("subtask1 description");
        saveSubTaskDto.setEpicId(epicId);
        int subTaskId = manager.saveSubtask(saveSubTaskDto);

        SaveSubTaskDto saveSubTaskDto2 = new SaveSubTaskDto();
        saveSubTaskDto.setName("subtask2");
        saveSubTaskDto.setDescription("subtask2 description");
        saveSubTaskDto2.setEpicId(epicId);
        manager.saveSubtask(saveSubTaskDto2);

        UpdateSubTaskDto updateSubTaskDto = new UpdateSubTaskDto();
        updateSubTaskDto.setName("updateSubTask");
        updateSubTaskDto.setDescription("updateSubTask description");
        updateSubTaskDto.setId(subTaskId);
        updateSubTaskDto.setEpicId(epicId);
        updateSubTaskDto.setStatus(Status.DONE);

        manager.updateSubTask(updateSubTaskDto);

        Epic actualEpic = manager.getEpicById(epicId);
        assertEquals(Status.IN_PROGRESS, actualEpic.getStatus());
    }

    @Test
    void getStatusOfEpicMustBeDone() {
        SaveEpicDto saveEpicDto = new SaveEpicDto();
        saveEpicDto.setName("epic");
        saveEpicDto.setDescription("epic description");
        int epicId = manager.saveEpic(saveEpicDto);

        SaveSubTaskDto saveSubTaskDto = new SaveSubTaskDto();
        saveSubTaskDto.setName("subtask1");
        saveSubTaskDto.setDescription("subtask1 description");
        saveSubTaskDto.setEpicId(epicId);
        int subTaskId = manager.saveSubtask(saveSubTaskDto);

        SaveSubTaskDto saveSubTaskDto2 = new SaveSubTaskDto();
        saveSubTaskDto.setName("subtask2");
        saveSubTaskDto.setDescription("subtask2 description");
        saveSubTaskDto2.setEpicId(epicId);
        int subTask2Id = manager.saveSubtask(saveSubTaskDto2);

        UpdateSubTaskDto updateSubTaskDto = new UpdateSubTaskDto();
        updateSubTaskDto.setName("updateSubTask");
        updateSubTaskDto.setDescription("updateSubTask description");
        updateSubTaskDto.setId(subTaskId);
        updateSubTaskDto.setEpicId(epicId);
        updateSubTaskDto.setStatus(Status.DONE);

        UpdateSubTaskDto updateSubTaskDto2 = new UpdateSubTaskDto();
        updateSubTaskDto2.setName("updateSubTask");
        updateSubTaskDto2.setDescription("updateSubTask description");
        updateSubTaskDto2.setId(subTask2Id);
        updateSubTaskDto2.setEpicId(epicId);
        updateSubTaskDto2.setStatus(Status.DONE);

        manager.updateSubTask(updateSubTaskDto);
        manager.updateSubTask(updateSubTaskDto2);

        Epic actualEpic = manager.getEpicById(epicId);
        assertEquals(Status.DONE, actualEpic.getStatus());
    }
}