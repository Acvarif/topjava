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
//                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
//                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
//                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
//                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
//                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
//                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
//                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)

                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

//        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
        List<UserMealWithExcess> mealsToStream = filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsToStream.forEach(System.out::println);

    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        /* HashMap LocalDate = sum calories*/
        HashMap<LocalDate, Integer> hashMap =  new HashMap<>();
        for (UserMeal userMeal : meals) {
            if (!hashMap.containsKey(userMeal.getDateTime().toLocalDate())) {
                hashMap.put(userMeal.getDateTime().toLocalDate(), userMeal.getCalories());
            } else {
                hashMap.put(userMeal.getDateTime().toLocalDate(),
                        hashMap.get(userMeal.getDateTime().toLocalDate()) + userMeal.getCalories());
            }
        }
        System.out.println(hashMap);

        System.out.println("filteredByCycles ---");

        boolean excess = true;
        List<UserMealWithExcess> userMealWithExcesses = new ArrayList<>();
        for (UserMeal userMeal : meals) {
            LocalDateTime dateTime = userMeal.getDateTime().withMinute(userMeal.getDateTime().getMinute());
            String description = userMeal.getDescription();

            /* entries between `startTime` and` endTime` with minutes */
            if (TimeUtil.isBetweenHalfOpen(dateTime.toLocalTime(), startTime, endTime)) {
                System.out.println(userMeal.getDateTime().toLocalDate());
                for(Map.Entry<LocalDate, Integer> entry : hashMap.entrySet()) {
                    LocalDate key = entry.getKey();
                    Integer value = entry.getValue();
                    System.out.println("key " + key + " value " + value);
                    if(userMeal.getDateTime().toLocalDate().isEqual(key)) {
                        System.out.println("key " + key + " value " + value);
                        if (value > caloriesPerDay) {
                            excess = true;
                        } else {
                            excess = false;
                        }
                    }
                }
                System.out.println(excess);
                UserMealWithExcess userMealWithExcess = new UserMealWithExcess(dateTime, description, userMeal.getCalories(), excess);
                userMealWithExcesses.add(userMealWithExcess);
            }
        }
        return userMealWithExcesses;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

//----------------------------------------------------------------------------------------------------------------------
//  Tests

        System.out.println("Start Stream Tests ---");

        List<LocalDateTime> localDateTimes = meals.stream()
                .map(UserMeal::getDateTime)
                .collect(Collectors.toList());
        System.out.println("localDateTimes " + localDateTimes);

        List<String> des = meals.stream()
                .map(UserMeal::getDescription)
                .collect(Collectors.toList());
        System.out.println("des " + des);

        List<Integer> cal = meals.stream()
                .map(UserMeal::getCalories)
                .collect(Collectors.toList());
        System.out.println("cal " + cal);

        Map<LocalDate, Integer> sumCalories = meals.stream()
                .collect(Collectors.groupingBy(meal -> meal.getDateTime().toLocalDate(),
                        Collectors.summingInt(UserMeal::getCalories)));
        System.out.println("sumCalories " + sumCalories);

        System.out.println("End Stream Tests ---");

//----------------------------------------------------------------------------------------------------------------------

        System.out.println("filteredByStreams ---");

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

        /* filter for List<UserMeal> between startTime, endTime */
        List<UserMeal> userMeals = meals.stream()
                .filter(meal -> TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime))
                .collect(Collectors.toList());
//        System.out.println("userMeals " + userMeals);

        boolean excess = false;
        List<UserMealWithExcess> userMealWithExcesses = new ArrayList<>();
        for (UserMeal userMeal : userMeals) {

            /* entries between `startTime` and` endTime` with minutes */
            if (userMeal.getDateTime().getDayOfMonth() == 30) {
                excess = calories30stream > caloriesPerDay;
            } else if (userMeal.getDateTime().getDayOfMonth() == 31) {
                excess = calories31stream > caloriesPerDay;
            }
            UserMealWithExcess userMealWithExcess = new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), excess);
            userMealWithExcesses.add(userMealWithExcess);
        }

//----------------------------------------------------------------------------------------------------------------------

        return userMealWithExcesses;
    }
}
