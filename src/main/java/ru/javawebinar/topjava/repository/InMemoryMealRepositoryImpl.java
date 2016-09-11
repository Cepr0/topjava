package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.TimeUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Created by Cepro on 11.09.2016.
 */
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Long, Meal> data = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        populateData(TimeUtil.getRandomDate(LocalDate.of(2016, 1, 1), LocalDate.of(2016, 8, 31)));
        populateData(TimeUtil.getRandomDate(LocalDate.of(2016, 1, 1), LocalDate.of(2016, 8, 31)));
        populateData(TimeUtil.getRandomDate(LocalDate.of(2016, 1, 1), LocalDate.of(2016, 8, 31)));
//        save(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
//        save(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
//        save(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
//        save(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
//        save(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
//        save(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
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
