package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;

/**
 * @author Cepro
 *         09.10.2016
 */

@ActiveProfiles(Profiles.JPA)
public class JpaMealServiceTest extends AbstractMealServiceTest {
}
