package com.epam.hotel.validation;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import static com.epam.hotel.dao.impl.DAOConstant.DATE_PATTERN;

public class AgeValidation {

    private static final DateTimeFormatter DATE_FORMAT_VALIDATION = DateTimeFormatter.ofPattern(DATE_PATTERN);
    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 100;
    private static LocalDate nowDate = LocalDate.now();

    public static boolean isAgeValid(String birthday) {
        boolean result = false;
        LocalDate birthDate = LocalDate.parse(birthday, DATE_FORMAT_VALIDATION);
        int yearDifference = Period.between(nowDate, birthDate).getYears();

        if (yearDifference >= MIN_AGE || yearDifference < MAX_AGE)
            result = true;

        return result;
    }
}