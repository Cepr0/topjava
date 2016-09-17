package ru.javawebinar.topjava.repository.mock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.TimeUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    // Map<userId, Map<mealId, Meal>>
    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    @Autowired
    private InMemoryMealRepositoryImpl(UserRepository userRepository) {
        userRepository.getAll().forEach(user -> populate(user.getId()));
    }

    private void populate(int userId) {
        ThreadLocalRandom r = ThreadLocalRandom.current();
        LocalDate date = TimeUtil.getRandomDate(LocalDate.of(2016, 9, 1), LocalDate.of(2016, 9, 17));
        save(new Meal(LocalDateTime.of(date, LocalTime.of(r.nextInt(8, 12), 0)), "Завтрак", r.nextInt(300, 700)), userId);
        save(new Meal(LocalDateTime.of(date, LocalTime.of(r.nextInt(12, 15), 0)), "Обед", r.nextInt(800, 1100)), userId);
        save(new Meal(LocalDateTime.of(date, LocalTime.of(r.nextInt(18, 21), 0)), "Ужин", r.nextInt(300, 700)), userId);
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) meal.setId(counter.incrementAndGet());
        else if (get(meal.getId(), userId) == null) return null;

        repository.computeIfAbsent(userId, ConcurrentHashMap::new).put(meal.getId(), meal);
        return meal;
    }

    @Override
    public boolean delete(int mealId, int userId) {
        Map<Integer, Meal> meals = repository.get(userId);
        return (meals != null) && (meals.remove(mealId) != null);
    }

    @Override
    public Meal get(int mealId, int userId) {
        Map<Integer, Meal> meals = repository.get(userId);
        return meals != null ? meals.get(mealId) : null;
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        return repository.get(userId).values();
    }
}

