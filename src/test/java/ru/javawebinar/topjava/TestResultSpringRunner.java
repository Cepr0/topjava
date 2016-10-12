package ru.javawebinar.topjava;

import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.InitializationError;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Cepro
 *         12.10.2016.
 */
public class TestResultSpringRunner extends SpringJUnit4ClassRunner {
  public TestResultSpringRunner(Class<?> clazz) throws InitializationError {
    super(clazz);
  }

  @Override public void run(RunNotifier notifier){
    notifier.addListener(new TestResultListener());
    super.run(notifier);
  }
}
