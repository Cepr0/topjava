package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

/**
 * GKislin
 * 07.01.2015.
 */
public class TimeUtil {
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static boolean isBetween(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) <= 0;
    }

    public static String timeToString(LocalDateTime dateTime) {
        return (dateTime != null) ? dateTime.format(TIME_FORMAT) : "";
    }

    public static LocalDate getRandomDate(LocalDate start, LocalDate end) {
        ThreadLocalRandom r = ThreadLocalRandom.current();
        return LocalDate.ofEpochDay(r.nextLong(start.toEpochDay(), end.toEpochDay()));
    }
}
