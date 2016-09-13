package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Long, Meal> data = new ConcurrentHashMap<>();
    private AtomicLong counter = new AtomicLong(0);

    @Override
    public Meal save(Meal meal) {
        if(meal.isNew()) meal.setId(counter.incrementAndGet());
        return data.put(meal.getId(), meal);
    }

    @Override
    public void delete(long id) {
        data.remove(id);
    }

    @Override
    public Meal getById(long id) {
        return data.get(id);
    }

    @Override
    public Collection<Meal> getAll() {
        return data.values();
    }
}
