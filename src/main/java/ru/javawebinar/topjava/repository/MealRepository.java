package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface MealRepository {
    Meal save(Meal meal);
    void delete(long id);
    Meal getById(long id);
    List<Meal> getAll();
    List<Meal> getAllSortedByDate();
    long count();
    void setFilter(LocalDate fromDate, LocalDate toDate, LocalTime fromTime, LocalTime toTime);
    void setCurPage(long pageNumber, int pageSize);
}
