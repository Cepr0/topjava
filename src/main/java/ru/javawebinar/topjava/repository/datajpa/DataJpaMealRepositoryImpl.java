package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class DataJpaMealRepositoryImpl implements MealRepository {
  
  private static final Sort ORDER_BY_DATETIME_DESC = new Sort(Sort.Direction.DESC, "dateTime");
  @Autowired
  private CrudMealRepository crudMealRepository;
  
  @Autowired
  private CrudUserRepository crudUserRepository;
  
  @Override
  public Meal save(Meal meal, int userId) {
    if (!meal.isNew() && get(meal.getId(), userId) == null) {
      return null;
    }
    meal.setUser(crudUserRepository.findOne(userId));
    return crudMealRepository.save(meal);
  }
  
  @Override
  public boolean delete(int id, int userId) {
    return crudMealRepository.delete(id, userId) != 0;
  }
  
  @Override
  public Meal get(int id, int userId) {
//    Meal meal = crudMealRepository.findOne(id);
//    return meal.getUser().getId() == userId ? meal : null;
    return crudMealRepository.get(id, userId);
  }
  
  @Override
  public List<Meal> getAll(int userId) {
    //return crudMealRepository.findByUserOrderByDateTimeDesc(crudUserRepository.getOne(userId));
    return crudMealRepository.getAll(userId);
  }
  
  @Override
  public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
    //return crudMealRepository.findByUserAndDateTimeBetweenOrderByDateTimeDesc(crudUserRepository.getOne(userId), startDate, endDate);
    return crudMealRepository.getBetween(userId, startDate, endDate);
  }
}
