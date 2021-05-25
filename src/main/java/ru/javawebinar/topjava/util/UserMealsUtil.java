package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

        List<UserMealWithExcess> mealsToStream = filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsToStream.forEach(System.out::println);

//        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));

    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        /* total calories per day for days of month 30 and 31 */
        int calories30 = 0;
        int calories31 = 0;
        for (UserMeal userMeal : meals) {
            if (userMeal.getDateTime().getMonth() == Month.JANUARY && userMeal.getDateTime().getYear() == 2020) {
                if (userMeal.getDateTime().getDayOfMonth() == 30) {
                    calories30 = calories30 + userMeal.getCalories();
                } else if (userMeal.getDateTime().getDayOfMonth() == 31) {
                    calories31 = calories31 + userMeal.getCalories();
                }
            }
        }

        boolean excess = true;
        List<UserMealWithExcess> userMealWithExcesses = new ArrayList<>();
        for (UserMeal userMeal : meals) {
            LocalDateTime dateTime = userMeal.getDateTime().withMinute(userMeal.getDateTime().getMinute());
            String description = userMeal.getDescription();

            /* entries between `startTime` and` endTime` with minutes */
            if (TimeUtil.isBetweenHalfOpen(dateTime.toLocalTime(), startTime, endTime)) {
                if (userMeal.getDateTime().getDayOfMonth() == 30) {
                    System.out.println("calories30 " + calories30);
                    System.out.println("calories31 " + calories31);
                    if (calories30 > caloriesPerDay) {
                        excess = true;
                    } else {
                        excess = false;
                    }
                } else if (userMeal.getDateTime().getDayOfMonth() == 31) {
                    if (calories31 > caloriesPerDay) {
                        excess = true;
                    } else {
                        excess = false;
                    }
                }
                UserMealWithExcess userMealWithExcess = new UserMealWithExcess(dateTime, description, userMeal.getCalories(), excess);
                userMealWithExcesses.add(userMealWithExcess);
            }
        }

        return userMealWithExcesses;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        /* total calories per day for days 30 and 31 */
        int calories30stream = 0;
        int calories31stream = 0;
        calories30stream = meals.stream()
                .filter(i -> i.getDateTime().getDayOfYear() == 30)
                .reduce(0, (partialResult, calorie) -> partialResult + calorie.getCalories(), Integer::sum);
        System.out.println("calories30stream " + calories30stream);
        calories31stream = meals.stream()
                .filter(i -> i.getDateTime().getDayOfYear() == 31)
                .reduce(0, (partialResult, calorie) -> partialResult + calorie.getCalories(), Integer::sum);
        System.out.println("calories31stream " + calories31stream);

        List<UserMeal> userMeals = meals.stream()
                .filter(i -> i.getDateTime().getHour() >= startTime.getHour())
                .filter(i -> i.getDateTime().getHour() < endTime.getHour())
                .collect(Collectors.toList());

        boolean excess = false;
        List<UserMealWithExcess> userMealWithExcesses = new ArrayList<>();
        for (UserMeal userMeal : userMeals) {
            LocalDateTime dateTime = userMeal.getDateTime().withMinute(userMeal.getDateTime().getMinute());
            String description = userMeal.getDescription();

            /* entries between `startTime` and` endTime` with minutes */
            if (userMeal.getDateTime().getDayOfMonth() == 30) {
                if (calories30stream > caloriesPerDay) {
                    excess = true;
                } else {
                    excess = false;
                }
            } else if (userMeal.getDateTime().getDayOfMonth() == 31) {
                if (calories31stream > caloriesPerDay) {
                    excess = true;
                } else {
                    excess = false;
                }
            }
            UserMealWithExcess userMealWithExcess = new UserMealWithExcess(dateTime, description, userMeal.getCalories(), excess);
            userMealWithExcesses.add(userMealWithExcess);
        }
        return userMealWithExcesses;
    }

}
