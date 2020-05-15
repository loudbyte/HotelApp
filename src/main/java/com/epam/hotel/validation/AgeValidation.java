package com.epam.hotel.validation;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import static com.epam.hotel.dao.impl.Constant.DATE_PATTERN;

public class AgeValidation {

    private static final DateTimeFormatter DATE_FORMAT_VALIDATION = DateTimeFormatter.ofPattern(DATE_PATTERN);
    private LocalDate nowDate = LocalDate.now();
    private LocalDate birthDate;
    private boolean result;
    private int minAge = 18;
    private int maxAge = 100;
    private int yearDifference;

    public boolean isAgeValid(String birthday) {
        birthDate = LocalDate.parse(birthday, DATE_FORMAT_VALIDATION);
        yearDifference = Period.between(nowDate, birthDate).getYears();
        if (yearDifference >= minAge || yearDifference < maxAge) {
            result = true;
        } else {
            result = false;
        }
        return result;
    }
}
