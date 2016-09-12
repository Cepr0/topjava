package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.concurrent.ThreadLocalRandom;

public class TimeUtil {

    static boolean isBetween(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) <= 0;
    }

    public static LocalDate getRandomDate(LocalDate start, LocalDate end) {
        ThreadLocalRandom r = ThreadLocalRandom.current();
        try {
            return LocalDate.ofEpochDay(r.nextLong(start.toEpochDay(), end.toEpochDay()));
        } catch (Exception e) {
            return start;
        }
    }
}
