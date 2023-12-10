package org.example.dao.repository;

import lombok.RequiredArgsConstructor;
import org.example.dao.inMemory.TaskDao;
import org.example.entity.Epic;
import org.example.entity.Status;
import org.example.entity.SubTask;
import org.example.entity.Task;
import org.example.mappers.EpicRowMapper;
import org.example.mappers.SubtaskRowMapper;
import org.example.mappers.TaskMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@ConditionalOnProperty(name = "app.storage.type", havingValue = "DATABASE")
public class DataBaseTaskDao implements TaskDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public int saveTask(Task task) {
        String sql = "INSERT INTO task (name, description, status_id) VALUES(?, ?, ?)";
        jdbcTemplate.update(sql, task.getName(), task.getDescription(), 1);
        return 0;//TODO вернуть id из базы данных
    }

    @Override
    public int saveEpic(Epic epic) {
        String sql = "INSERT INTO epic (name, description) VALUES(?, ?)";
        jdbcTemplate.update(sql, epic.getName(), epic.getDescription());
        return 0;
    }

    @Override
    public int saveSubtask(SubTask subTask) {
        String sql = "INSERT INTO subtask (name, description, epic_id, status_id) VALUES(?, ?, ?, ?)";
        jdbcTemplate.update(sql, subTask.getName(), subTask.getDescription(), subTask.getEpicId(), 1);
        return 0;
    }

    @Override
    public Task getTaskById(int id) {
        String sql = "SELECT task.id, task.name, description, status.name status_name FROM task JOIN status " +
                "ON task.status_id = status.id  WHERE task.id=?";
        return jdbcTemplate.queryForObject(sql, new TaskMapper(), id);
    }

    @Override
    public Epic getEpicById(int id) {
        String sql = ;
        return jdbcTemplate.queryForObject(sql, new EpicRowMapper(), id, id);
    }

    @Override
    public SubTask getSubTasksById(int id) {
        String sql = "SELECT subtask.id, subtask.name, subtask.description, subtask.epic_id, status.name status_name " +
                "FROM subtask JOIN status ON subtask.status_id = status.id WHERE subtask.id=?";
        return jdbcTemplate.queryForObject(sql, new SubtaskRowMapper(), id);
    }

    @Override
    public void updateTask(Task task) {
        int statusId = 0;
        if (task.getStatus().equals(Status.NEW)) {
            statusId = 1;
        } else if (task.getStatus().equals(Status.IN_PROGRESS)) {
            statusId = 2;
        }  else if (task.getStatus().equals(Status.DONE)) {
            statusId = 3;
        }
        jdbcTemplate.update("UPDATE task SET name=?, description=?, status_id=? WHERE id=?",
                task.getName(), task.getDescription(), statusId, task.getId());
    }

    @Override
    public void updateEpic(Epic epic) {
        String sql = "UPDATE epic SET name=?, description=? WHERE id=?";
        jdbcTemplate.update(sql, epic.getName(), epic.getDescription(), epic.getId());
    }

    @Override
    public void updateSubTask(SubTask subTask) {
        int statusId = 0;
        if (subTask.getStatus().equals(Status.NEW)) {
            statusId = 1;
        } else if (subTask.getStatus().equals(Status.IN_PROGRESS)) {
            statusId = 2;
        }  else if (subTask.getStatus().equals(Status.DONE)) {
            statusId = 3;
        }
        String sql = "UPDATE subtask SET name=?, description=?, status_id=?, epic_id=? WHERE id=? ";
        jdbcTemplate.update(sql, subTask.getName(), subTask.getDescription(), statusId, subTask.getEpicId(),
                subTask.getId());
    }

    @Override
    public void removeTaskById(int id) {
        jdbcTemplate.update("DELETE FROM task WHERE id=?", id);
    }

    @Override
    public void removeEpicById(int id) {
        String sql1 = "DELETE FROM subtask WHERE epic_id=?";
        jdbcTemplate.update(sql1, id);
    }

    @Override
    public void removeSubTaskById(int id) {
        String sql = "DELETE FROM subtask WHERE id=?";
        jdbcTemplate.update(sql, id);
    }
}
