package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.function.Function;
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

        long tm = System.nanoTime();
        /* HashMap LocalDate = sum calories*/
        HashMap<LocalDate, Integer> hashMap =  new HashMap<>();
        for (UserMeal userMeal : meals) {
            Integer sumCalories = hashMap.get(userMeal.getDateTime().toLocalDate());
            if (sumCalories == null) {
                hashMap.put(userMeal.getDateTime().toLocalDate(), userMeal.getCalories());
            } else {
                hashMap.put(userMeal.getDateTime().toLocalDate(),
                        hashMap.get(userMeal.getDateTime().toLocalDate()) + userMeal.getCalories());
            }
        }

//        for (UserMeal userMeal : meals) {
//            int sumCalories = hashMap.get(userMeal.getDateTime().toLocalDate());
//            if (!(sumCalories == 0)) {
//                hashMap.put(userMeal.getDateTime().toLocalDate(), userMeal.getCalories());
//            } else {
//                hashMap.put(userMeal.getDateTime().toLocalDate(), sumCalories + userMeal.getCalories());
//            }
//        }

//        System.out.println(hashMap);
//
//        System.out.println("filteredByCycles ---");

        boolean excess = true;
        List<UserMealWithExcess> userMealWithExcesses = new ArrayList<>();
        for (UserMeal userMeal : meals) {
            LocalDateTime dateTime = userMeal.getDateTime().withMinute(userMeal.getDateTime().getMinute());
            String description = userMeal.getDescription();

            /* entries between `startTime` and` endTime` with minutes */
            if (TimeUtil.isBetweenHalfOpen(dateTime.toLocalTime(), startTime, endTime)) {
                System.out.println(userMeal.getDateTime().toLocalDate());
//                for(Map.Entry<LocalDate, Integer> entry : hashMap.entrySet()) {
//                    LocalDate key = entry.getKey();
//                    Integer value = entry.getValue();
//                    System.out.println("key " + key + " value " + value);
//                    if(userMeal.getDateTime().toLocalDate().isEqual(key)) {
//                        System.out.println("key " + key + " value " + value);
//                        if (value > caloriesPerDay) {
//                            excess = true;
//                        } else {
//                            excess = false;
//                        }
//                    }
//                }
                if(hashMap.containsKey(userMeal.getDateTime().toLocalDate())) {
                    excess = hashMap.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay;
                }
                System.out.println(excess);
                UserMealWithExcess userMealWithExcess = new UserMealWithExcess(dateTime, description, userMeal.getCalories(), excess);
                userMealWithExcesses.add(userMealWithExcess);
            }
        }

        tm= System.nanoTime() - tm;
        System.out.printf("Elapsed %,9.3f ms\n", tm/1_000_000.0);

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

        System.out.println("End Stream Tests ---");

//----------------------------------------------------------------------------------------------------------------------

        System.out.println("filteredByStreams ---");

        /* filter for List<UserMeal> between startTime, endTime */
//        List<UserMeal> userMeals = meals.stream()
//                .filter(meal -> TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime))
//                .collect(Collectors.toList());
//        System.out.println("userMeals " + userMeals);

        long time = System.nanoTime();

        Map<LocalDate, Integer> sumCalories = meals.stream()
                .collect(Collectors.groupingBy(meal -> meal.getDateTime().toLocalDate(),
                        Collectors.summingInt(UserMeal::getCalories)));

        List<UserMealWithExcess> userMealWithExcesses;
        userMealWithExcesses = meals.stream()
                .filter(meal -> TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime))
                .map(userMeal -> new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(),
                        userMeal.getCalories(),
                        sumCalories.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay))
                        .collect(Collectors.toList());
        // Код, который нужно померить
        time = System.nanoTime() - time;
        System.out.printf("Elapsed %,9.3f ms\n", time/1_000_000.0);

        System.out.println("userMealWithExcesses " + userMealWithExcesses);

        meals.stream()
                .collect(Collectors.groupingBy(UserMeal::getCalories))
                .entrySet()
                .forEach(System.out::println);

        meals.stream()
                .map(u -> u.getDateTime().getHour())
                .distinct()
                .forEach(System.out::println);

        Map<LocalDateTime, Integer> sumCal =  meals.stream()
                .collect(Collectors.toMap(u -> u.getDateTime(), UserMeal::getCalories));
                System.out.println(sumCal);

        System.out.println("--------------");
//----------------------------------------------------------------------------------------------------------------------

        return userMealWithExcesses;
    }
}
