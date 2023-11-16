package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
class TaskManagerControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    TaskManagerController taskManagerController;

    @Test
    void createTask() {
    }

    @Test
    void getTask() {
    }

    @Test
    void updateTask() {
    }

    @Test
    void deleteTask() {
    }

    @Test
    void createEpic() {
    }

    @Test
    void getEpic() {
    }

    @Test
    void updateEpic() {
    }

    @Test
    void deleteEpic() {
    }

    @Test
    void createSubtask() {
    }

    @Test
    void getSubtask() {
    }

    @Test
    void updateSubtask() {
    }

    @Test
    void deleteSubtask() {
    }
}