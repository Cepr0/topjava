package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;

/**
 * @author Cepro
 *         09.10.2016
 */

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaMealServiceTest extends MealServiceTest {
  @Override
  public void setUp() throws Exception {
    super.setUp();
  }
  
  @Override
  public void setTestedClassName() {
    testedClassName = getClass().getSimpleName();
  }
  
  @Override
  public void testDelete() throws Exception {
    super.testDelete();
  }
  
  @Override
  public void testDeleteNotFound() throws Exception {
    super.testDeleteNotFound();
  }
  
  @Override
  public void testSave() throws Exception {
    super.testSave();
  }
  
  @Override
  public void testGet() throws Exception {
    super.testGet();
  }
  
  @Test
  public void testGetWithUser() throws Exception {
    Meal actual = service.getWithUser(ADMIN_MEAL_ID, ADMIN_ID);
    MATCHER.assertEquals(ADMIN_MEAL1, actual);
    UserTestData.MATCHER.assertEquals(UserTestData.ADMIN, actual.getUser());
  }
  
  @Test(expected = NotFoundException.class)
  public void testGetWithUserNotFound() throws Exception {
    service.getWithUser(MEAL1_ID, ADMIN_ID);
  }
  
  @Override
  public void testGetNotFound() throws Exception {
    super.testGetNotFound();
  }
  
  @Override
  public void testUpdate() throws Exception {
    super.testUpdate();
  }
  
  @Override
  public void testNotFoundUpdate() throws Exception {
    super.testNotFoundUpdate();
  }
  
  @Override
  public void testGetAll() throws Exception {
    super.testGetAll();
  }
  
  @Override
  public void testGetBetween() throws Exception {
    super.testGetBetween();
  }
}
