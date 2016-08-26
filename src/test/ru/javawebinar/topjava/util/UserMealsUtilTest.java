package ru.javawebinar.topjava.util;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by ua050339 on 25.08.2016.
 */
public class UserMealsUtilTest {
    List<UserMeal> mealList = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        LocalDate date = LocalDate.of(2012, Month.JANUARY, 1);
        Random r = new Random();
        for (int i = 0; i < 750_000; i++) {
            date = date.plus(1, ChronoUnit.DAYS);
            mealList.add(new UserMeal(LocalDateTime.of(date, LocalTime.of(10, 0)), "Завтрак", 300 + r.nextInt(400)));
            mealList.add(new UserMeal(LocalDateTime.of(date, LocalTime.of(13, 0)), "Обед", 800 + r.nextInt(400)));
            mealList.add(new UserMeal(LocalDateTime.of(date, LocalTime.of(20, 0)), "Ужин", 300 + r.nextInt(400)));
        }

        UserMealsUtil.getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(13, 0), 2000);
        UserMealsUtil.getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(13, 0), 2000);
        UserMealsUtil.getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(13, 0), 2000);

    }

    @After
    public void tearDown() throws Exception {
        mealList.clear();
    }

    @Test
    public void getFilteredWithExceeded() throws Exception {

        Instant beforeRun = Instant.now();
        List<UserMealWithExceed> exceedList = UserMealsUtil.getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(13, 0), 2000);
        long runTimeMs = Duration.between(beforeRun, Instant.now()).toMillis();
        long exceedCount = exceedList.stream().filter(UserMealWithExceed::isExceed).count();
        System.out.printf("Implementation 1, mealList.size() = %d, exceedList.size() = %d, exceed count = %d, runtime = %d ms%n", mealList.size(), exceedList.size(), exceedCount, runTimeMs);
    }

    @Test
    public void getFilteredWithExceeded1() throws Exception {

        Instant beforeRun = Instant.now();
        List<UserMealWithExceed> exceedList = UserMealsUtil.getFilteredWithExceeded1(mealList, LocalTime.of(7, 0), LocalTime.of(13, 0), 2000);
        long runTimeMs = Duration.between(beforeRun, Instant.now()).toMillis();
        long exceedCount = exceedList.stream().filter(UserMealWithExceed::isExceed).count();
        System.out.printf("Implementation 1, mealList.size() = %d, exceedList.size() = %d, exceed count = %d, runtime = %d ms%n", mealList.size(), exceedList.size(), exceedCount, runTimeMs);
    }

//    @Test
//    public void getFilteredWithExceeded2() throws Exception {
//
//        Instant beforeRun = Instant.now();
//        List<UserMealWithExceed> exceedList = UserMealsUtil.getFilteredWithExceeded2(mealList, LocalTime.of(7, 0), LocalTime.of(13, 0), 2000);
//        long runTimeMs = Duration.between(beforeRun, Instant.now()).toMillis();
//        long exceedCount = exceedList.stream().filter(UserMealWithExceed::isExceed).count();
//        System.out.printf("Implementation 1, mealList.size() = %d, exceedList.size() = %d, exceed count = %d, runtime = %d ms%n", mealList.size(), exceedList.size(), exceedCount, runTimeMs);
//    }

    @Test
    public void getFilteredWithExceeded3() throws Exception {

        Instant beforeRun = Instant.now();
        List<UserMealWithExceed> exceedList = UserMealsUtil.getFilteredWithExceeded3(mealList, LocalTime.of(7, 0), LocalTime.of(13, 0), 2000);
        long runTimeMs = Duration.between(beforeRun, Instant.now()).toMillis();
        long exceedCount = exceedList.stream().filter(UserMealWithExceed::isExceed).count();
        System.out.printf("Implementation 1, mealList.size() = %d, exceedList.size() = %d, exceed count = %d, runtime = %d ms%n", mealList.size(), exceedList.size(), exceedCount, runTimeMs);
    }
}