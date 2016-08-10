package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2016, Month.AUGUST, 10, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2016, Month.AUGUST, 10, 11, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2016, Month.AUGUST, 10, 20, 0), "Ужин", 600),
                new UserMeal(LocalDateTime.of(2016, Month.AUGUST, 11, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2016, Month.AUGUST, 11, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2016, Month.AUGUST, 11, 20, 0), "Ужин", 510)
        );

        List<UserMealWithExceed> filteredMeal = getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        for (UserMealWithExceed meal : filteredMeal) {
            System.out.println(meal);
        }
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(
            List<UserMeal> mealList
            , LocalTime startTime
            , LocalTime endTime
            , int caloriesPerDay) {
        List<UserMealWithExceed> filteredMealList = new ArrayList<>();

        LocalDate currentDate = LocalDate.now(); // current date
        LocalDateTime startDateTime = LocalDateTime.of(currentDate, startTime); //start date-time
        LocalDateTime endDateTime = LocalDateTime.of(currentDate, endTime); //end date-time

        int todayCaloriesCount = 0;
        for (UserMeal curMeal : mealList) {
            LocalDateTime userMealDateTime = curMeal.getDateTime();
            Period period = Period.between(currentDate, userMealDateTime.toLocalDate());

            // if period between today and current meal day is 0 then count calories total of today
            if (Period.between(currentDate, userMealDateTime.toLocalDate()).getDays() == 0) {
                todayCaloriesCount = todayCaloriesCount + curMeal.getCalories();
            }
        }

        if (todayCaloriesCount > caloriesPerDay) {
            for (UserMeal curMeal : mealList) {
                LocalDateTime userMealDateTime = curMeal.getDateTime();

                // if current meal date-time is after or equal of filter start time
                // and if it date-time is before or equal of filter end time
                if ((userMealDateTime.isAfter(startDateTime) || userMealDateTime.isEqual(startDateTime))
                        && (userMealDateTime.isBefore(endDateTime) || userMealDateTime.isEqual(endDateTime))) {
                    // then add it to result list
                    filteredMealList.add(new UserMealWithExceed(curMeal, true));
                }
            }
        }

        return filteredMealList;
    }
}
