package org.example.dto;

import org.example.service.manager.Manager;
import org.example.service.manager.ManagerImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {
    private Epic epic;
    private SubTask subTask1;
    private SubTask subTask2;
    private Manager manager;
    @BeforeEach
    void setUp() {
        epic = new Epic();
        subTask1 = new SubTask();
        subTask2 = new SubTask();
        manager = new ManagerImpl();
        manager.saveEpic(epic);
        manager.saveSubtask(subTask1);
        manager.saveSubtask(subTask2);
        subTask1.setEpicId(epic.getId());
        subTask2.setEpicId(epic.getId());

    }
    @Test
    void getSubtasksId() {
        epic.addSubtaskId(subTask1.getId());
        epic.addSubtaskId(subTask2.getId());
        List<Integer> expected = List.of(subTask1.getId(), subTask2.getId());
        assertEquals(expected, epic.getSubtasksId());
    }

    @Test
    void getStatusEqualsNew() {

    }

}