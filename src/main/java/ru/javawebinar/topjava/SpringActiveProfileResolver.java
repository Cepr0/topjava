package ru.javawebinar.topjava;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ActiveProfilesResolver;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author Cepro
 *         19.10.16
 */
public class SpringActiveProfileResolver implements ActiveProfilesResolver {
    private static final Logger LOG = LoggerFactory.getLogger(SpringActiveProfileResolver.class);
    
    @Override
    public String[] resolve(final Class<?> aClass) {
        final String property = System.getProperty("spring.profiles.active");
        String[] profiles;
        if (property != null) {
            profiles = property.split(",");
        } else {
            profiles = new String[]{Profiles.HSQLDB, Profiles.DATAJPA};
        }
        LOG.debug("Active profiles: {}", Arrays.stream(profiles).collect(Collectors.joining(", ")));
        return profiles;
    }
}