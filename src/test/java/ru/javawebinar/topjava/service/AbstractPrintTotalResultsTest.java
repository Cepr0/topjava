package ru.javawebinar.topjava.service;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public abstract class AbstractPrintTotalResultsTest {
  // set number of test classes
  // TODO maybe use reflection to determine count of inheritors?
  private static int TESTS_COUNT = 3;

  private final static StringBuilder testResults = new StringBuilder();
  private static final Map<String, Long> testDurations = new HashMap<>();
  private static boolean needToSetHeader = false;

  @Rule
  public Stopwatch stopwatch = new Stopwatch() {
    @Override
    protected void finished(long nanos, Description description) {
      String testName = description.getMethodName();
      long duration = TimeUnit.NANOSECONDS.toMillis(nanos);
      testDurations.put(testName, duration);
    }
  };

  @BeforeClass
  public static void init() {
    needToSetHeader = true;
  }

  public AbstractPrintTotalResultsTest() {
    // Set the header of the results table
    if (needToSetHeader) {
      testResults
          .append(String.format("%n%s%n", getResultTableTitle()))
          .append(String.format("=============================%n"))
          .append(String.format("Test             Duration, ms%n"))
          .append(String.format("-----------------------------%n"));
    }
    needToSetHeader = false;
  }

  @AfterClass
  public static void print() {
    TESTS_COUNT--;

    testResults
        .append(testDurations.entrySet().stream()
            .sorted((e1, e2) -> e1.getValue().compareTo(e2.getValue()))
            .map(e -> String.format("%-23s %5d%n", e.getKey(), e.getValue()))
            .collect(Collectors.joining("")))
        .append(String.format("-----------------------------%n"));

    testDurations.clear();

    if (TESTS_COUNT == 0) {
      System.out.println(testResults.toString());
    }
  }

  public abstract String getResultTableTitle();
}
