package org.example.dao.repository;

import org.example.dao.in_memory.TaskDao;
import org.example.entity.Epic;
import org.example.entity.SubTask;
import org.example.entity.Task;
import org.example.mappers.TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DataBaseTaskDao implements TaskDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DataBaseTaskDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int saveTask(Task task) {
        String sql = "INSERT INTO tasks (name, description, status_id) VALUES(?, ?, ?)";
        jdbcTemplate.update(sql, task.getName(), task.getDescription(), 1);
        return task.getId();
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
        String sql = "SELECT FROM tasks WHERE id=?";
        return jdbcTemplate.query(sql, new Object[]{id}, new TaskMapper())
                .stream().findAny().orElse(null);
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
        jdbcTemplate.update("UPDATE tasks SET name=?, description=?, status_id=? WHERE id=?", task.getId(),
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
        jdbcTemplate.update("DELETE FROM tasks WHERE id=?", id);
    }

    @Override
    public void removeEpicById(int id) {

    }

    @Override
    public void removeSubTaskById(int id) {

    }
}
