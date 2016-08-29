package ru.javawebinar.topjava.util;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class UserMealsUtilTest {
    // База с тестовыми данными: [кол-во данных] - [список с даными]
    private static Map<Integer, List<UserMeal>> testData = new TreeMap<>();

    // База результатов тестов (см. вл. класс ResultData ниже)
    private static List<ResultData> resultData = new ArrayList<>();

    // Мапа обрабатываемых тестов: [номер теста] - [название]
    private static Map<Integer, String> variants = new TreeMap<>();

    // Массив, в котором указываем кол-во данных для тестов (они же - индексы тестовых наборов данных)
    private static int[] counts = {1_000, 5_000, 10_000, 25_000, 50_000, 75_000, 100_000, 250_000, 500_000};

    private static final int TIMEOUT = 2_100_000; // Значение timeout для аннотации @Test

    // Заполнем базу тестовыми данными
    @BeforeClass
    public static void setUp() throws Exception {
        LocalDate date = LocalDate.of(2012, Month.JANUARY, 1);
        Random r = new Random();
        List<UserMeal> mealList;

        for (int count : counts) {
            // Генерируем данные
            mealList = new ArrayList<>();
            for (int i = 0; i < count; i++) {
                date = date.plus(1, ChronoUnit.DAYS);
                mealList.add(new UserMeal(LocalDateTime.of(date, LocalTime.of(10, 0)), "Завтрак", 300 + r.nextInt(400)));
                mealList.add(new UserMeal(LocalDateTime.of(date, LocalTime.of(13, 0)), "Обед", 800 + r.nextInt(400)));
                mealList.add(new UserMeal(LocalDateTime.of(date, LocalTime.of(20, 0)), "Ужин", 300 + r.nextInt(400)));
            }
            // Сохраняем в базу
            testData.put(count, mealList);
        }

        // греем Джаву на третьем наборе данных и на 4-х тестах (3-й тест заранее долгий - пропускаем)
        UserMealsUtil.getFilteredWithExceeded0(testData.get(counts[2]), LocalTime.of(7, 0), LocalTime.of(13, 0), 2000);
        UserMealsUtil.getFilteredWithExceeded1(testData.get(counts[2]), LocalTime.of(7, 0), LocalTime.of(13, 0), 2000);
        UserMealsUtil.getFilteredWithExceeded2(testData.get(counts[2]), LocalTime.of(7, 0), LocalTime.of(13, 0), 2000);
        UserMealsUtil.getFilteredWithExceeded4(testData.get(counts[2]), LocalTime.of(7, 0), LocalTime.of(13, 0), 2000);

        // Выводим заголовок таблицы результатов
        System.out.println();
        System.out.println("Variant        |   UM size | UMWE size | Exc. count | Duration, ms");
        System.out.println("------------------------------------------------------------------");
    }

    @AfterClass
    public static void printResults() throws Exception {

        // Цикл по протестированным вариантам
        for (Map.Entry<Integer, String> varEntry : variants.entrySet()) {
            final int curVariant = varEntry.getKey();

            // Цикл по наборам данных
            for (int j = 0; j < testData.size(); j++) {
                final int curDataSetNum = counts[j];

                // Получаем текущий набор данных
                ResultData curResult = resultData.stream()
                        .filter(result -> result.testNum == curVariant && result.dataSetNum == curDataSetNum)
                        .findFirst()
                        .orElse(null);

                // Считаем кол-во объектов UserMealWithExceed у которых exceed == true
                long exceedCount = (curResult != null && curResult.data != null)
                        ? curResult.data.stream().filter(UserMealWithExceed::isExceed).count()
                        : -1L;

                // Получаем длительность теста
                long duration = (curResult != null) ? curResult.getDuration() : -1L;

                // Определяем кол-во всего объектов UserMealWithExceed
                int exceedListSize = (curResult != null && curResult.data != null) ? curResult.data.size() : -1;

                // Выводим результат
                System.out.printf("%-14s | %9d | %9d | %10d | %12d%n"
                        , varEntry.getValue(), testData.get(counts[j]).size(), exceedListSize, exceedCount, duration);
            }
            System.out.println("------------------------------------------------------------------");
        }

        // "Освобождаем" память
        testData.clear();
        resultData.clear();
    }

//    @Before
//    public void before() {
//    }

//    @After
//    public void after() {
//    }

    private static class ResultData {
        int testNum; // Номер теста
        int dataSetNum; // Номер набора тестовых данных
        Instant start;
        Instant end = null;
        List<UserMealWithExceed> data = null; // результирующий список объектов UserMealWithExceed

        ResultData(int testNum, int dataSetNum, Instant start) {
            this.testNum = testNum;
            this.dataSetNum = dataSetNum;
            this.start = start;
        }

        long getDuration() {
            return (start != null && end != null) ? Duration.between(start, end).toMillis() : -1;
        }
    }

    // Основной метод для проведения теста на нескольких наборах данных
    private void runTest(int testNum, String testName) {
        variants.put(testNum, testName);
        System.out.println(testName);

        for (Map.Entry<Integer, List<UserMeal>> entry : testData.entrySet()) {
            int dataSetNum = entry.getKey();
            ResultData result = new ResultData(testNum, dataSetNum, Instant.now());
            resultData.add(result);
            List<UserMeal> testDataSet = entry.getValue();
            switch (testNum) {
                case 0:
                    result.data = UserMealsUtil.getFilteredWithExceeded0(testDataSet, LocalTime.of(7, 0), LocalTime.of(13, 0), 2000);
                    break;
                case 1:
                    result.data = UserMealsUtil.getFilteredWithExceeded1(testDataSet, LocalTime.of(7, 0), LocalTime.of(13, 0), 2000);
                    break;
                case 2:
                    result.data = UserMealsUtil.getFilteredWithExceeded2(testDataSet, LocalTime.of(7, 0), LocalTime.of(13, 0), 2000);
                    break;
                case 3:
                    result.data = UserMealsUtil.getFilteredWithExceeded3(testDataSet, LocalTime.of(7, 0), LocalTime.of(13, 0), 2000);
                    break;
                default:
                    result.data = UserMealsUtil.getFilteredWithExceeded4(testDataSet, LocalTime.of(7, 0), LocalTime.of(13, 0), 2000);
            }
            result.end = Instant.now();

            System.out.printf("%-8s: %d%n", String.valueOf(dataSetNum), result.getDuration());
        }
    }

    @Test(timeout = TIMEOUT)
    public void getFilteredWithExceeded0() throws Exception {
        runTest(0, "O(2n) cycle");
    }

    @Test(timeout = TIMEOUT)
    public void getFilteredWithExceeded1() throws Exception {
        runTest(1, "O(1.5n) cycle");
    }

    @Test (timeout = TIMEOUT)
    public void getFilteredWithExceeded2() throws Exception {
        runTest(2, "O(2n) stream");
    }

    @Test(timeout = TIMEOUT)
    public void getFilteredWithExceeded3() throws Exception {
        runTest(3, "O(n^2?) stream");
    }

    @Test(timeout = TIMEOUT)
    public void getFilteredWithExceeded4() throws Exception {
        runTest(4, "O(1.5n) stream");
    }
}