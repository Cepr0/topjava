package ru.javawebinar.topjava;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Arrays;

public class AppContextListener implements ServletContextListener {
    private final Logger LOG = LoggerFactory.getLogger(getClass());
    public static ConfigurableApplicationContext context;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        LOG.info("contextInitialized invoked");
        context = new ClassPathXmlApplicationContext("spring/spring-app.xml");

        String[] beans = context.getBeanDefinitionNames();
        System.err.println("Bean definition names:");
        Arrays.stream(beans).forEach(System.err::println);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        LOG.info("contextDestroyed invoked");
        context.close();
    }
}
