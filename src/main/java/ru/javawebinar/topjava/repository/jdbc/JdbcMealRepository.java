package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealRowMapper;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcMealRepository implements MealRepository {

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public JdbcMealRepository(JdbcTemplate jdbcTemplate) {
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("meals").usingGeneratedKeyColumns("id");
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
//            Number id = jdbcTemplate.update("INSERT INTO meals VALUES(?,?,?,?)",
//                    meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories());
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("id", meal.getId());
            parameters.put("dateTime", meal.getDateTime());
            parameters.put("description", meal.getDescription());
            parameters.put("calories", meal.getCalories());
            Number id = simpleJdbcInsert.executeAndReturnKey(parameters);

            meal.setId(id.intValue());
            return meal;
        } else {
            Number id = jdbcTemplate.update("UPDATE meals SET dateTime=?, description=?, calories=? WHERE id=?",
                    meal.getDateTime(), meal.getDescription(), meal.getCalories(), meal.getId());
            meal.setId(id.intValue());
            return meal;
        }
    }

    @Override
    public boolean delete(int id, int userId) {
        return jdbcTemplate.update("DELETE FROM meals WHERE id = ?", id) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        return jdbcTemplate.query("SELECT * FROM meals WHERE id=?", new MealRowMapper(), id)
                .stream().findAny().orElse(null);
    }

    @Override
    public List<Meal> getAll(int userId) {
        List<Meal> meals = jdbcTemplate.query("SELECT * FROM meals", new MealRowMapper());
        return meals;
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        List<Meal> meals = jdbcTemplate.query(
                "SELECT * FROM meals WHERE dateTime>=? AND dateTime<? ORDER BY dateTime DESC",
                new MealRowMapper(), startDateTime, endDateTime);
        return meals;
    }

// https://alexkosarev.name/2019/05/04/spring-framework-simple-jdbc-insert/

//    public List<Person> index() {
//        return jdbcTemplate.query("SELECT * FROM person", new BeanPropertyRowMapper<>(Person.class));
//    }
//
//    public Person show(int id){
//        return jdbcTemplate.query("SELECT * FROM person WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
//                .stream().findAny().orElse(null);
//    }
//
//    public void save(Person person){
//        jdbcTemplate.update("INSERT INTO person (name, age, email) VALUES (?, ?, ?)", person.getName(), person.getAge(), person.getEmail());
//    }
//
//    public void edit(Integer id, Person person) {
//        jdbcTemplate.update("UPDATE person SET name = ?, age = ?, email = ? WHERE id = ?", person.getName(), person.getAge(), person.getEmail(), id);
//    }
//
//    public void delete(Integer id) {
//        jdbcTemplate.update("DELETE FROM person WHERE id = ?", id);
//    }
}
