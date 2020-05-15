package com.epam.hotel.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidation {
    private static final String REGEX = "^([a-z0-9_\\.-]+)@([a-z0-9_\\.-]+)\\.([a-z\\.]{2,6})$";
    private Pattern pattern;
    private Matcher matcher;
    private boolean result;

    public boolean isEmailValid(String email) {
        pattern = Pattern.compile(REGEX, Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(email);

        if (matcher.find()) {
            result = true;
        } else {
            result = false;
       }
        return result;
    }
}
