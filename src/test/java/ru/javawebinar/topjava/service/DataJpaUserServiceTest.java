package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static ru.javawebinar.topjava.UserTestData.*;

/**
 * @author Cepro
 *         09.10.2016
 */

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaUserServiceTest extends UserServiceTest {
  @Override
  public void setUp() throws Exception {
    super.setUp();
  }
  
  @Override
  public void testSave() throws Exception {
    super.testSave();
  }
  
  @Override
  public void testDuplicateMailSave() throws Exception {
    super.testDuplicateMailSave();
  }
  
  @Override
  public void testDelete() throws Exception {
    super.testDelete();
  }
  
  @Override
  public void testNotFoundDelete() throws Exception {
    super.testNotFoundDelete();
  }
  
  @Override
  public void testGet() throws Exception {
    super.testGet();
  }
  
  @Test
  public void testGetWithMeals() throws Exception {
    User user = service.getWithMeals(USER_ID);
    MATCHER.assertEquals(USER, user);
    MealTestData.MATCHER.assertCollectionEquals(MealTestData.MEALS, user.getMeals());
  }
    
  @Override
  public void testGetNotFound() throws Exception {
    super.testGetNotFound();
  }
  
  @Test(expected = NotFoundException.class)
  public void testGetWithMealsNotFound() throws Exception {
    service.getWithMeals(1);
  }
  
  @Override
  public void testGetByEmail() throws Exception {
    super.testGetByEmail();
  }
  
  @Override
  public void testGetAll() throws Exception {
    super.testGetAll();
  }
  
  @Override
  public void testUpdate() throws Exception {
    super.testUpdate();
  }
}
