package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.ExceptionUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class MealServiceImpl implements MealService {

    private MealRepository repository;

    @Autowired
    public void setRepository(MealRepository repository) {
        this.repository = repository;
    }

    @Override
    public Meal save(Meal meal) {
        return repository.save(meal);
    }

    @Override
    public void delete(int mealId) throws NotFoundException {
        ExceptionUtil.checkNotFoundWithId(repository.delete(mealId), mealId);
    }

    @Override
    public Meal get(int mealId) throws NotFoundException {
        return ExceptionUtil.checkNotFoundWithId(repository.get(mealId), mealId);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return repository.getAll(userId);
    }

    @Override
    public List<Meal> getAllWithFilter(int userId, LocalDate fromDate, LocalDate toDate, LocalTime fromTime, LocalTime toTime) {
        return repository.getAllWithFilter(userId, fromDate, toDate, fromTime, toTime);
    }
}
