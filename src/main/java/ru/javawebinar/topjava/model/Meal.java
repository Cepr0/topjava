package ru.javawebinar.topjava.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * GKislin
 * 11.01.2015.
 */
public class Meal extends BaseEntity {
    private LocalDateTime dateTime;
    private String description;
    private int calories;
    private Integer userId;

    public Meal() {
    }

    public Meal(LocalDateTime dateTime, String description, int calories) {
        this(null, null, dateTime, description, calories);
    }

    public Meal(Integer userId, LocalDateTime dateTime, String description, int calories) {
        this(null, userId, dateTime, description, calories);
    }

    public Meal(Integer mealId, Integer userId, LocalDateTime dateTime, String description, int calories) {
        super(mealId);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.userId = userId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                ", userId=" + userId +
                '}';
    }
}
