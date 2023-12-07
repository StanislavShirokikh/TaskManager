package org.example.dao.repository;

import lombok.RequiredArgsConstructor;
import org.example.dao.inMemory.TaskDao;
import org.example.entity.Epic;
import org.example.entity.SubTask;
import org.example.entity.Task;
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
        return 0;
    }

    @Override
    public int saveSubtask(SubTask subTask) {
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
        return null;
    }

    @Override
    public SubTask getSubTasksById(int id) {
        return null;
    }

    @Override
    public void updateTask(Task task) {
        jdbcTemplate.update("UPDATE task SET name=?, description=?, status_id=? WHERE id=?", task.getId(),
                task.getName(), task.getDescription(), task.getStatus());
    }

    @Override
    public void updateEpic(Epic epic) {

    }

    @Override
    public void updateSubTask(SubTask subTask) {

    }

    @Override
    public void removeTaskById(int id) {
        jdbcTemplate.update("DELETE FROM task WHERE id=?", id);
    }

    @Override
    public void removeEpicById(int id) {

    }

    @Override
    public void removeSubTaskById(int id) {

    }
}
