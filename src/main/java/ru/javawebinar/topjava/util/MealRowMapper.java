package ru.javawebinar.topjava.util;

import org.springframework.jdbc.core.RowMapper;
import ru.javawebinar.topjava.model.Meal;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MealRowMapper implements RowMapper<Meal> {

    @Override
    public Meal mapRow(ResultSet rs, int rowNum) throws SQLException {

        Meal meal = new Meal(rs.getInt("id"),
                rs.getTimestamp("dateTime").toLocalDateTime(),
                rs.getString("description"), rs.getInt("calories"));
        return meal;
    }
}
