package ru.javawebinar.topjava.util;

import org.junit.*;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * Created by ua050339 on 25.08.2016.
 */
public class UserMealsUtilTest {
    // База с тестовыми данными: [кол-во данных] - [список с даными]
    private static Map<Integer, List<UserMeal>> testData = new TreeMap<>();

    // База с результатами: [номер теста] - [данные с результатами]
    private static Map<Integer, List<UserMealWithExceed>> resultData = new TreeMap<>();

    // Базы измерений времени выполнения тестов (см. вн. класс MeasurementData ниже)
    private static List<MeasurementData> measurements = new ArrayList<>();

    private static List<Instant> beforeList = new ArrayList<>();
    private static List<Instant> afterList = new ArrayList<>();

    // Мапа с названиями тестов: [номер теста] - [название]
    private static Map<Integer, String> variantNames = new TreeMap<>();

    @BeforeClass
    public static void setUp() throws Exception {
        LocalDate date = LocalDate.of(2012, Month.JANUARY, 1);
        Random r = new Random();
        List<UserMeal> mealList = new ArrayList<>();

        // Заполнем тестовыми данными
        // Массив, в котором указываем кол-во данных для тестов (они же - номер тестового набора данных)
        int[] counts = {1_000, 5_000, 10_000, 50_000, 100_000, 500_000};

        for (int count : counts) {
            // Генерируем данные
            for (int i = 0; i < count; i++) {
                date = date.plus(1, ChronoUnit.DAYS);
                mealList.add(new UserMeal(LocalDateTime.of(date, LocalTime.of(10, 0)), "Завтрак", 300 + r.nextInt(400)));
                mealList.add(new UserMeal(LocalDateTime.of(date, LocalTime.of(13, 0)), "Обед", 800 + r.nextInt(400)));
                mealList.add(new UserMeal(LocalDateTime.of(date, LocalTime.of(20, 0)), "Ужин", 300 + r.nextInt(400)));
            }
            // И сохраняем в базу
            testData.put(count, mealList);
        }

        // греем Джаву на тетьем наборе данных и на 4-х тестах (3 тест заранее долгий - пропускаем)
        UserMealsUtil.getFilteredWithExceeded0(testData.get(2), LocalTime.of(7, 0), LocalTime.of(13, 0), 2000);
        UserMealsUtil.getFilteredWithExceeded1(testData.get(2), LocalTime.of(7, 0), LocalTime.of(13, 0), 2000);
        UserMealsUtil.getFilteredWithExceeded2(testData.get(2), LocalTime.of(7, 0), LocalTime.of(13, 0), 2000);
        UserMealsUtil.getFilteredWithExceeded4(testData.get(2), LocalTime.of(7, 0), LocalTime.of(13, 0), 2000);

        // Выводим хедер таблицы результатов
        System.out.println("Variant        |   ML size | MLWE size | exc. count | Duration, ms");
    }

    @AfterClass
    public static void tearDown() throws Exception {
        // Выводим результаты
        List<Long> exceedCounts = new ArrayList<>();
        List<Long> durations = new ArrayList<>();

        // Цикл по кол-ву вариантов
        for (int i = 0; i < variantNames.size(); i++) {

            for (int j = 0; j < testData.size(); j++) {
                exceedCounts.add(resultData.get(j).stream().filter(UserMealWithExceed::isExceed).count());
                durations.add(Duration.between(beforeList.get(j), afterList.get(j)).toMillis());
            }

            for (int j = 0; j < testData.size(); j++) {
                System.out.printf("%-14s | %9d | %9d | %10d | %12d\n"
                        , variantNames, testData.get(j).size(), resultData.get(j).size(), exceedCounts.get(j), durations.get(j));
            }
        }
        // "Освождаем" память
        testData.clear();
        resultData.clear();
    }

    @Before
    public void before() {
        // Разделитель опытов
        System.out.println("------------------------------------------------------------------");
    }

    @After
    public void after() {
    }

    private class MeasurementData {
        int testNum;
        int dataSetNum;
        Instant start;
        Instant end = null;

        public MeasurementData(int testNum, int dataSetNum, Instant start) {
            this.testNum = testNum;
            this.dataSetNum = dataSetNum;
            this.start = start;
        }

        public void setEnd(Instant end) {
            this.end = end;
        }

        public int getTestNum() {
            return testNum;
        }

        public int getDataSetNum() {
            return dataSetNum;
        }

        public long getDuration() {
            return (end != null) ? Duration.between(start, end).toMillis() : -1;
        }
    }

    private void runTest(int testNum, String testName) {
        variantNames.put(testNum, testName);

        for (Map.Entry<Integer, List<UserMeal>> entry : testData.entrySet()) {
            MeasurementData measurementData = new MeasurementData(testNum, entry.getKey(), Instant.now());
            measurements.add(measurementData);
            switch (testNum) {
                case 0:
                    resultData.put(testNum, UserMealsUtil.getFilteredWithExceeded0(entry.getValue(), LocalTime.of(7, 0), LocalTime.of(13, 0), 2000));
                    break;
                case 1:
                    resultData.put(testNum, UserMealsUtil.getFilteredWithExceeded1(entry.getValue(), LocalTime.of(7, 0), LocalTime.of(13, 0), 2000));
                    break;
                case 2:
                    resultData.put(testNum, UserMealsUtil.getFilteredWithExceeded2(entry.getValue(), LocalTime.of(7, 0), LocalTime.of(13, 0), 2000));
                    break;
                case 3:
                    resultData.put(testNum, UserMealsUtil.getFilteredWithExceeded3(entry.getValue(), LocalTime.of(7, 0), LocalTime.of(13, 0), 2000));
                    break;
                case 4:
                    resultData.put(testNum, UserMealsUtil.getFilteredWithExceeded4(entry.getValue(), LocalTime.of(7, 0), LocalTime.of(13, 0), 2000));
                    break;
            }
            measurementData.setEnd(Instant.now());
        }
    }

    @Test
    public void getFilteredWithExceeded0() throws Exception {
        runTest(0, "O(2n) cycle");
    }

    @Test
    public void getFilteredWithExceeded1() throws Exception {
        runTest(1, "O(1.5n) cycle");
    }

    @Test
    public void getFilteredWithExceeded2() throws Exception {
        runTest(2, "O(2n) stream");
    }

    @Test(timeout = 60000)
    public void getFilteredWithExceeded3() throws Exception {
        runTest(3, "O(n^2?) stream");
    }

    @Test
    public void getFilteredWithExceeded4() throws Exception {
        runTest(4, "O(1.5n) stream");
    }
}