package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;

/**
 * @author Cepro
 *         09.10.2016
 */

@ActiveProfiles(Profiles.JPA)
public class JpaMealServiceTest extends MealServiceTest {
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
