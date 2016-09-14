package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.TimeUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Long, Meal> data = new ConcurrentHashMap<>();
    private AtomicLong counter = new AtomicLong(0);
    private LocalDate fromDate = LocalDate.MIN;
    private LocalDate toDate = LocalDate.MAX;
    private LocalTime fromTime = LocalTime.MIN;
    private LocalTime toTime = LocalTime.MAX;
    private long curPage = 1;
    private int pageSize = Integer.MAX_VALUE;

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
    public List<Meal> getAll() {
        return new ArrayList<>(data.values())
                .stream()
                .filter(meal -> TimeUtil.isBetween(meal.getDate(), fromDate, toDate) && TimeUtil.isBetween(meal.getTime(), fromTime, toTime))
                .collect(Collectors.toList());
    }

    @Override
    public List<Meal> getAllSortedByDate() {
        return getAll().stream()
                .sorted((m1, m2) -> m1.getDateTime().compareTo(m2.getDateTime()))
                .skip((curPage - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    @Override
    public long count() {
        return (long) data.size();
    }

    @Override
    public void setFilter(LocalDate fromDate, LocalDate toDate, LocalTime fromTime, LocalTime toTime) {
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.fromTime = fromTime;
        this.toTime = toTime;
    }

    @Override
    public void setCurPage(long pageNumber, int pageSize) {
        this.pageSize = pageSize;
        long pagesCount = (long) Math.ceil(((double) count()) / pageSize);
        this.curPage = (pageNumber > 0 && pageNumber <= pagesCount) ? pageNumber : 1;
    }
}
