package org.example.mappers;

import org.example.dao.exceptions.SubTaskNotFoundException;
import org.example.entity.Status;
import org.example.entity.SubTask;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SubtaskRowMapper implements RowMapper<SubTask> {
    @Override
    public SubTask mapRow(ResultSet rs, int rowNum) throws SQLException {
        SubTask subTask = new SubTask();
        subTask.setId(rs.getInt("id"));
        subTask.setName(rs.getString("name"));
        subTask.setDescription(rs.getString("description"));
        subTask.setEpicId(rs.getInt("epic_id"));
        subTask.setStatus(Status.valueOf(rs.getString("status_name")));
        return subTask;
    }
}
