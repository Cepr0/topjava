package ru.javawebinar.topjava.service;

import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
    "classpath:spring/spring-app.xml",
    "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
@ActiveProfiles(Profiles.ACTIVE_DB)
public abstract class MealServiceTest {
  private static final Logger LOG = LoggerFactory.getLogger(MealServiceTest.class);
  
  @Rule
  public ExpectedException thrown = ExpectedException.none();
  
  private static final Map<String, Long> testDurations = new HashMap<>();
  
  static String testedClassName;
  
  @AfterClass
  public static void printResult() {
    if (!testDurations.isEmpty()) {
      Stream<String> stream = testDurations.entrySet().stream()
          .sorted((e1, e2) -> e1.getValue().compareTo(e2.getValue()))
          .map(e -> String.format("%-23s %5d", e.getKey(), e.getValue()));
  
      System.out.printf("%n%s%n", testedClassName);
      System.out.println("=============================");
      System.out.printf("Test             Duration, ms%n");
      System.out.println("-----------------------------");
      stream.forEach(System.out::println);
      System.out.printf("-----------------------------%n%n");
    }
  }
  
  @Rule
  // http://stackoverflow.com/questions/14892125/what-is-the-best-practice-to-determine-the-execution-time-of-the-bussiness-relev
  public Stopwatch stopwatch = new Stopwatch() {
    @Override
    protected void finished(long nanos, Description description) {
      String testName = description.getMethodName();
      long duration = TimeUnit.NANOSECONDS.toMillis(nanos);
      String result = String.format(">>> %s.%s duration is %d ms", testedClassName, testName, duration);
      LOG.info(result);
      
      testDurations.put(testName, duration);
    }
  };
  
  @Autowired
  protected MealService service;
  
  @Before
  public void setUp() throws Exception {
    setTestedClassName();
    service.evictCache();
  }
  
  public abstract void setTestedClassName();
    
  @Test
  public void testDelete() throws Exception {
    service.delete(MEAL1_ID, USER_ID);
    MATCHER.assertCollectionEquals(Arrays.asList(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2), service.getAll(USER_ID));
  }
  
  @Test
  public void testDeleteNotFound() throws Exception {
    thrown.expect(NotFoundException.class);
    service.delete(MEAL1_ID, 1);
  }
  
  @Test
  public void testSave() throws Exception {
    Meal created = getCreated();
    service.save(created, USER_ID);
    MATCHER.assertCollectionEquals(Arrays.asList(created, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1), service.getAll(USER_ID));
  }
  
  @Test
  public void testGet() throws Exception {
    Meal actual = service.get(ADMIN_MEAL_ID, ADMIN_ID);
    MATCHER.assertEquals(ADMIN_MEAL1, actual);
  }
  
  @Test
  public void testGetNotFound() throws Exception {
    thrown.expect(NotFoundException.class);
    service.get(MEAL1_ID, ADMIN_ID);
  }
  
  @Test
  public void testUpdate() throws Exception {
    Meal updated = getUpdated();
    service.update(updated, USER_ID);
    MATCHER.assertEquals(updated, service.get(MEAL1_ID, USER_ID));
  }
  
  @Test
  public void testNotFoundUpdate() throws Exception {
    Meal item = service.get(MEAL1_ID, USER_ID);
    thrown.expect(NotFoundException.class);
    thrown.expectMessage("Not found entity with id=" + MEAL1_ID);
    service.update(item, ADMIN_ID);
  }
  
  @Test
  public void testGetAll() throws Exception {
    MATCHER.assertCollectionEquals(MEALS, service.getAll(USER_ID));
  }
  
  @Test
  public void testGetBetween() throws Exception {
    MATCHER.assertCollectionEquals(Arrays.asList(MEAL3, MEAL2, MEAL1),
        service.getBetweenDates(LocalDate.of(2015, Month.MAY, 30), LocalDate.of(2015, Month.MAY, 30), USER_ID));
  }
}