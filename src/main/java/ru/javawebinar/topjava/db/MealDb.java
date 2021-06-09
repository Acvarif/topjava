package ru.javawebinar.topjava.db;

import ru.javawebinar.topjava.model.Meal;

import java.util.HashMap;
import java.util.Map;

public class MealDb {

    private static final Map<Integer, Meal> mealMap = new HashMap<>();

    public static Map<Integer, Meal> getMealMap() {
        return mealMap;
    }
}
