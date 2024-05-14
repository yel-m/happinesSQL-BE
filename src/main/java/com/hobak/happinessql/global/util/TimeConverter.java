package com.hobak.happinessql.global.util;

import com.hobak.happinessql.global.exception.GeneralException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TimeConverter {
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static LocalDate stringToLocalDate(String dateString) {
        try {
            return LocalDate.parse(dateString, formatter);
        } catch (DateTimeParseException e) {
            throw new GeneralException("Invalid date format. Please use yyyy-MM-dd.");
        }
    }
}
