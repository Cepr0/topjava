package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.TimeUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * Created by Cepro on 11.09.2016.
 */
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Long, Meal> data = new ConcurrentHashMap<>();
    private AtomicLong counter = new AtomicLong(0);

    {
        populateData(TimeUtil.getRandomDate(LocalDate.of(2016, 1, 1), LocalDate.of(2016, 8, 31)));
        populateData(TimeUtil.getRandomDate(LocalDate.of(2016, 1, 1), LocalDate.of(2016, 8, 31)));
        populateData(TimeUtil.getRandomDate(LocalDate.of(2016, 1, 1), LocalDate.of(2016, 8, 31)));
    }

    public void populateData(LocalDate date) {
        ThreadLocalRandom r = ThreadLocalRandom.current();
        save(new Meal(LocalDateTime.of(date, LocalTime.of(r.nextInt(8, 12), 0)), "Завтрак", r.nextInt(300, 700)));
        save(new Meal(LocalDateTime.of(date, LocalTime.of(r.nextInt(12, 15), 0)), "Обед", r.nextInt(800, 1100)));
        save(new Meal(LocalDateTime.of(date, LocalTime.of(r.nextInt(18, 21), 0)), "Ужин", r.nextInt(300, 700)));
    }

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

    @Override
    public List<Meal> getAllSortedByDate() {
        return getAll().stream()
                .sorted((m1, m2) -> {return m1.getDateTime().compareTo(m2.getDateTime());})
                .collect(Collectors.toList());
    }
}
