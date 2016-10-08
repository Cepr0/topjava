package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.javawebinar.topjava.Profiles;

import java.time.LocalDateTime;

/**
 * @author Cepro
 *         09.10.2016
 */

@Component
@Profile(Profiles.POSTGRES)
public class PostgresFieldConverter implements DbFieldConverter<LocalDateTime> {
  @Override
  public LocalDateTime fromDateTime(LocalDateTime dateTime) {
    return dateTime;
  }
}