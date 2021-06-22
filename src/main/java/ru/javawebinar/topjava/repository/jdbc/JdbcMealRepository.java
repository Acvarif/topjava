package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DbUtil;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcMealRepository implements MealRepository {

    private final Connection connection;

    @Autowired
    public JdbcMealRepository() {
        connection = DbUtil.getConnection();
    }


    @Override
    public Meal save(Meal meal, int userId) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("insert into meals(dateTime, description, calories) values (?, ?, ? )");
            // Parameters start with 1
//            preparedStatement.setTimestamp(1, meal.getDateTime().toString());
            preparedStatement.setString(2, meal.getDescription());
//            preparedStatement.setDate(3, new java.sql.Date(user.getDob().getTime()));
            preparedStatement.setInt(3, meal.getCalories());
//            preparedStatement.executeUpdate();
            int row = preparedStatement.executeUpdate();
            System.out.println(row);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean delete(int id, int userId) {
        return false;
    }

    @Override
    public Meal get(int id, int userId) {
        return null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        List<Meal> meals = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from meals");
            while (rs.next()) {
                Meal meal = new Meal(rs.getInt("id"),
                        rs.getTimestamp("dateTime").toLocalDateTime(),
                        rs.getString("description"), rs.getInt("calories"));
                meals.add(meal);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return meals;
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return null;
    }
}
