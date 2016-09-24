package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * User: gkislin
 * Date: 26.08.2014
 */

@Repository
public class JdbcMealRepositoryImpl implements MealRepository {

    private static final BeanPropertyRowMapper<Meal> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Meal.class);
    private final JdbcTemplate jdbcTmpl;
    private final NamedParameterJdbcTemplate namedJdbcTmpl;
    private SimpleJdbcInsert insertingMeal;

    @Autowired
    public JdbcMealRepositoryImpl(DataSource dataSource, JdbcTemplate jdbcTmpl, NamedParameterJdbcTemplate namedJdbcTmpl) {

        insertingMeal = new SimpleJdbcInsert(dataSource)
                .withTableName("meals")
                .usingGeneratedKeyColumns("id");

        this.jdbcTmpl = jdbcTmpl;
        this.namedJdbcTmpl = namedJdbcTmpl;
    }

    @Override
    public Meal save(Meal meal, int userId) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", meal.getId())
                .addValue("dateTime", meal.getDateTime())
                .addValue("description", meal.getDescription())
                .addValue("calories", meal.getCalories())
                .addValue("userId", userId);

        if (meal.isNew()) {
            int mealId = insertingMeal.executeAndReturnKey(map).intValue();
            meal.setId(mealId);
        } else {
            namedJdbcTmpl.update("update meals \n" +
                    "set date_time = :dateTime, description = :description, calories = :calories\n" +
                    "where id = :id and user_id = :userId", map);
        }
        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        return jdbcTmpl.update("delete from meals where id = ? and user_id = ?", id, userId) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        List<Meal> meals = jdbcTmpl.query("select * from meals where id = ? and user_id = ?", ROW_MAPPER, id, userId);
        return DataAccessUtils.singleResult(meals);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return jdbcTmpl.query("select * from meals where user_id = ? order by date_time desc", ROW_MAPPER, userId);
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return jdbcTmpl.query("select * from meals where date_time >= ? and date_time <= ? and user_id = ? order by date_time desc",
                ROW_MAPPER,
                startDate, endDate, userId);
    }
}
