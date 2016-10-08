package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {
  @Transactional
  @Modifying
  @Query("delete from Meal m where m.id = :id and m.user.id = :userId")
  int delete(
      @Param("id") int mealId,
      @Param("userId") int userId
  );
  
  @Query("select m from Meal m where m.id = :id and m.user.id = :userId")
  Meal get(
      @Param("id") int mealId,
      @Param("userId") int userId
  );
  
  @Query("select m from Meal m where m.user.id = :userId and m.dateTime >= :startDate and m.dateTime <= :endDate order by m.dateTime desc")
  List<Meal> getBetween(
      @Param("userId") int userId,
      @Param("startDate") LocalDateTime startDate,
      @Param("endDate") LocalDateTime endDate
  );
  
  @Query("select m from Meal m where m.user.id = :userId order by m.dateTime desc")
  List<Meal> getAll(@Param("userId") int userId);
  
  List<Meal> findByUserOrderByDateTimeDesc(User user);
  
  List<Meal> findByUserAndDateTimeBetweenOrderByDateTimeDesc(
      User user,
      LocalDateTime startDate,
      LocalDateTime endDate
  );
}
