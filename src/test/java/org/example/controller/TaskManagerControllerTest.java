package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.example.controller.requests.CreateEpicRequest;
import org.example.controller.requests.CreateSubtaskRequest;
import org.example.controller.requests.CreateTaskRequest;
import org.example.controller.requests.UpdateEpicRequest;
import org.example.controller.requests.UpdateSubtaskRequest;
import org.example.controller.requests.UpdateTaskRequest;
import org.example.dto.UpdateSubTaskDto;
import org.example.entity.Epic;
import org.example.dto.SaveEpicDto;
import org.example.dto.SaveSubTaskDto;
import org.example.dto.SaveTaskDto;
import org.example.entity.Status;
import org.example.entity.SubTask;
import org.example.entity.Task;
import org.example.service.manager.Manager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

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
                        .param("id", "7"))
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
    public void createTaskWhenNameIsEmpty() throws Exception {
        CreateTaskRequest createTaskRequest = new CreateTaskRequest();
        createTaskRequest.setName("");
        createTaskRequest.setDescription("task description");

        mockMvc.perform(post("/task-manager/task/create")
                        .content(objectMapper.writeValueAsString(createTaskRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
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
    public void updateTaskWhenNameIsEmpty() throws Exception {
        SaveTaskDto saveTaskDto = new SaveTaskDto();
        saveTaskDto.setName("task");
        saveTaskDto.setDescription("task description");
        int taskId = manager.saveTask(saveTaskDto);

        UpdateTaskRequest updateTaskRequest = new UpdateTaskRequest();
        updateTaskRequest.setName("");
        updateTaskRequest.setDescription("update description");
        updateTaskRequest.setId(taskId);
        updateTaskRequest.setStatus(Status.IN_PROGRESS);

        mockMvc.perform(put("/task-manager/task/update")
                        .content(objectMapper.writeValueAsString(updateTaskRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateTaskWhenStatusIsNull() throws Exception {
        SaveTaskDto saveTaskDto = new SaveTaskDto();
        saveTaskDto.setName("task");
        saveTaskDto.setDescription("task description");
        int taskId = manager.saveTask(saveTaskDto);

        UpdateTaskRequest updateTaskRequest = new UpdateTaskRequest();
        updateTaskRequest.setName("update task");
        updateTaskRequest.setDescription("update description");
        updateTaskRequest.setId(taskId);
        updateTaskRequest.setStatus(null);

        mockMvc.perform(put("/task-manager/task/update")
                        .content(objectMapper.writeValueAsString(updateTaskRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateTaskWithBadId() throws Exception {
        UpdateTaskRequest updateTaskRequest = new UpdateTaskRequest();
        updateTaskRequest.setName("update task");
        updateTaskRequest.setDescription("updateTask description");
        updateTaskRequest.setId(7);
        updateTaskRequest.setStatus(Status.IN_PROGRESS);

        mockMvc.perform(put("/task-manager/task/update")
                        .content(objectMapper.writeValueAsString(updateTaskRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Entity with this id not found"));
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
    public void deleteTasWithBadId() throws Exception {
        SaveTaskDto saveTaskDto = new SaveTaskDto();
        saveTaskDto.setName("task");
        saveTaskDto.setDescription("task description");
        manager.saveTask(saveTaskDto);

        mockMvc.perform(delete("/task-manager/task/delete/")
                        .param("id", String.valueOf(7)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Entity with this id not found"));


    }

    @Test
    public void createEpic() throws Exception {
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
    public void createEpicWhenNameIsEmpty() throws Exception {
        CreateEpicRequest createEpicRequest = new CreateEpicRequest();
        createEpicRequest.setName("");
        createEpicRequest.setDescription("epic description");

        mockMvc.perform(post("/task-manager/epic/create")
                        .content(objectMapper.writeValueAsString(createEpicRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getEpicById() throws Exception {
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
    public void getEpicWithBadId() throws Exception {
        mockMvc.perform(get("/task-manager/epic/get/")
                        .param("id", String.valueOf(Integer.MAX_VALUE)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateEpic() throws Exception {
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

        Assertions.assertEquals(updateEpicRequest.getName(), epic.getName());
        Assertions.assertEquals(updateEpicRequest.getDescription(), epic.getDescription());
        Assertions.assertEquals(epicId, epic.getId());
        Assertions.assertEquals(Status.NEW, epic.getStatus());

        List<Integer> actualList = epic.getSubtasksId();

        Assertions.assertTrue(actualList.contains(subtaskId));
        Assertions.assertEquals(1, actualList.size());
    }

    @Test
    public void updateEpicWithBadId() throws Exception {
        UpdateEpicRequest updateEpicRequest = new UpdateEpicRequest();
        updateEpicRequest.setId(7);
        updateEpicRequest.setName("update epic");
        updateEpicRequest.setDescription("update epic description");

        mockMvc.perform(put("/task-manager/epic/update")
                        .content(objectMapper.writeValueAsString(updateEpicRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Entity with this id not found"));
    }

    @Test
    public void updateEpicWhenNameIsEmpty() throws Exception {
        SaveEpicDto saveEpicDto = new SaveEpicDto();
        saveEpicDto.setName("epic");
        saveEpicDto.setDescription("epic description");
        int epicId = manager.saveEpic(saveEpicDto);

        UpdateEpicRequest updateEpicRequest = new UpdateEpicRequest();
        updateEpicRequest.setId(epicId);
        updateEpicRequest.setName("");
        updateEpicRequest.setDescription("update epic description");

        mockMvc.perform(put("/task-manager/epic/update")
                        .content(objectMapper.writeValueAsString(updateEpicRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteEpic() throws Exception {
        SaveEpicDto saveEpicDto = new SaveEpicDto();
        saveEpicDto.setName("epic");
        saveEpicDto.setDescription("epic description");
        int epicId = manager.saveEpic(saveEpicDto);

        SaveSubTaskDto saveSubTaskDto = new SaveSubTaskDto();
        saveSubTaskDto.setEpicId(epicId);
        saveSubTaskDto.setName("subtask 1");
        saveSubTaskDto.setDescription("subtask description 1");
        int subtaskId = manager.saveSubtask(saveSubTaskDto);

        mockMvc.perform(delete("/task-manager/epic/delete/")
                        .param("id", String.valueOf(epicId)))
                .andExpect(status().isOk());

        Assertions.assertNull(manager.getSubTasksById(subtaskId));
        Assertions.assertNull(manager.getEpicById(epicId));
    }

    @Test
    public void deleteEpicWithBadId() throws Exception {
        mockMvc.perform(delete("/task-manager/epic/delete/")
                        .param("id", String.valueOf(5)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Entity with this id not found"));
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

        MvcResult result = mockMvc.perform(post("/task-manager/subtask/create")
                        .content(objectMapper.writeValueAsString(createSubtaskRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andReturn();

        int id = JsonPath.read(result.getResponse().getContentAsString(), "$.id");

        SubTask subTask = manager.getSubTasksById(id);

        Assertions.assertEquals(createSubtaskRequest.getName(), subTask.getName());
        Assertions.assertEquals(createSubtaskRequest.getDescription(), subTask.getDescription());
        Assertions.assertEquals(createSubtaskRequest.getEpicId(), subTask.getEpicId());
        Assertions.assertEquals(id, subTask.getId());
        Assertions.assertEquals(Status.NEW, subTask.getStatus());
    }

    @Test
    public void createSubtaskWhenNameIsEmpty() throws Exception {
        SaveEpicDto saveEpicDto = new SaveEpicDto();
        saveEpicDto.setName("epic");
        saveEpicDto.setDescription("epic description");
        int epicId = manager.saveEpic(saveEpicDto);

        CreateSubtaskRequest createSubtaskRequest = new CreateSubtaskRequest();
        createSubtaskRequest.setName("");
        createSubtaskRequest.setDescription("subtask description");
        createSubtaskRequest.setEpicId(epicId);

        mockMvc.perform(post("/task-manager/subtask/create")
                        .content(objectMapper.writeValueAsString(createSubtaskRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createSubtaskWhenEpicIdIsNull() throws Exception {
        CreateSubtaskRequest createSubtaskRequest = new CreateSubtaskRequest();
        createSubtaskRequest.setName("subtask");
        createSubtaskRequest.setDescription("subtask description");
        createSubtaskRequest.setEpicId(null);

        mockMvc.perform(post("/task-manager/subtask/create")
                        .content(objectMapper.writeValueAsString(createSubtaskRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
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
                        .param("id", String.valueOf(subtaskId)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(subTask.getName()))
                .andExpect(jsonPath("$.description").value(subTask.getDescription()))
                .andExpect(jsonPath("$.id").value(subTask.getId()))
                .andExpect(jsonPath("$.status").value(String.valueOf(subTask.getStatus())))
                .andExpect(jsonPath("$.epicId").value(subTask.getEpicId()));
    }

    @Test
    public void getSubtaskWithBadId() throws Exception {
        mockMvc.perform(get("/task-manager/subtask/get/")
                        .param("id", "7"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateSubtask() throws Exception {
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
        updateSubtaskRequest.setId(subtaskId);

        mockMvc.perform(put("/task-manager/subtask/update")
                        .content(objectMapper.writeValueAsString(updateSubtaskRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        SubTask subTask = manager.getSubTasksById(subtaskId);
        Epic epic = manager.getEpicById(epicId);

        Assertions.assertEquals(updateSubtaskRequest.getName(), subTask.getName());
        Assertions.assertEquals(updateSubtaskRequest.getDescription(), subTask.getDescription());
        Assertions.assertEquals(subtaskId, subTask.getId());
        Assertions.assertEquals(epicId, subTask.getEpicId());
        Assertions.assertEquals(Status.IN_PROGRESS, subTask.getStatus());
        Assertions.assertEquals(Status.IN_PROGRESS, epic.getStatus());
    }

    @Test
    public void updateSubtaskWithBadId() throws Exception {
        SaveEpicDto saveEpicDto = new SaveEpicDto();
        saveEpicDto.setName("epic");
        saveEpicDto.setDescription("epic description");
        int epicId = manager.saveEpic(saveEpicDto);

        UpdateSubtaskRequest updateSubtaskRequest = new UpdateSubtaskRequest();
        updateSubtaskRequest.setName("update subtask");
        updateSubtaskRequest.setDescription("update subtask description");
        updateSubtaskRequest.setStatus(Status.IN_PROGRESS);
        updateSubtaskRequest.setEpicId(epicId);
        updateSubtaskRequest.setId(7);

        mockMvc.perform(put("/task-manager/subtask/update")
                        .content(objectMapper.writeValueAsString(updateSubtaskRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Entity with this id not found"));
    }

    @Test
    public void updateSubtaskWhenNameIsEmpty() throws Exception {
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
        updateSubtaskRequest.setName("");
        updateSubtaskRequest.setDescription("update subtask description");
        updateSubtaskRequest.setStatus(Status.IN_PROGRESS);
        updateSubtaskRequest.setEpicId(epicId);
        updateSubtaskRequest.setId(subtaskId);

        mockMvc.perform(put("/task-manager/subtask/update")
                        .content(objectMapper.writeValueAsString(updateSubtaskRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateSubtaskWhenEpicIdIsNull() throws Exception {
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
        updateSubtaskRequest.setEpicId(null);
        updateSubtaskRequest.setId(subtaskId);

        mockMvc.perform(put("/task-manager/subtask/update")
                        .content(objectMapper.writeValueAsString(updateSubtaskRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateSubtaskWhenStatusIsNull() throws Exception {
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
        updateSubtaskRequest.setStatus(null);
        updateSubtaskRequest.setEpicId(epicId);
        updateSubtaskRequest.setId(subtaskId);

        mockMvc.perform(put("/task-manager/subtask/update")
                        .content(objectMapper.writeValueAsString(updateSubtaskRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteSubtask() throws Exception {
        SaveEpicDto saveEpicDto = new SaveEpicDto();
        saveEpicDto.setName("epic");
        saveEpicDto.setDescription("epic description");
        int epicId = manager.saveEpic(saveEpicDto);

        SaveSubTaskDto saveSubTaskDto1 = new SaveSubTaskDto();
        saveSubTaskDto1.setName("subtask");
        saveSubTaskDto1.setDescription("subtask description");
        saveSubTaskDto1.setEpicId(epicId);

        SaveSubTaskDto saveSubTaskDto2 = new SaveSubTaskDto();
        saveSubTaskDto2.setName("subtask");
        saveSubTaskDto2.setDescription("subtask description");
        saveSubTaskDto2.setEpicId(epicId);

        int subtaskId1 = manager.saveSubtask(saveSubTaskDto1);
        manager.saveSubtask(saveSubTaskDto2);

        UpdateSubTaskDto updateSubTaskDtoInProgress = new UpdateSubTaskDto();
        updateSubTaskDtoInProgress.setStatus(Status.IN_PROGRESS);
        updateSubTaskDtoInProgress.setName("subtask update");
        updateSubTaskDtoInProgress.setDescription("description subtask");
        updateSubTaskDtoInProgress.setId(subtaskId1);

        manager.updateSubTask(updateSubTaskDtoInProgress);

        mockMvc.perform(delete("/task-manager/subtask/delete/")
                        .param("id", String.valueOf(subtaskId1)))
                .andExpect(status().isOk());

        Epic epic = manager.getEpicById(epicId);

        Assertions.assertNull(manager.getSubTasksById(subtaskId1));
        Assertions.assertEquals(Status.NEW, epic.getStatus());
    }

    @Test
    public void deleteSubtaskWithBadId() throws Exception {
        mockMvc.perform(delete("/task-manager/subtask/delete/")
                        .param("id", String.valueOf(4)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Entity with this id not found"));
    }
}