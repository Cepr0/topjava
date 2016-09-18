package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
public class MealRestController {
    private final Logger LOG = LoggerFactory.getLogger(getClass());

    private MealService service;

    @Autowired
    public void setService(MealService service) {
        this.service = service;
    }

    public Meal save(Meal meal) {
        LOG.info("save " + meal);
        return service.save(meal);
    }

    public void delete(int mealId) {
        LOG.info("delete meal with ID: " + mealId);
        service.delete(mealId);
    }

    public Meal get(int mealId) {
        LOG.info("get meal ID: " + mealId);
        return service.get(mealId);
    }

    public List<Meal> getAll(int userId) {
        LOG.info("get all meal for user ID: " + userId);
        return service.getAll(userId);
    }

    public List<Meal> getAllWithFilter(int userId, LocalDate fromDate, LocalDate toDate, LocalTime fromTime, LocalTime toTime) {
        LOG.info("get all with filter meal for user ID: " + userId);
        return service.getAllWithFilter(userId, fromDate, toDate, fromTime, toTime);
    }
}
