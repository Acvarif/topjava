package ru.javawebinar.topjava.dao.impl;

import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.db.MealDb;
import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MealDaoImpl implements MealDao {

    private AtomicInteger mealAtomicId = new AtomicInteger(0);

    @Override
    public void create(Meal meal) {
        meal.setId(mealAtomicId.incrementAndGet());
        MealDb.getMealMap().put(meal.getId(), meal);
    }

    @Override
    public void update(int id, Meal meal) {
        meal.setId(id);
        MealDb.getMealMap().put(meal.getId(), meal);
    }

    @Override
    public void delete(int id) {
        MealDb.getMealMap().remove(id);
    }

    @Override
    public List<Meal> getList() {
        return new ArrayList<Meal>(MealDb.getMealMap().values());
    }
}
