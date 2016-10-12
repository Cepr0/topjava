package ru.javawebinar.topjava;

import org.junit.Rule;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;

import java.util.concurrent.TimeUnit;

/**
 * @author Cepro
 *         11.10.16
 */
public abstract class TestResultBroadcaster {

  @Rule
  public Stopwatch stopwatch = new Stopwatch() {
    @Override
    protected void finished(long nanos, Description description) {
      String className = description.getTestClass().getSimpleName();
      String testName = description.getMethodName();
      long duration = TimeUnit.NANOSECONDS.toMillis(nanos);
      TestResultListener.putResults(className, testName, duration);
    }
  };
}
