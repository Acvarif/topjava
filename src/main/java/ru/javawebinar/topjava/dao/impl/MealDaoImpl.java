package ru.javawebinar.topjava.dao.impl;

import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class MealDaoImpl implements MealDao {

    private AtomicInteger mealAtomicId = new AtomicInteger(0);

    private Map<Integer, Meal> mealMap = new HashMap<>();

    public void createMeal(Meal meal) {
        Integer id = mealAtomicId.get() + 1;
        mealMap.put(id, meal);
    }

    public void updateMeal(Meal meal) {
        Meal mealNew = mealMap.get(meal.getId());
        mealNew.setId(meal.getId());
        mealNew.setDateTime(meal.getDateTime());
        mealNew.setDescription(meal.getDescription());
        mealNew.setCalories(meal.getCalories());
    }

    public void deleteMeal(Meal meal) {
        mealMap.remove(meal.getId());
    }

    public List<Meal> getMealList() {
        return new ArrayList<Meal>();
    }

}
