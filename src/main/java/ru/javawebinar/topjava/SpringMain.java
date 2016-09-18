package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;

/**
 * User: gkislin
 * Date: 22.08.2014
 */
public class SpringMain {
    public static void main(String[] args) {
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            String[] beanDefinitionNames = appCtx.getBeanDefinitionNames();
            System.err.println("Bean definition names:");
            Arrays.stream(beanDefinitionNames).forEach(System.err::println);

//            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
//            adminUserController.create(new User(1, "userName", "email", "password", Role.ROLE_ADMIN));
        }
    }
}
