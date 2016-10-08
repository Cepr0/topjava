package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;

/**
 * @author Cepro
 *         09.10.2016
 */
@ActiveProfiles(Profiles.JPA)
public class JpaUserServiceTest extends UserServiceTest {
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
  
  @Override
  public void testGetNotFound() throws Exception {
    super.testGetNotFound();
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
