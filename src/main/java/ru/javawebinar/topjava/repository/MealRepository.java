package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
public interface MealRepository {
    Meal save(Meal meal);
    boolean delete(int mealId);
    Meal get(int mealId);
    List<Meal> getAll(int userId);
    List<Meal> getWithFilter(int userId, LocalDate fromDate, LocalDate toDate);
}
