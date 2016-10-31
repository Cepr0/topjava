package ru.javawebinar.topjava.web.meal;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * @author Cepro
 *         30.10.16
 */
@RestController
@RequestMapping("/ajax/meals")
public class AjaxMealController extends AbstractMealController {
    
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public List<MealWithExceed> getAll() {
        return super.getAll();
    }
    
    @DeleteMapping("/{id}")
    @Override
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }
    
    @PostMapping
    public void createOrUpdate(@RequestParam("id") Integer id,
                               @RequestParam("dateTime") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime dateTime,
                               @RequestParam("calories") Integer calories,
                               @RequestParam("description") String description) {
        
        Meal meal = new Meal(id, dateTime, description, calories);
        if (meal.isNew()) {
            super.create(meal);
        } else {
            super.update(meal, id);
        }
    }
    
    
    @PostMapping(value = "/filter}")
    public List<MealWithExceed> getBetween(
            @RequestParam("startDate") LocalDate startDate,
            @RequestParam("startTime") LocalTime startTime,
            @RequestParam("endDate") LocalDate endDate,
            @RequestParam("endTime") LocalTime endTime) {
        return super.getBetween(startDate, startTime, endDate, endTime);
    }
}
