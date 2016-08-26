package ru.javawebinar.topjava.util;

import org.junit.*;
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
    private static List<List<UserMeal>> mealLists = new ArrayList<>();
    private static List<List<UserMealWithExceed>> exceedLists = new ArrayList<>();
    private List<Instant> beforeList = new ArrayList<>();
    private List<Instant> afterList = new ArrayList<>();
    private String variantName = null;

    @BeforeClass
    public static void setUp() throws Exception {
        LocalDate date = LocalDate.of(2012, Month.JANUARY, 1);
        Random r = new Random();
        List<UserMeal> mealList = new ArrayList<>();
        for (int i = 0; i < 1_000; i++) {
            date = date.plus(1, ChronoUnit.DAYS);
            mealList.add(new UserMeal(LocalDateTime.of(date, LocalTime.of(10, 0)), "Завтрак", 300 + r.nextInt(400)));
            mealList.add(new UserMeal(LocalDateTime.of(date, LocalTime.of(13, 0)), "Обед", 800 + r.nextInt(400)));
            mealList.add(new UserMeal(LocalDateTime.of(date, LocalTime.of(20, 0)), "Ужин", 300 + r.nextInt(400)));
        }
        mealLists.add(mealList);

        mealList = new ArrayList<>();
        for (int i = 0; i < 5_000; i++) {
            date = date.plus(1, ChronoUnit.DAYS);
            mealList.add(new UserMeal(LocalDateTime.of(date, LocalTime.of(10, 0)), "Завтрак", 300 + r.nextInt(400)));
            mealList.add(new UserMeal(LocalDateTime.of(date, LocalTime.of(13, 0)), "Обед", 800 + r.nextInt(400)));
            mealList.add(new UserMeal(LocalDateTime.of(date, LocalTime.of(20, 0)), "Ужин", 300 + r.nextInt(400)));
        }
        mealLists.add(mealList);

        mealList = new ArrayList<>();
        for (int i = 0; i < 10_000; i++) {
            date = date.plus(1, ChronoUnit.DAYS);
            mealList.add(new UserMeal(LocalDateTime.of(date, LocalTime.of(10, 0)), "Завтрак", 300 + r.nextInt(400)));
            mealList.add(new UserMeal(LocalDateTime.of(date, LocalTime.of(13, 0)), "Обед", 800 + r.nextInt(400)));
            mealList.add(new UserMeal(LocalDateTime.of(date, LocalTime.of(20, 0)), "Ужин", 300 + r.nextInt(400)));
        }
        mealLists.add(mealList);

        mealList = new ArrayList<>();
        for (int i = 0; i < 50_000; i++) {
            date = date.plus(1, ChronoUnit.DAYS);
            mealList.add(new UserMeal(LocalDateTime.of(date, LocalTime.of(10, 0)), "Завтрак", 300 + r.nextInt(400)));
            mealList.add(new UserMeal(LocalDateTime.of(date, LocalTime.of(13, 0)), "Обед", 800 + r.nextInt(400)));
            mealList.add(new UserMeal(LocalDateTime.of(date, LocalTime.of(20, 0)), "Ужин", 300 + r.nextInt(400)));
        }
        mealLists.add(mealList);

        mealList = new ArrayList<>();
        for (int i = 0; i < 100_000; i++) {
            date = date.plus(1, ChronoUnit.DAYS);
            mealList.add(new UserMeal(LocalDateTime.of(date, LocalTime.of(10, 0)), "Завтрак", 300 + r.nextInt(400)));
            mealList.add(new UserMeal(LocalDateTime.of(date, LocalTime.of(13, 0)), "Обед", 800 + r.nextInt(400)));
            mealList.add(new UserMeal(LocalDateTime.of(date, LocalTime.of(20, 0)), "Ужин", 300 + r.nextInt(400)));
        }
        mealLists.add(mealList);

        mealList = new ArrayList<>();
        for (int i = 0; i < 500_000; i++) {
            date = date.plus(1, ChronoUnit.DAYS);
            mealList.add(new UserMeal(LocalDateTime.of(date, LocalTime.of(10, 0)), "Завтрак", 300 + r.nextInt(400)));
            mealList.add(new UserMeal(LocalDateTime.of(date, LocalTime.of(13, 0)), "Обед", 800 + r.nextInt(400)));
            mealList.add(new UserMeal(LocalDateTime.of(date, LocalTime.of(20, 0)), "Ужин", 300 + r.nextInt(400)));
        }
        mealLists.add(mealList);

        // warming...
        UserMealsUtil.getFilteredWithExceeded0(mealLists.get(2), LocalTime.of(7, 0), LocalTime.of(13, 0), 2000);
        UserMealsUtil.getFilteredWithExceeded1(mealLists.get(2), LocalTime.of(7, 0), LocalTime.of(13, 0), 2000);
        UserMealsUtil.getFilteredWithExceeded2(mealLists.get(2), LocalTime.of(7, 0), LocalTime.of(13, 0), 2000);
        UserMealsUtil.getFilteredWithExceeded4(mealLists.get(2), LocalTime.of(7, 0), LocalTime.of(13, 0), 2000);

        System.out.println("Variant        |   ML size | MLWE size | exc. count | Duration, ms");
    }

    @AfterClass
    public static void tearDown() throws Exception {
        for (List<UserMeal> mealList : mealLists) {
            mealList.clear();
        }
    }

    @Before
    public void before() {
        System.out.println("------------------------------------------------------------------");
    }

    @After
    public void after() {
        List<Long> exceedCounts = new ArrayList<>();
        List<Long> durations = new ArrayList<>();

        for (int i = 0; i < mealLists.size(); i++) {
            exceedCounts.add(exceedLists.get(i).stream().filter(UserMealWithExceed::isExceed).count());
            durations.add(Duration.between(beforeList.get(i), afterList.get(i)).toMillis());
        }

        for (int i = 0; i < mealLists.size(); i++) {
            System.out.printf("%-14s | %9d | %9d | %10d | %12d\n"
                    , variantName, mealLists.get(i).size(), exceedLists.get(i).size(), exceedCounts.get(i), durations.get(i));
        }
    }

    @Test
    public void getFilteredWithExceeded0() throws Exception {
        variantName = "O(2n) cycle";
        for (List<UserMeal> mealList : mealLists) {
            beforeList.add(Instant.now());
            exceedLists.add(UserMealsUtil.getFilteredWithExceeded0(mealList, LocalTime.of(7, 0), LocalTime.of(13, 0), 2000));
            afterList.add(Instant.now());
        }
    }

    @Test
    public void getFilteredWithExceeded1() throws Exception {
        variantName = "O(1.5n) cycle";
        for (List<UserMeal> mealList : mealLists) {
            beforeList.add(Instant.now());
            exceedLists.add(UserMealsUtil.getFilteredWithExceeded1(mealList, LocalTime.of(7, 0), LocalTime.of(13, 0), 2000));
            afterList.add(Instant.now());
        }
    }

    @Test
    public void getFilteredWithExceeded2() throws Exception {
        variantName = "O(2n) stream";
        for (List<UserMeal> mealList : mealLists) {
            beforeList.add(Instant.now());
            exceedLists.add(UserMealsUtil.getFilteredWithExceeded2(mealList, LocalTime.of(7, 0), LocalTime.of(13, 0), 2000));
            afterList.add(Instant.now());
        }
    }

    @Test(timeout = 60000)
    public void getFilteredWithExceeded3() throws Exception {
        try {
            variantName = "O(n^2?) stream";
            for (List<UserMeal> mealList : mealLists) {
                beforeList.add(Instant.now());
                exceedLists.add(UserMealsUtil.getFilteredWithExceeded3(mealList, LocalTime.of(7, 0), LocalTime.of(13, 0), 2000));
                afterList.add(Instant.now());
            }
        } catch (AssertionError e) {
            System.out.println("failed!");
            throw e;
        }
    }

    @Test
    public void getFilteredWithExceeded4() throws Exception {
        variantName = "O(1.5n) stream";
        for (List<UserMeal> mealList : mealLists) {
            beforeList.add(Instant.now());
            exceedLists.add(UserMealsUtil.getFilteredWithExceeded4(mealList, LocalTime.of(7, 0), LocalTime.of(13, 0), 2000));
            afterList.add(Instant.now());
        }
    }
}