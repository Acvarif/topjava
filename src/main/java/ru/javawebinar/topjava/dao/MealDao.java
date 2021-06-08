package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealDao {

    void createMeal(Meal meal);
    void updateMeal(Meal meal);
    void deleteMeal(Meal meal);
    List<Meal> getMealList();
}
