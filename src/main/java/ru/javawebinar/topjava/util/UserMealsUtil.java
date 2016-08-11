package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.*;
import java.util.*;

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
        Map<LocalDate, Integer> totalCaloriesPerDay = new HashMap<>();

        for (UserMeal curMeal : mealList) {
            LocalDate localDate = curMeal.getDateTime().toLocalDate();
            int totalCalories = totalCaloriesPerDay.containsKey(localDate)
                    ? totalCaloriesPerDay.get(localDate) + curMeal.getCalories()
                    : curMeal.getCalories();
            totalCaloriesPerDay.put(localDate, totalCalories);
        }

        for (UserMeal curMeal : mealList) {
            if (TimeUtil.isBetween(curMeal.getDateTime().toLocalTime(), startTime, endTime)) {
                filteredMeals.add(new UserMealWithExceed(
                        curMeal
                        , (totalCaloriesPerDay.get(curMeal.getDateTime().toLocalDate()) > caloriesPerDay))
                );
            }
        }

        return filteredMeals;
    }
}
