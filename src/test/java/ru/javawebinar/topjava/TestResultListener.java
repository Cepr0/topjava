package ru.javawebinar.topjava;

import org.junit.runner.Result;
import org.junit.runner.notification.RunListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Collectors;

/**
 * @author Cepro
 *         11.10.16
 */
public class TestResultListener extends RunListener {
  private static final Logger LOGGER = LoggerFactory.getLogger(TestResultListener.class);
  
  // <"className", <"testName", duration>>
  static final private Map<String, Map<String, Long>> resultsData = new ConcurrentSkipListMap<>();
  
//  static Map<String, Long> putTestClassName(@NotNull String className) {
//    Map<String, Long> newTestResults = new HashMap<>();
//    Map<String, Long> curTestResults = resultsData.putIfAbsent(className, newTestResults);
//    return curTestResults == null ? newTestResults : curTestResults;
//  }
//
  static void putResults(@NotNull String className, @NotNull String testName, long duration) {
    Map<String, Long> testResults = resultsData.getOrDefault(className, new HashMap<>());
    testResults.put(testName, duration);
    resultsData.put(className, testResults);
  }

//  // Called before any tests have been run.
//  @Override
//  public void testRunStarted(Description description) throws Exception {
//  }

  // Called when all tests have finished
  @Override
  public void testRunFinished(Result result) throws Exception {

    StringBuilder resultStringBuilder = new StringBuilder();

    for (Map.Entry<String, Map<String, Long>> entry : resultsData.entrySet()) {
      String testClassName = entry.getKey();
      
      resultStringBuilder
          .append(String.format("%n%s%n", testClassName))
          .append(String.format("===============================%n"))
          .append(String.format("Test               Duration, ms%n"))
          .append(String.format("-------------------------------%n"));
      
      Map<String, Long> testClassResults = entry.getValue();
      
      resultStringBuilder
          .append(testClassResults.entrySet().stream()
              .sorted((e1, e2) -> e1.getValue().compareTo(e2.getValue()))
              .map(e -> String.format("%-25s %5d%n", e.getKey(), e.getValue()))
              .collect(Collectors.joining("")))
          .append(String.format("-------------------------------%n"));
    }
  
    LOGGER.info(resultStringBuilder.toString());
  }
}

