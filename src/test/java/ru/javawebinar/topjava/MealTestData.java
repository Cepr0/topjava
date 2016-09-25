package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;

/**
 * GKislin
 * 13.03.2015.
 */
public class MealTestData {

    public static final ModelMatcher<Meal> MATCHER
            = new ModelMatcher<>((expected, actual) -> expected.toString().equals(actual.toString()));

    private static final int MEAL_ID = START_SEQ + 1;

    public static List<Meal> adminData;
    public static List<Meal> userData;

    public static void populate() {
        if (adminData != null) adminData.clear();
        adminData = new ArrayList<>(Arrays.asList(
                new Meal(MEAL_ID + 12, LocalDateTime.of(2016, 9, 24, 19, 0), "Ужин " + 100001, 500),
                new Meal(MEAL_ID + 11, LocalDateTime.of(2016, 9, 24, 14, 0), "Обед " + 100001, 1000),
                new Meal(MEAL_ID + 10, LocalDateTime.of(2016, 9, 24, 10, 0), "Завтрак " + 100001, 500),
                new Meal(MEAL_ID + 9, LocalDateTime.of(2016, 9, 23, 19, 0), "Ужин " + 100001, 600),
                new Meal(MEAL_ID + 8, LocalDateTime.of(2016, 9, 23, 14, 0), "Обед " + 100001, 1000),
                new Meal(MEAL_ID + 7, LocalDateTime.of(2016, 9, 23, 10, 0), "Завтрак " + 100001, 500)
        ));

        if (userData != null) userData.clear();
        userData = new ArrayList<>(Arrays.asList(
                new Meal(MEAL_ID + 6, LocalDateTime.of(2016, 9, 24, 19, 0), "Ужин " + 100000, 600),
                new Meal(MEAL_ID + 5, LocalDateTime.of(2016, 9, 24, 14, 0), "Обед " + 100000, 1000),
                new Meal(MEAL_ID + 4, LocalDateTime.of(2016, 9, 24, 10, 0), "Завтрак " + 100000, 500),
                new Meal(MEAL_ID + 3, LocalDateTime.of(2016, 9, 23, 19, 0), "Ужин " + 100000, 500),
                new Meal(MEAL_ID + 2, LocalDateTime.of(2016, 9, 23, 14, 0), "Обед " + 100000, 1000),
                new Meal(MEAL_ID + 1, LocalDateTime.of(2016, 9, 23, 10, 0), "Завтрак " + 100000, 500)
        ));
    }
}
