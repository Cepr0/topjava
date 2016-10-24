package ru.javawebinar.topjava.util.formatter;

import org.springframework.format.Formatter;
import ru.javawebinar.topjava.util.TimeUtil;

import java.text.ParseException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * @author Cepro
 *         23.10.16
 */
public class LocalTimeFormatter implements Formatter<LocalTime> {
    @Override
    public LocalTime parse(String text, Locale locale) throws ParseException {
        return TimeUtil.parseLocalTime(text);
    }
    
    @Override
    public String print(LocalTime time, Locale locale) {
        return time.format(DateTimeFormatter.ofPattern("HH:mm")/*DateTimeFormatter.ISO_LOCAL_TIME*/);
    }
}
