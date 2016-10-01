package ru.javawebinar.topjava.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;

/**
 * User: gkisline
 * Date: 26.08.2014
 */

@Repository
@Transactional(readOnly = true)
public class JpaMealRepositoryImpl implements MealRepository {
  
  @PersistenceContext
  private EntityManager em;
  
  @Override
  @Transactional
  public Meal save(Meal meal, int userId) {
    // TODO Проверить что находиться в userId в случае апдэйта
    if (!meal.isNew() && get(meal.getId(), userId) == null) {
      return null;
    }
    
    meal.setUser(em.getReference(User.class, userId));
    if (meal.isNew()) {
      em.persist(meal);
      return meal;
    } else {
      return em.merge(meal);
    }
  }
  
  @Override
  @Transactional
  public boolean delete(int id, int userId) {
    if (get(id, userId) != null) {
      em.remove(em.getReference(Meal.class, id));
      return true;
    }
    return false;
  }
  
  @Override
  public Meal get(int id, int userId) {
    Meal meal = em.find(Meal.class, id); //getReference(Meal.class, id);
    
    // TODO Убедиться, что userId присутствует в Meal
    if (meal != null && meal.getUser().getId() == userId) {
      return meal;
    }
    return null;
  }
  
  @Override
  public List<Meal> getAll(int userId) {
    TypedQuery<Meal> query = em.createQuery("select meal FROM Meal meal WHERE meal.user.id = :userId order by meal.dateTime desc", Meal.class)
        .setParameter("userId", userId);
    return query.getResultList();
  }
  
  @Override
  public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
    TypedQuery<Meal> query = em.createQuery("select meal FROM Meal meal WHERE meal.user.id = :userId and meal.dateTime >= :fromDate and meal.dateTime <= :toDate order by meal.dateTime desc", Meal.class)
        .setParameter("userId", userId)
        .setParameter("fromDate", startDate)
        .setParameter("toDate", endDate);
    
    return query.getResultList();
  }
}