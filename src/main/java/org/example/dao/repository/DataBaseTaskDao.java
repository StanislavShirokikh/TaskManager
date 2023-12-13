package org.example.dao.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dao.exceptions.EpicNotFoundException;
import org.example.dao.exceptions.SubTaskNotFoundException;
import org.example.dao.exceptions.TaskNotFoundException;
import org.example.dao.inMemory.TaskDao;
import org.example.entity.Epic;
import org.example.entity.SubTask;
import org.example.entity.Task;
import org.example.mappers.EpicRowMapper;
import org.example.mappers.SubtaskRowMapper;
import org.example.mappers.TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
@ConditionalOnProperty(name = "app.storage.type", havingValue = "DATABASE")
@Slf4j
public class DataBaseTaskDao implements TaskDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleTaskInsert;
    private final SimpleJdbcInsert simpleEpicInsert;
    private final SimpleJdbcInsert simpleSubtaskInsert;

    @Autowired
    public DataBaseTaskDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleTaskInsert = new SimpleJdbcInsert(jdbcTemplate){{
            withTableName("task");
            usingGeneratedKeyColumns("id");
        }};
        this.simpleEpicInsert = new SimpleJdbcInsert(jdbcTemplate){{
            withTableName("epic");
            usingGeneratedKeyColumns("id");
        }};
        this.simpleSubtaskInsert = new SimpleJdbcInsert(jdbcTemplate){{
            withTableName("subtask");
            usingGeneratedKeyColumns("id");
        }};
    }

    @Override
    public int saveTask(Task task) {
        Map<String, Object> map = new HashMap<>(){{
            put("name", task.getName());
            put("description", task.getDescription());
            put("status_id", 1);
        }};

        return simpleTaskInsert.executeAndReturnKey(map).intValue();
    }

    @Override
    public int saveEpic(Epic epic) {
        Map<String, Object> map = new HashMap<>(){{
            put("name", epic.getName());
            put("description", epic.getDescription());
        }};
        return simpleEpicInsert.executeAndReturnKey(map).intValue();
    }

    @Override
    public int saveSubtask(SubTask subTask) {
        Map<String, Object> map = new HashMap<>(){{
            put("name", subTask.getName());
            put("description", subTask.getDescription());
            put("status_id", 1);
            put("epic_id", subTask.getEpicId());
        }};
        return simpleSubtaskInsert.executeAndReturnKey(map).intValue();
    }

    @Override
    public Task getTaskById(int id) {
        String sql = "SELECT task.id, task.name, description, status.name status_name FROM task JOIN status " +
                "ON task.status_id = status.id  WHERE task.id=?";
        Task task = null;
        try {
            task = jdbcTemplate.queryForObject(sql, new TaskMapper(), id);
        } catch (Exception e) {
            log.info("Task with id {} not found", id);
        }
        return task;
    }

    @Override
    public Epic getEpicById(int id) {
        String sql = "SELECT id, name, description FROM epic WHERE id=?";
        Epic epic = null;
        try {
            epic = jdbcTemplate.queryForObject(sql, new EpicRowMapper(), id);
            epic.setSubtasksId(getSubtaskIdByEpicId(id));

        } catch (Exception e) {
            log.info("Epic with id {} not found", id);
        }
        return epic;
    }

    @Override
    public SubTask getSubTasksById(int id) {
        String sql = "SELECT subtask.id, subtask.name, subtask.description, subtask.epic_id, status.name status_name " +
                "FROM subtask JOIN status ON subtask.status_id = status.id WHERE subtask.id=?";
        SubTask subTask = null;
        try {
            subTask = jdbcTemplate.queryForObject(sql, new SubtaskRowMapper(), id);
        } catch (Exception e) {
            log.info("Subtask with id {} not found", id);
        }
        return subTask;
    }

    @Override
    public void updateTask(Task task) {
        if (doesTaskIdExist(task.getId())) {
            jdbcTemplate.update("UPDATE task SET name=?, description=?, status_id=(SELECT id FROM status WHERE name=?) " +
                            "WHERE id=?",
                    task.getName(), task.getDescription(), String.valueOf(task.getStatus()), task.getId());
        } else {
            throw new TaskNotFoundException("Вы не можете обновить Task с несуществующим Id");
        }
    }

    @Override
    public void updateEpic(Epic epic) {
        if (doesEpicIdExist(epic.getId())) {
            String sql = "UPDATE epic SET name=?, description=? WHERE id=?";
            jdbcTemplate.update(sql, epic.getName(), epic.getDescription(), epic.getId());
        } else {
            throw new EpicNotFoundException("Вы не можете обновить Epic с несуществующим Id");
        }
    }

    @Override
    public void updateSubTask(SubTask subTask) {
        if (doesSubtaskIdExist(subTask.getId())) {
            String sql = "UPDATE subtask SET name=?, description=?, status_id=(SELECT id FROM status WHERE name=?)," +
                    " epic_id=? WHERE id=? ";
            jdbcTemplate.update(sql, subTask.getName(), subTask.getDescription(), String.valueOf(subTask.getStatus()), subTask.getEpicId(),
                    subTask.getId());
        } else {
            throw new SubTaskNotFoundException("Вы не можете обновить Subtask с несуществующим Id");
        }
    }

    @Override
    public void removeTaskById(int id) {
        if (doesTaskIdExist(id)) {
            jdbcTemplate.update("DELETE FROM task WHERE id=?", id);
        } else {
            throw new TaskNotFoundException("Вы не можете удалить Task с несуществующим Id");        }
    }

    @Override
    public void removeEpicById(int id) {
        if (doesEpicIdExist(id)) {
            jdbcTemplate.update("DELETE FROM epic WHERE id=?", id);
        } else {
            throw new EpicNotFoundException("Вs не можете удалить Epic с несуществующим Id");
        }
    }

    @Override
    public void removeSubTaskById(int id) {
        if (doesSubtaskIdExist(id)) {
            jdbcTemplate.update("DELETE FROM subtask WHERE id=?", id);
        } else {
            throw new SubTaskNotFoundException("Вы не можете удалить SubTask с несуществующим Id");
        }
    }
    private List<Integer> getSubtaskIdByEpicId(int epicId) {
        return jdbcTemplate.queryForList("SELECT id FROM subtask WHERE epic_id=?", Integer.class, epicId);
    }

    private boolean doesTaskIdExist(int id) {
        int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM task WHERE id=?", Integer.class, id);
        return count == 1;
    }

    private boolean doesEpicIdExist(int id) {
        int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM epic WHERE id=?", Integer.class, id);
        return count == 1;
    }

    private boolean doesSubtaskIdExist(int id) {
        int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM subtask WHERE id=?", Integer.class, id);
        return count == 1;
    }


}
