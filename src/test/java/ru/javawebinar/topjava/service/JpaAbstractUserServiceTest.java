package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.repository.JpaUtil;

/**
 * @author Cepro
 *         15.10.16
 */
public abstract class JpaAbstractUserServiceTest extends AbstractUserServiceTest {
  
  @Autowired
  protected JpaUtil jpaUtil;
  
  @Before
  public void setUp() throws Exception {
    super.setUp();
    jpaUtil.clear2ndLevelHibernateCache();
  }
  
  
}
