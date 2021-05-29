package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
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

        System.out.println("filteredByCycles() --- ");

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

        System.out.println("filteredByStreams() --- ");

//        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
        List<UserMealWithExcess> mealsToStream = filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsToStream.forEach(System.out::println);

    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        /* HashMap LocalDate = sum calories*/
        HashMap<LocalDate, Integer> hashMap = new HashMap<>();
        for (UserMeal userMeal : meals) {
            if (hashMap.containsKey(userMeal.getDateTime().toLocalDate())) {
                hashMap.put(userMeal.getDateTime().toLocalDate(),
                        hashMap.get(userMeal.getDateTime().toLocalDate()) + userMeal.getCalories());
            } else {
                hashMap.put(userMeal.getDateTime().toLocalDate(), userMeal.getCalories());
            }
        }

        List<UserMealWithExcess> userMealWithExcesses = new ArrayList<>();
        for (UserMeal userMeal : meals) {

            /* entries between `startTime` and` endTime` with minutes */
            if (TimeUtil.isBetweenHalfOpen(userMeal.getDateTime().toLocalTime(), startTime, endTime)) {
                UserMealWithExcess userMealWithExcess = new UserMealWithExcess(userMeal.getDateTime(),
                        userMeal.getDescription(),
                        userMeal.getCalories(),
                        hashMap.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay);
                userMealWithExcesses.add(userMealWithExcess);
            }
        }
        return userMealWithExcesses;
    }

//----------------------------------------------------------------------------------------------------------------------

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        /* HashMap LocalDate = sum calories*/
        Map<LocalDate, Integer> sumCalories = meals.stream()
                .collect(Collectors.groupingBy(meal -> meal.getDateTime().toLocalDate(),
                        Collectors.summingInt(UserMeal::getCalories)));

        /* filter for List<UserMeal> between startTime, endTime */
        List<UserMeal> userMeals = meals.stream()
                .filter(meal -> TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime))
                .collect(Collectors.toList());

        List<UserMealWithExcess> userMealWithExcesses = new ArrayList<>();
        userMealWithExcesses = userMeals.stream()
                .map((UserMeal userMeal) ->
                        new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(),
                                sumCalories.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
//----------------------------------------------------------------------------------------------------------------------

        return userMealWithExcesses;
    }
}
