package ru.javawebinar.topjava.repository.jdbc;


import java.time.LocalDateTime;

/**
 * @author Cepro
 *         08.10.2016
 */
public interface DbFieldConverter<T> {
  T fromDateTime(LocalDateTime dateTime);
}
