package com.epam.hotel.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidation {
    private static final String PASSWORD_REGEX = "^[a-zA-Z0-9]{2,}$";
    private Pattern pattern = Pattern.compile(PASSWORD_REGEX);
    private Matcher matcher;

    public boolean isPasswordValid(String password) {
        boolean result = false;
        matcher = pattern.matcher(password);

        if (matcher.find())
            result = true;

        return result;
    }
}
