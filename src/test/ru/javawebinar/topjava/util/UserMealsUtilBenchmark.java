package ru.javawebinar.topjava.util;

import org.junit.Test;
import org.openjdk.jmh.annotations.Param;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class UserMealsUtilBenchmark {

    @Param({ "1_000", "5_000", "10_000" })
    // "1_000", "5_000", "10_000", "25_000", "50_000", "75_000", "100_000", "250_000", "500_000"
    private int iterations;
    private List<Integer> input;

    public void setUp() {
        input = IntStreamEx.of(new Random(1), iterations, 1, 10000).boxed().toList();
        // https://gist.github.com/amaembo/f683235194a59ae4f10c
        // https://habrahabr.ru/post/255659/
        // https://habrahabr.ru/post/256905/
    }


    public void tearDown() throws Exception {

    }

    @Test
    public void getFilteredWithExceeded0() throws Exception {

    }

    @Test
    public void getFilteredWithExceeded1() throws Exception {

    }

    @Test
    public void getFilteredWithExceeded2() throws Exception {

    }

    @Test
    public void getFilteredWithExceeded3() throws Exception {

    }

    @Test
    public void getFilteredWithExceeded4() throws Exception {

    }

    @Test
    public void getFilteredWithExceeded5() throws Exception {

    }

}