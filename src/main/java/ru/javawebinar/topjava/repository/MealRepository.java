package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;
import java.util.List;

/**
 * Created by Cepro on 11.09.2016.
 */
interface MealRepository {
    Meal save(Meal meal);
    void delete(long id);
    Meal getById(long id);
    Collection<Meal> getAll();
    List<Meal> getAllSortedByDate();
}
