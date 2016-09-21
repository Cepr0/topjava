package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * GKislin
 * 15.06.2015.
 */
public interface MealService {
    Meal save(Meal meal);
    void delete(int mealId) throws NotFoundException;

    void delete(int mealId, int userId) throws NotFoundException;

    Meal get(int mealId) throws NotFoundException;

    Meal get(int mealId, int userId) throws NotFoundException;

    List<Meal> getAll(int userId);
    List<MealWithExceed> getWithFilter(int userId, LocalDate fromDate, LocalDate toDate, LocalTime fromTime, LocalTime toTime);
}
