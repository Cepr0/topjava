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
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    // Map<mealId, Meal>
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    @Autowired
    public InMemoryMealRepositoryImpl(UserRepository userRepository) {
        userRepository.getAll().forEach(user -> populate(user.getId()));
    }

    private void populate(int userId) {
        ThreadLocalRandom r = ThreadLocalRandom.current();
        LocalDate date = TimeUtil.getRandomDate(LocalDate.of(2016, 9, 1), LocalDate.of(2016, 9, 17));
        save(new Meal(userId, LocalDateTime.of(date, LocalTime.of(r.nextInt(8, 12), 0)), "Завтрак", r.nextInt(300, 700)));
        save(new Meal(userId, LocalDateTime.of(date, LocalTime.of(r.nextInt(12, 15), 0)), "Обед", r.nextInt(800, 1100)));
        save(new Meal(userId, LocalDateTime.of(date, LocalTime.of(r.nextInt(18, 21), 0)), "Ужин", r.nextInt(300, 700)));
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) meal.setId(counter.incrementAndGet());
        else if (get(meal.getId()) == null) return null;

        repository.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public boolean delete(int mealId) {
        return repository.remove(mealId) != null;
    }

    @Override
    public Meal get(int mealId) {
        return repository.get(mealId);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return repository.values().stream()
                .filter(meal -> meal.getUserId() == userId)
                .sorted((m1, m2) -> m2.getDateTime().compareTo(m1.getDateTime()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Meal> getAllWithFilter(int userId, LocalDate fromDate, LocalDate toDate, LocalTime fromTime, LocalTime toTime) {
        return repository.values().stream()
                .filter(meal -> meal.getUserId() == userId
                                && TimeUtil.isBetween(meal.getDate(), fromDate, toDate)
                                && TimeUtil.isBetween(meal.getTime(), fromTime, toTime))
                .sorted((m1, m2) -> m2.getDateTime().compareTo(m1.getDateTime()))
                .collect(Collectors.toList());
    }
}

