package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;

public class UserMealWithExceed {
    private LocalDateTime dateTime;
    private String description;
    private int calories;
    private boolean exceed;

    public UserMealWithExceed(LocalDateTime dateTime, String description, int calories, boolean exceed) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.exceed = exceed;
    }

    public UserMealWithExceed(UserMeal userMeal) {
        this.dateTime = userMeal.getDateTime();
        this.description = userMeal.getDescription();
        this.calories = userMeal.getCalories();
        this.exceed = false;
    }

    public UserMealWithExceed(UserMeal userMeal, boolean exceed) {
        this.dateTime = userMeal.getDateTime();
        this.description = userMeal.getDescription();
        this.calories = userMeal.getCalories();
        this.exceed = exceed;
    }

    @Override
    public String toString() {
        return "UserMealWithExceed{" +
                "dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                ", exceed=" + exceed +
                '}';
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setExceed(boolean exceed) {
        this.exceed = exceed;
    }

    public boolean isExceed() {
        return exceed;
    }
}
