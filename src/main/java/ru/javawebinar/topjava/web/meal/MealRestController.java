package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;

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
        LOG.info(String.format("save %s", meal));
        return service.save(meal);
    }

    public void delete(int mealId) {
        LOG.info(String.format("delete meal with ID: %d", mealId));
        service.delete(mealId);
    }

    public void delete(int mealId, int userId) {
        LOG.info(String.format("delete meal with ID: %d for user with ID: %d", mealId, userId));
        service.delete(mealId, userId);
    }

    public Meal get(int mealId) {
        LOG.info(String.format("get meal ID: %d", mealId));
        return service.get(mealId);
    }

    public Meal get(int mealId, int userId) {
        LOG.info(String.format("get meal ID: %d for user with ID: %d", mealId, userId));
        return service.get(mealId, userId);
    }

    public List<Meal> getAll(int userId) {
        LOG.info(String.format("get all meal for user ID: %d", userId));
        return service.getAll(userId);
    }

    public List<MealWithExceed> getWithFilter(int userId, LocalDate fromDate, LocalDate toDate, LocalTime fromTime, LocalTime toTime) {
        LOG.info(String.format("get all with filter meal for user ID: %d", userId));
        return service.getWithFilter(userId, fromDate, toDate, fromTime, toTime);
    }
}
