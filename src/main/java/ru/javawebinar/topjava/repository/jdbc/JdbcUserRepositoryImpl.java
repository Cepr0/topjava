package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import javax.sql.DataSource;
import java.util.List;

/**
 * User: gkislin
 * Date: 26.08.2014
 */

@Repository
public class JdbcUserRepositoryImpl implements UserRepository {
    
    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);
    private static final RowMapper<User> ROW_MAPPER_WITH_ROLE = (rs, rowNum) -> {
        User user = ROW_MAPPER.mapRow(rs, rowNum);
        user.setRole(Role.valueOf(rs.getString("role")));
        return user;
    };
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    
    private SimpleJdbcInsert insertUser;
    
    @Autowired
    public JdbcUserRepositoryImpl(DataSource dataSource) {
        this.insertUser = new SimpleJdbcInsert(dataSource)
                .withTableName("USERS")
                .usingGeneratedKeyColumns("id");
    }
    
    @Override
    public User save(User user) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", user.getId())
                .addValue("name", user.getName())
                .addValue("email", user.getEmail())
                .addValue("password", user.getPassword())
                .addValue("registered", user.getRegistered())
                .addValue("enabled", user.isEnabled())
                .addValue("caloriesPerDay", user.getCaloriesPerDay());
        
        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(map);
            user.setId(newKey.intValue());
        } else {
            namedParameterJdbcTemplate.update(
                    "update users set name=:name, email=:email, password=:password, " +
                            "registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay where id=:id", map);
        }
        return user;
    }
    
    @Override
    public boolean delete(int id) {
        return jdbcTemplate.update("delete from users where id=?", id) != 0;
    }
    
    @Override
    public User get(int id) {
        List<User> users = jdbcTemplate.query("select * from users where id=?", ROW_MAPPER, id);
        return DataAccessUtils.singleResult(users);
    }
    
    @Override
    public User getByEmail(String email) {
        List<User> users = jdbcTemplate.query("select * from users where email=?", ROW_MAPPER, email);
        return DataAccessUtils.singleResult(users);
    }
    
    @Override
    public List<User> getAll() {
        List<User> users = jdbcTemplate.query("select distinct u.id, u.email, u.name, u.enabled, r.role, u.calories_per_day from users u left join user_roles r on r.user_id = u.id order by name, email", ROW_MAPPER_WITH_ROLE);
        return users;
    }
}
