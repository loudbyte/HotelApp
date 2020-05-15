package com.epam.hotel.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidation {
    private static final String REGEX = "^[a-zA-Z0-9]{2,}$";
    private Pattern pattern = Pattern.compile(REGEX);
    private Matcher matcher;
    private boolean result;

    public boolean isPasswordValid(String password) {
        matcher = pattern.matcher(password);

        if (matcher.find()) {
            result = true;
        } else {
            result = false;
        }
        return result;
    }
}
