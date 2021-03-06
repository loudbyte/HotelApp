package com.epam.hotel.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LocaleValidator {
    private static final String LOCALE_REGEX = "^[a-z]{2}_[A-Z]{2}$";

    public static boolean isValid(String locale) {
        Pattern pattern = Pattern.compile(LOCALE_REGEX);
        Matcher matcher = pattern.matcher(locale);

        return matcher.find();
    }
}