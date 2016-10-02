package ru.javawebinar.topjava.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import static ru.javawebinar.topjava.model.Meal.*;

@Repository
@Transactional(readOnly = true)
public class JpaMealRepositoryImpl implements MealRepository {
  
  @PersistenceContext
  private EntityManager em;
  
  @Override
  @Transactional
  public Meal save(Meal meal, int userId) {
//    if (!meal.isNew() && get(meal.getId(), userId) == null) {
//      return null;
//    }
//
//    meal.setUser(em.getReference(User.class, userId));
    if (meal.isNew()) {
      meal.setUser(em.getReference(User.class, userId));
      em.persist(meal);
      return meal;
    } else {
      int count = em.createNamedQuery(UPDATE)
          .setParameter("dateTime", meal.getDateTime())
          .setParameter("description", meal.getDescription())
          .setParameter("calories", meal.getCalories())
          .setParameter("id", meal.getId())
          .setParameter("userId", userId)
          .executeUpdate();
      if (count == 0) {
        return null;
      } else {
        return meal;
      }
//      return em.merge(meal);
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
//    try {
//      int count = em.createQuery("delete from Meal where id = :id and user.id = :userId")
//          .setParameter("id", id)
//          .setParameter("userId", userId)
//          .executeUpdate();
//      return count > 0;
//
//    } catch (Exception e) {
//      return false;
//    }
  }
  
  @Override
  public Meal get(int id, int userId) {
//    Meal meal = em.find(Meal.class, id);
//
//    if (meal != null && meal.getUser().getId() == userId) {
//      return meal;
//    }
//    return null;
    try {
      return em.createNamedQuery(GET_BY_ID, Meal.class)
          .setParameter("id", id)
          .setParameter("userId", userId)
          .getSingleResult();
  
//      return em.createQuery("select meal FROM Meal meal WHERE meal.id = :id and meal.user.id = :userId", Meal.class)
//          .setParameter("id", id)
//          .setParameter("userId", userId)
//          .getSingleResult();

    } catch (NoResultException | NonUniqueResultException e) {
      return null;
    }
  }
  
  @Override
  public List<Meal> getAll(int userId) {
    TypedQuery<Meal> query = em.createNamedQuery(GET_ALL, Meal.class)
        .setParameter("userId", userId);
    
//    TypedQuery<Meal> query = em.createQuery("select meal FROM Meal meal WHERE meal.user.id = :userId order by meal.dateTime desc", Meal.class)
//        .setParameter("userId", userId);
//
      return query.getResultList();
  }
  
  @Override
  public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
    TypedQuery<Meal> query = em.createNamedQuery(GET_BETWEEN, Meal.class)
        .setParameter("userId", userId)
        .setParameter("fromDate", startDate)
        .setParameter("toDate", endDate);

//    TypedQuery<Meal> query = em.createQuery("select meal FROM Meal meal WHERE meal.user.id = :userId and meal.dateTime >= :fromDate and meal.dateTime <= :toDate order by meal.dateTime desc", Meal.class)
//        .setParameter("userId", userId)
//        .setParameter("fromDate", startDate)
//        .setParameter("toDate", endDate);
//
      return query.getResultList();
  }
}