package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.javawebinar.topjava.Profiles;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @author Cepro
 *         09.10.2016
 */
@Component
@Profile(Profiles.HSQLDB)
public class HsqlFieldConverter implements DbFieldConverter<Timestamp> {
  @Override
  public Timestamp fromDateTime(LocalDateTime dateTime) {
    return Timestamp.valueOf(dateTime);
  }
}
