package ru.javawebinar.topjava.db;

import ru.javawebinar.topjava.model.Meal;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MealDb {

    private static final Map<Integer, Meal> mealMap = new ConcurrentHashMap<>();

    public static Map<Integer, Meal> getMealMap() {
        return mealMap;
    }
}
