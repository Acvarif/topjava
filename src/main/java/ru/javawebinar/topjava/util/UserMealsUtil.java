package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

        System.out.println("-------------");

//        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with excess. Implement by cycles

        List<UserMealWithExcess> userMealWithExcesses = new ArrayList<>();
        boolean excess;
        for (UserMeal userMeal : meals) {
            LocalDateTime dateTime = userMeal.getDateTime();
            String description = userMeal.getDescription();
            int calories = userMeal.getCalories();
            if(userMeal.getCalories() > caloriesPerDay)
               excess = true;
            else
               excess = false;
            if(userMeal.getDateTime().getHour() >= startTime.getHour() && userMeal.getDateTime().getHour() < endTime.getHour()) {
               UserMealWithExcess userMealWithExcess = new UserMealWithExcess(dateTime, description, calories, excess);
               userMealWithExcesses.add(userMealWithExcess);
            }
        }
        return userMealWithExcesses;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO Implement by streams

        List<UserMealWithExcess> userMealWithExcesses = new ArrayList<>();
        boolean excess = false;
        meals.stream()
                .filter(i -> i.getDateTime().getHour() >= startTime.getHour())
                .filter(i -> i.getDateTime().getHour() < endTime.getHour());

        userMealWithExcesses.add(meals, excess);

        return userMealWithExcesses;
    }
}
