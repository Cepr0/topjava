package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.TimeUtil;
import ru.javawebinar.topjava.util.exception.ExceptionUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MealServiceImpl implements MealService {

    private MealRepository repository;
    private UserRepository userRepository;

    @Autowired
    public void setRepository(MealRepository repository) {
        this.repository = repository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
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
    public void delete(int mealId, int userId) throws NotFoundException {
        ExceptionUtil.checkNotFoundWithId((repository.get(mealId).getUserId() == userId), mealId);
        repository.delete(mealId);
    }

    @Override
    public Meal get(int mealId) throws NotFoundException {
        return ExceptionUtil.checkNotFoundWithId(repository.get(mealId), mealId);
    }

    @Override
    public Meal get(int mealId, int userId) throws NotFoundException {
        ExceptionUtil.checkNotFoundWithId((repository.get(mealId).getUserId() == userId), mealId);
        return repository.get(mealId);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return repository.getAll(userId);
    }

    @Override
    public List<MealWithExceed> getWithFilter(int userId, LocalDate fromDate, LocalDate toDate, LocalTime fromTime, LocalTime toTime) {
        return MealsUtil.getWithExceeded(repository.getWithFilter(userId, fromDate, toDate)
                , userRepository.get(userId).getCaloriesPerDay())
                .stream()
                .filter(meal -> TimeUtil.isBetween(meal.getTime(), fromTime, toTime))
                .collect(Collectors.toList());
    }
}
