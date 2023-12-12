package org.example.mappers;

import org.example.entity.Epic;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EpicRowMapper implements RowMapper<Epic> {
    @Override
    public Epic mapRow(ResultSet rs, int rowNum) throws SQLException {
        Epic epic = new Epic();
        epic.setId(rs.getInt("id"));
        epic.setName(rs.getString("name"));
        epic.setDescription(rs.getString("description"));

        return epic;
    }
}
