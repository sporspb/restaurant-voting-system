package ru.spor.topjava.graduation.web;

import org.springframework.format.Formatter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Locale;

import static ru.spor.topjava.graduation.util.DateTimeUtil.parseLocalDate;
import static ru.spor.topjava.graduation.util.DateTimeUtil.parseLocalTime;


public class DateTimeFormatters {
    public static class LocalDateFormatter implements Formatter<LocalDate> {

        @Override
        public LocalDate parse(String text, Locale locale) {
            return parseLocalDate(text);
        }

        @Override
        public String print(LocalDate lt, Locale locale) {
            return lt.format(java.time.format.DateTimeFormatter.ISO_LOCAL_DATE);
        }
    }

    public static class LocalTimeFormatter implements Formatter<LocalTime> {

        @Override
        public LocalTime parse(String text, Locale locale) {
            return parseLocalTime(text);
        }

        @Override
        public String print(LocalTime lt, Locale locale) {
            return lt.format(java.time.format.DateTimeFormatter.ISO_LOCAL_TIME);
        }
    }
}
