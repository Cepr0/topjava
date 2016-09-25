package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class MealServiceTest {

    @Autowired
    private MealService mealService;

    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
        MealTestData.populate();
    }

    @Test
    public void get() throws Exception {
        Meal meal = mealService.get(userData.get(0).getId(), USER_ID);
        MATCHER.assertEquals(userData.get(0), meal);

        meal = mealService.get(adminData.get(0).getId(), ADMIN_ID);
        MATCHER.assertEquals(adminData.get(0), meal);
    }

    @Test
    public void delete() throws Exception {
        mealService.delete(userData.get(0).getId(), USER_ID);
        userData.remove(0);
        MATCHER.assertCollectionEquals(userData, mealService.getAll(USER_ID));

        mealService.delete(adminData.get(0).getId(), ADMIN_ID);
        adminData.remove(0);
        MATCHER.assertCollectionEquals(adminData, mealService.getAll(ADMIN_ID));
    }

    @Test
    public void getBetweenDates() throws Exception {
        MATCHER.assertCollectionEquals(userData.subList(3, 6),
                mealService.getBetweenDates(LocalDate.of(2016, 9, 23), LocalDate.of(2016, 9, 23), USER_ID));

        MATCHER.assertCollectionEquals(adminData.subList(3, 6),
                mealService.getBetweenDates(LocalDate.of(2016, 9, 23), LocalDate.of(2016, 9, 23), ADMIN_ID));
    }

    @Test
    public void getBetweenDateTimes() throws Exception {
        MATCHER.assertCollectionEquals(userData.subList(0, 2),
                mealService.getBetweenDateTimes(LocalDateTime.of(LocalDate.of(2016, 9, 24), LocalTime.of(11, 0)),
                        LocalDateTime.of(LocalDate.of(2016, 9, 24), LocalTime.MAX), USER_ID));

        MATCHER.assertCollectionEquals(adminData.subList(0, 2),
                mealService.getBetweenDateTimes(LocalDateTime.of(LocalDate.of(2016, 9, 24), LocalTime.of(11, 0)),
                        LocalDateTime.of(LocalDate.of(2016, 9, 24), LocalTime.MAX), ADMIN_ID));
    }

    @Test
    public void getAll() throws Exception {
        MATCHER.assertCollectionEquals(userData, mealService.getAll(USER_ID));
        MATCHER.assertCollectionEquals(adminData, mealService.getAll(ADMIN_ID));
    }

    @Test
    public void update() throws Exception {
        Meal meal = mealService.get(userData.get(0).getId(), USER_ID);
        meal.setDescription("Updated User descriptions");
        meal.setCalories(1001);
        mealService.update(meal, USER_ID);
        MATCHER.assertEquals(meal, mealService.get(meal.getId(), USER_ID));

        meal = mealService.get(adminData.get(0).getId(), ADMIN_ID);
        meal.setDescription("Updated Admin descriptions");
        meal.setCalories(1001);
        mealService.update(meal, ADMIN_ID);
        MATCHER.assertEquals(meal, mealService.get(meal.getId(), ADMIN_ID));
    }

    @Test
    public void save() throws Exception {
        Meal newMeal = new Meal(null, LocalDateTime.now().withSecond(0).withNano(0), "New user meal", 1000);
        Meal savedMeal = mealService.save(newMeal, USER_ID);
        newMeal.setId(savedMeal.getId());
        MATCHER.assertEquals(newMeal, savedMeal);

        newMeal = new Meal(null, LocalDateTime.now().withSecond(0).withNano(0), "New admin meal", 1100);
        savedMeal = mealService.save(newMeal, ADMIN_ID);
        newMeal.setId(savedMeal.getId());
        MATCHER.assertEquals(newMeal, savedMeal);
    }

    @Test(expected = NotFoundException.class)
    public void testGetNotFound() throws Exception {
        mealService.get(userData.get(0).getId(), ADMIN_ID);
        mealService.get(1, ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void testNotFoundDelete() throws Exception {
        mealService.delete(userData.get(0).getId(), ADMIN_ID);
        mealService.delete(1, ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void testNotFoundUpdate() throws Exception {
        Meal meal = mealService.get(userData.get(0).getId(), USER_ID);
        meal.setDescription("Updated User descriptions");
        meal.setCalories(1001);
        mealService.update(meal, ADMIN_ID);
    }
}