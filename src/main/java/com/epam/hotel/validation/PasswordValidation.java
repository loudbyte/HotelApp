package com.epam.hotel.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidation {
    private static final String PASSWORD_REGEX = "^[a-zA-Z0-9]{2,}$";
    private static final Pattern pattern = Pattern.compile(PASSWORD_REGEX);

    public static boolean isPasswordValid(String password) {
        boolean result = false;
        Matcher matcher = pattern.matcher(password);

        if (matcher.find())
            result = true;

        return result;
    }
}
