package ru.javawebinar.topjava.util;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;
import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Warmup(iterations = 3)
@Measurement(iterations = 5)
@BenchmarkMode({Mode.SingleShotTime,  Mode.AverageTime})
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class UserMealsUtilBenchmark {

    // Кол-во тестовых данных на итерацию
    @Param({"100000", "300000", "400000", "500000", "600000", "700000", "800000", "900000", "1000000"})
    private int samplesCount;

    // База с тестовыми данными
    private static List<UserMeal> testData = new ArrayList<>();

    @Setup
    public void setUp() {
        LocalDate date = LocalDate.of(2000, Month.JANUARY, 1);
        Random r = new Random();

        // Генерируем тестовые данные
        for (int i = 0; i < samplesCount; i++) {
            date = date.plusDays(1);
            testData.add(new UserMeal(
                    LocalDateTime.of(date, LocalTime.of(8 + r.nextInt(13), 0))
                    , new String[]{"Завтрак", "Обед", "Ужин"}[r.nextInt(3)]
                    , 300 + r.nextInt(1000)));
        }
    }

    // Запускаем измерения
    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(UserMealsUtilBenchmark.class.getSimpleName())
                .threads(1)
                .forks(1)
                .timeout(TimeValue.minutes(10))
                .build();

        new Runner(options).run();
    }

    @Benchmark()
    public void O2NCycles() throws Exception {
        UserMealsUtil.getFilteredWithExceeded0(testData, LocalTime.of(7, 0), LocalTime.of(13, 0), 2000);
    }

    @Benchmark
    public void O15NCycles() throws Exception {
        UserMealsUtil.getFilteredWithExceeded1(testData, LocalTime.of(7, 0), LocalTime.of(13, 0), 2000);
    }

    @Benchmark
    public void O2NStreams() throws Exception {
        UserMealsUtil.getFilteredWithExceeded2(testData, LocalTime.of(7, 0), LocalTime.of(13, 0), 2000);
    }

    //@Benchmark
    public void ON2Streams() throws Exception {
        UserMealsUtil.getFilteredWithExceeded3(testData, LocalTime.of(7, 0), LocalTime.of(13, 0), 2000);
    }

    @Benchmark
    public void O15NStreams() throws Exception {
        UserMealsUtil.getFilteredWithExceeded4(testData, LocalTime.of(7, 0), LocalTime.of(13, 0), 2000);
    }

    @Benchmark
    public void O2NStreamsV2() throws Exception {
        UserMealsUtil.getFilteredWithExceeded5(testData, LocalTime.of(7, 0), LocalTime.of(13, 0), 2000);
    }
}