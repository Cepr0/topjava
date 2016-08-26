package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2016, Month.AUGUST, 10, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2016, Month.AUGUST, 10, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2016, Month.AUGUST, 10, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2016, Month.AUGUST, 11, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2016, Month.AUGUST, 11, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2016, Month.AUGUST, 11, 20, 0), "Ужин", 510)
        );

        List<UserMealWithExceed> filteredMeal = getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        for (UserMealWithExceed meal : filteredMeal) {
            System.out.println(meal);
        }
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        int variant = 0;
        switch (variant) {
            case 0:
                return getFilteredWithExceeded0(mealList, startTime, endTime, caloriesPerDay);
            case 1:
                return getFilteredWithExceeded1(mealList, startTime, endTime, caloriesPerDay);
            case 2:
                return getFilteredWithExceeded2(mealList, startTime, endTime, caloriesPerDay);
            case 3:
                return getFilteredWithExceeded3(mealList, startTime, endTime, caloriesPerDay);
            default:
                return getFilteredWithExceeded4(mealList, startTime, endTime, caloriesPerDay);
        }
    }

    // O(2n) with regular cycles
    public static List<UserMealWithExceed> getFilteredWithExceeded0(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> totalCaloriesPerDay = new HashMap<>();
        for (UserMeal curMeal : mealList) {
            totalCaloriesPerDay.put(curMeal.getLocalDate(), Optional.ofNullable(totalCaloriesPerDay.get(curMeal.getLocalDate())).orElse(0) + curMeal.getCalories());
        }

        List<UserMealWithExceed> filteredMeals = new ArrayList<>();
        for (UserMeal curMeal : mealList) {
            if (TimeUtil.isBetween(curMeal.getLocalTime(), startTime, endTime)) {
                boolean isExceed = (totalCaloriesPerDay.get(curMeal.getLocalDate()) > caloriesPerDay);
                filteredMeals.add(new UserMealWithExceed(curMeal, isExceed));
            }
        }

        return filteredMeals;
    }

    // O(1.5n) with regular cycles
    public static List<UserMealWithExceed> getFilteredWithExceeded1(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> totalCaloriesPerDay = new HashMap<>();
        List<UserMealWithExceed> filteredMeals = new ArrayList<>();

        for (UserMeal curMeal : mealList) {
            totalCaloriesPerDay.put(curMeal.getLocalDate(), Optional.ofNullable(totalCaloriesPerDay.get(curMeal.getLocalDate())).orElse(0) + curMeal.getCalories());
            addUserMealWithExceed(curMeal, filteredMeals, startTime, endTime);
        }

        for (UserMealWithExceed curMeal : filteredMeals) {
            setExceed(curMeal, totalCaloriesPerDay, caloriesPerDay);
        }

        return filteredMeals;
    }

    // O(2n) with streams
    public static List<UserMealWithExceed> getFilteredWithExceeded2(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> totalCaloriesPerDay = mealList.stream()
                .collect(Collectors.groupingBy(UserMeal::getLocalDate, Collectors.summingInt(UserMeal::getCalories)));

        return mealList.stream()
                .filter(curMeal -> TimeUtil.isBetween(curMeal.getLocalTime(), startTime, endTime))
                .map(curMeal -> {
                    boolean isExceed = (totalCaloriesPerDay.get(curMeal.getLocalDate()) > caloriesPerDay);
                    return new UserMealWithExceed(curMeal, isExceed);
                })
                .collect(Collectors.toList());
    }

    // O(n^2) with streams
    public static List<UserMealWithExceed> getFilteredWithExceeded3(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        return mealList.stream()
                .filter(curMeal -> TimeUtil.isBetween(curMeal.getLocalTime(), startTime, endTime))
                .map(curMeal -> new UserMealWithExceed(curMeal,  mealList.stream()
                        .collect(Collectors.groupingBy(UserMeal::getLocalDate, Collectors.summingInt(UserMeal::getCalories))).get(curMeal.getLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    // O(1.5n) with streams
    public static List<UserMealWithExceed> getFilteredWithExceeded4(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExceed> filteredMeals = new ArrayList<>();

        Map<LocalDate, Integer> totalCaloriesPerDay = mealList.stream()
                .peek(curMeal -> addUserMealWithExceed(curMeal, filteredMeals, startTime, endTime))
                .collect(Collectors.groupingBy(UserMeal::getLocalDate, Collectors.summingInt(UserMeal::getCalories)));

        filteredMeals.forEach(curMeal -> setExceed(curMeal, totalCaloriesPerDay, caloriesPerDay));
        return filteredMeals;
    }

    private static void addUserMealWithExceed(UserMeal curMeal, List<UserMealWithExceed> filteredMeals, LocalTime startTime, LocalTime endTime) {
        if (TimeUtil.isBetween(curMeal.getDateTime().toLocalTime(), startTime, endTime)) {
            filteredMeals.add(new UserMealWithExceed(curMeal));
        }
    }

    private static void setExceed(UserMealWithExceed curMeal, Map<LocalDate, Integer> totalCaloriesPerDay, int caloriesPerDay) {
        if (totalCaloriesPerDay.get(curMeal.getDateTime().toLocalDate()) > caloriesPerDay) {
            curMeal.setExceed(true);
        }
    }
}
