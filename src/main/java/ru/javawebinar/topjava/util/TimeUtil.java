package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalTime;

public class TimeUtil {
    public static boolean isBetweenHalfOpen(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) < 0;
    }

    public static boolean isBetweenHalfOpenStream(LocalTime end, LocalTime start, UserMeal meal) {
        return meal.getDateTime().toLocalTime().isBefore(end) && meal.getDateTime().toLocalTime().isAfter(start);
    }
}
