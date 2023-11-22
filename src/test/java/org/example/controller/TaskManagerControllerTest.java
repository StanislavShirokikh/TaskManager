package org.example.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.controller.requests.CreateTaskRequest;
import org.example.controller.requests.UpdateTaskRequest;
import org.example.dto.SaveTaskDto;
import org.example.dto.Status;
import org.example.dto.Task;
import org.example.dto.UpdateTaskDto;
import org.example.service.manager.Manager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class TaskManagerControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private Manager manager;
    @Autowired
    ObjectMapper objectMapper;


    @Test
    public void getTaskById() throws Exception {
        SaveTaskDto saveTaskDto = new SaveTaskDto();
        saveTaskDto.setName("task");
        saveTaskDto.setDescription("task description");
        int taskId = manager.saveTask(saveTaskDto);
        Task task = manager.getTaskById(taskId);

        mockMvc.perform(get("/task-manager/task/get/")
                .param("id", String.valueOf(taskId)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(task.getName()))
                .andExpect(jsonPath("$.description").value(task.getDescription()))
                .andExpect(jsonPath("$.id").value(task.getId()))
                .andExpect(jsonPath("$.status").value(String.valueOf(task.getStatus())));
    }

    @Test
    public void getTaskWithBadId() throws Exception {
        mockMvc.perform(get("/task-manager/task/get/")
                .param("id", "-7"))
                .andExpect(status().isOk());
    }

    @Test
    public void createTask() throws Exception {
        CreateTaskRequest createTaskRequest = new CreateTaskRequest();
        createTaskRequest.setName("task");
        createTaskRequest.setDescription("task description");

        mockMvc.perform(post("/task-manager/task/create")
                .content(objectMapper.writeValueAsString(createTaskRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void updateTask() throws Exception {
        SaveTaskDto saveTaskDto = new SaveTaskDto();
        saveTaskDto.setName("task");
        saveTaskDto.setDescription("task description");
        int taskId = manager.saveTask(saveTaskDto);
        UpdateTaskRequest updateTaskRequest = new UpdateTaskRequest();
        updateTaskRequest.setName("update task");
        updateTaskRequest.setDescription("update description");
        updateTaskRequest.setId(taskId);
        updateTaskRequest.setStatus(Status.IN_PROGRESS);

        mockMvc.perform(put("/task-manager/task/update")
                .content(objectMapper.writeValueAsString(updateTaskRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }
}