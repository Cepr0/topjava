package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.*;
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
        filteredMeal.forEach(System.out::println);
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
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
