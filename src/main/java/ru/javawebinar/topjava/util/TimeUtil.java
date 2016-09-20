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
    private static final DateTimeFormatter DATE_TME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final LocalDate DEFAULT_START_DATE = LocalDate.of(1900, 1, 1);
    private static final LocalDate DEFAULT_END_DATE = LocalDate.of(3000, 1, 1);

    public static boolean isBetween(LocalTime testedTime, LocalTime startTime, LocalTime endTime) {
        startTime = (startTime != null) ? startTime : LocalTime.MIN;
        endTime = (endTime != null) ? endTime : LocalTime.MAX;
        return testedTime.compareTo(startTime) >= 0 && testedTime.compareTo(endTime) <= 0;
    }

    public static boolean isBetween(LocalDate testedDate, LocalDate startDate, LocalDate endDate) {
        startDate = (startDate != null) ? startDate : DEFAULT_START_DATE;
        endDate = (endDate != null) ? endDate : DEFAULT_END_DATE;
        return testedDate.compareTo(startDate) >= 0 && testedDate.compareTo(endDate) <= 0;
    }

    public static String toString(LocalDateTime dateTime) {
        return dateTime == null ? "" : dateTime.format(DATE_TME_FORMATTER);
    }

    public static LocalDate getRandomDate(LocalDate start, LocalDate end) {
        ThreadLocalRandom r = ThreadLocalRandom.current();
        try {
            return LocalDate.ofEpochDay(r.nextLong(start.toEpochDay(), end.toEpochDay()));
        } catch (Exception e) {
            return start;
        }
    }

    public static LocalDate parseDate(String date) {
        try {
            return LocalDate.parse(date);
        } catch (Exception e) {
            return null;
        }
    }

    public static LocalTime parseTime(String time) {
        try {
            return LocalTime.parse(time);
        } catch (Exception e) {
            return null;
        }
    }
}
