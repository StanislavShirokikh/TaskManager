package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.example.controller.requests.CreateEpicRequest;
import org.example.controller.requests.CreateSubtaskRequest;
import org.example.controller.requests.CreateTaskRequest;
import org.example.controller.requests.UpdateEpicRequest;
import org.example.controller.requests.UpdateSubtaskRequest;
import org.example.controller.requests.UpdateTaskRequest;
import org.example.dto.Epic;
import org.example.dto.SaveEpicDto;
import org.example.dto.SaveSubTaskDto;
import org.example.dto.SaveTaskDto;
import org.example.dto.Status;
import org.example.dto.SubTask;
import org.example.dto.Task;
import org.example.service.manager.Manager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
    private ObjectMapper objectMapper;


    @Test
    public void getTaskById() throws Exception {
        SaveTaskDto saveTaskDto = new SaveTaskDto();
        saveTaskDto.setName("task");
        saveTaskDto.setDescription("task description");
        int taskId = manager.saveTask(saveTaskDto);

        mockMvc.perform(get("/task-manager/task/get/")
                .param("id", String.valueOf(taskId)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(saveTaskDto.getName()))
                .andExpect(jsonPath("$.description").value(saveTaskDto.getDescription()))
                .andExpect(jsonPath("$.id").value(taskId))
                .andExpect(jsonPath("$.status").value(String.valueOf(Status.NEW)));
    }

    @Test
    public void getTaskWithBadId() throws Exception {
        mockMvc.perform(get("/task-manager/task/get/")
                .param("id", "-7"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void createTask() throws Exception {
        CreateTaskRequest createTaskRequest = new CreateTaskRequest();
        createTaskRequest.setName("task");
        createTaskRequest.setDescription("task description");

        MvcResult result = mockMvc.perform(post("/task-manager/task/create")
                .content(objectMapper.writeValueAsString(createTaskRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andReturn();

        int id = JsonPath.read(result.getResponse().getContentAsString(), "$.id");

        Task task = manager.getTaskById(id);
        Assertions.assertEquals(createTaskRequest.getName(), task.getName());
        Assertions.assertEquals(createTaskRequest.getDescription(), task.getDescription());
        Assertions.assertEquals(id, task.getId());
        Assertions.assertEquals(Status.NEW, task.getStatus());
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
                .andExpect(status().isOk());

        Task task = manager.getTaskById(taskId);
        Assertions.assertEquals(updateTaskRequest.getName(), task.getName());
        Assertions.assertEquals(updateTaskRequest.getDescription(), task.getDescription());
        Assertions.assertEquals(taskId, task.getId());
        Assertions.assertEquals(Status.IN_PROGRESS, task.getStatus());
    }

    @Test
    public void deleteTask() throws Exception {
        SaveTaskDto saveTaskDto = new SaveTaskDto();
        saveTaskDto.setName("task");
        saveTaskDto.setDescription("task description");
        int taskId = manager.saveTask(saveTaskDto);

        mockMvc.perform(delete("/task-manager/task/delete/")
                        .param("id", String.valueOf(taskId)))
                .andExpect(status().isOk());
        Assertions.assertNull(manager.getTaskById(taskId));
    }

    @Test
    public void createEpic() throws  Exception {
        CreateEpicRequest createEpicRequest = new CreateEpicRequest();
        createEpicRequest.setName("epic");
        createEpicRequest.setDescription("epic description");

        MvcResult result = mockMvc.perform(post("/task-manager/epic/create")
                        .content(objectMapper.writeValueAsString(createEpicRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andReturn();

        int id = JsonPath.read(result.getResponse().getContentAsString(), "$.id");

        Epic epic = manager.getEpicById(id);
        Assertions.assertEquals(createEpicRequest.getName(), epic.getName());
        Assertions.assertEquals(createEpicRequest.getDescription(), epic.getDescription());
        Assertions.assertEquals(id, epic.getId());
        Assertions.assertEquals(0, epic.getSubtasksId().size());
        Assertions.assertEquals(Status.NEW, epic.getStatus());
    }

    @Test
    public void getEpicById() throws  Exception {
        SaveEpicDto saveEpicDto = new SaveEpicDto();
        saveEpicDto.setName("epic");
        saveEpicDto.setDescription("epic description");
        int epicId = manager.saveEpic(saveEpicDto);

        SaveSubTaskDto saveSubTaskDto = new SaveSubTaskDto();
        saveSubTaskDto.setEpicId(epicId);
        saveEpicDto.setName("subtask");
        saveEpicDto.setDescription("subtask description");
        int subtaskId = manager.saveSubtask(saveSubTaskDto);

        Epic epic = manager.getEpicById(epicId);

        mockMvc.perform(get("/task-manager/epic/get/")
                        .param("id", String.valueOf(epicId)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(epic.getName()))
                .andExpect(jsonPath("$.description").value(epic.getDescription()))
                .andExpect(jsonPath("$.id").value(epic.getId()))
                .andExpect(jsonPath("$.status").value(String.valueOf(epic.getStatus())))
                .andExpect(jsonPath("$.subtasksId").value(subtaskId));
    }

    @Test
    public void updateEpic() throws  Exception {
        SaveEpicDto saveEpicDto = new SaveEpicDto();
        saveEpicDto.setName("epic");
        saveEpicDto.setDescription("epic description");
        int epicId = manager.saveEpic(saveEpicDto);

        SaveSubTaskDto saveSubTaskDto = new SaveSubTaskDto();
        saveSubTaskDto.setEpicId(epicId);
        saveEpicDto.setName("subtask");
        saveEpicDto.setDescription("subtask description");
        int subtaskId = manager.saveSubtask(saveSubTaskDto);

        UpdateEpicRequest updateEpicRequest = new UpdateEpicRequest();
        updateEpicRequest.setId(epicId);
        updateEpicRequest.setName("update epic");
        updateEpicRequest.setDescription("update epic description");

        mockMvc.perform(put("/task-manager/epic/update")
                        .content(objectMapper.writeValueAsString(updateEpicRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Epic epic = manager.getEpicById(epicId);

    }

    @Test
    public void deleteEpic() throws  Exception {
        SaveEpicDto saveEpicDto = new SaveEpicDto();
        saveEpicDto.setName("epic");
        saveEpicDto.setDescription("epic description");
        int epicId = manager.saveEpic(saveEpicDto);

        mockMvc.perform(delete("/task-manager/epic/delete/")
                        .param("id", String.valueOf(epicId)))
                .andExpect(status().isOk());
    }

    @Test
    public void createSubtask() throws Exception {
        SaveEpicDto saveEpicDto = new SaveEpicDto();
        saveEpicDto.setName("epic");
        saveEpicDto.setDescription("epic description");
        int epicId = manager.saveEpic(saveEpicDto);

        CreateSubtaskRequest createSubtaskRequest = new CreateSubtaskRequest();
        createSubtaskRequest.setName("subtask");
        createSubtaskRequest.setDescription("subtask description");
        createSubtaskRequest.setEpicId(epicId);

        mockMvc.perform(post("/task-manager/subtask/create")
                        .content(objectMapper.writeValueAsString(createSubtaskRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void getSubtaskById() throws Exception {
        SaveEpicDto saveEpicDto = new SaveEpicDto();
        saveEpicDto.setName("epic");
        saveEpicDto.setDescription("epic description");
        int epicId = manager.saveEpic(saveEpicDto);

        SaveSubTaskDto saveSubTaskDto = new SaveSubTaskDto();
        saveSubTaskDto.setName("subtask");
        saveSubTaskDto.setDescription("subtask description");
        saveSubTaskDto.setEpicId(epicId);

        int subtaskId = manager.saveSubtask(saveSubTaskDto);

        SubTask subTask = manager.getSubTasksById(subtaskId);

        mockMvc.perform(get("/task-manager/subtask/get/")
                        .param("id", String.valueOf(epicId)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(subTask.getName()))
                .andExpect(jsonPath("$.description").value(subTask.getDescription()))
                .andExpect(jsonPath("$.id").value(subTask.getId()))
                .andExpect(jsonPath("$.status").value(String.valueOf(subTask.getStatus())))
                .andExpect(jsonPath("$.epicId").value(subTask.getEpicId()));
    }

    @Test
    public void updateSubtask() throws  Exception {
        SaveEpicDto saveEpicDto = new SaveEpicDto();
        saveEpicDto.setName("epic");
        saveEpicDto.setDescription("epic description");
        int epicId = manager.saveEpic(saveEpicDto);

        SaveSubTaskDto saveSubTaskDto = new SaveSubTaskDto();
        saveSubTaskDto.setName("subtask");
        saveSubTaskDto.setDescription("subtask description");
        saveSubTaskDto.setEpicId(epicId);

        int subtaskId = manager.saveSubtask(saveSubTaskDto);

        UpdateSubtaskRequest updateSubtaskRequest = new UpdateSubtaskRequest();
        updateSubtaskRequest.setName("update subtask");
        updateSubtaskRequest.setDescription("update subtask description");
        updateSubtaskRequest.setStatus(Status.IN_PROGRESS);
        updateSubtaskRequest.setEpicId(epicId);

        mockMvc.perform(put("/task-manager/subtask/update")
                        .content(objectMapper.writeValueAsString(updateSubtaskRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        SubTask subTask = manager.getSubTasksById(subtaskId);

        Assertions.assertEquals(updateSubtaskRequest.getName(), subTask.getName());
        Assertions.assertEquals(updateSubtaskRequest.getDescription(), subTask.getDescription());
        Assertions.assertEquals(subtaskId, subTask.getId());
        Assertions.assertEquals(epicId, subTask.getEpicId());
        Assertions.assertEquals(Status.IN_PROGRESS, subTask.getStatus());
    }

    @Test
    public void deleteSubtask() throws Exception {
        SaveEpicDto saveEpicDto = new SaveEpicDto();
        saveEpicDto.setName("epic");
        saveEpicDto.setDescription("epic description");
        int epicId = manager.saveEpic(saveEpicDto);

        SaveSubTaskDto saveSubTaskDto = new SaveSubTaskDto();
        saveSubTaskDto.setName("subtask");
        saveSubTaskDto.setDescription("subtask description");
        saveSubTaskDto.setEpicId(epicId);

        int subtaskId = manager.saveSubtask(saveSubTaskDto);

        mockMvc.perform(delete("/task-manager/subtask/delete/")
                        .param("id", String.valueOf(subtaskId)))
                .andExpect(status().isOk());

        Assertions.assertNull(manager.getSubTasksById(subtaskId));
    }
}