package ru.javawebinar.topjava.util.formatter;

import org.springframework.format.Formatter;
import ru.javawebinar.topjava.util.TimeUtil;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * @author Cepro
 *         23.10.16
 */
public class LocalDateFormatter implements Formatter<LocalDate> {
    @Override
    public LocalDate parse(String text, Locale locale) throws ParseException {
        return TimeUtil.parseLocalDate(text);
    }
    
    @Override
    public String print(LocalDate date, Locale locale) {
        return date.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }
}
