package com.epam.hotel.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidation {
    private static final String EMAIL_REGEX = "^([a-z0-9_\\.-]+)@([a-z0-9_\\.-]+)\\.([a-z\\.]{2,6})$";

    public static boolean isEmailValid(String email) {
        boolean result = false;
        Pattern pattern = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);

        if (matcher.find())
            result = true;

        return result;
    }
}