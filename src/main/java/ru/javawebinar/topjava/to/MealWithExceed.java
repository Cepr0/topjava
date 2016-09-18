package ru.javawebinar.topjava.to;

import ru.javawebinar.topjava.model.Meal;

/**
 * GKislin
 * 11.01.2015.
 */
public class MealWithExceed extends Meal {
    private final boolean exceed;

    public MealWithExceed(Meal meal, boolean exceed) {
        super(meal.getId(), meal.getUserId(), meal.getDateTime(), meal.getDescription(), meal.getCalories());
        this.exceed = exceed;
    }

    public boolean isExceed() {
        return exceed;
    }

    @Override
    public String toString() {
        return "MealWithExceed{" +
                super.toString() +
                "exceed=" + exceed +
                '}';
    }
}
