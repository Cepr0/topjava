package ru.javawebinar.topjava;

import org.springframework.test.context.ActiveProfilesResolver;

import static ru.javawebinar.topjava.Profiles.HSQLDB;
import static ru.javawebinar.topjava.Profiles.POSTGRES;

/**
 * @author Cepro
 *         19.10.16
 */
public class SpringActiveProfileResolver implements ActiveProfilesResolver {
    @Override
    public String[] resolve(final Class<?> aClass) {
        final String property = System.getProperty("spring.profiles.active");
        String profile;
        if (property != null) {
            profile = property.contains(HSQLDB) ? HSQLDB : POSTGRES;
        } else {
            profile = HSQLDB;
        }
        return new String[]{profile};
    }
}