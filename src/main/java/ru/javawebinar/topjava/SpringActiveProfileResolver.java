package ru.javawebinar.topjava;

import org.springframework.test.context.ActiveProfilesResolver;

/**
 * @author Cepro
 *         19.10.16
 */
public class SpringActiveProfileResolver implements ActiveProfilesResolver {
    
    @Override
    public String[] resolve(final Class<?> aClass) {
        final String activeDBProfile = System.getProperty("spring.profiles.active");
        return new String[]{activeDBProfile == null ? Profiles.HSQLDB : activeDBProfile};
    }
}