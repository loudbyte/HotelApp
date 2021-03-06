package com.epam.hotel.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator {
    private static final String PASSWORD_REGEX = "^[a-zA-Z0-9]{2,}$";
    private static final Pattern pattern = Pattern.compile(PASSWORD_REGEX);

    public static boolean isPasswordValid(String password) {
        Matcher matcher = pattern.matcher(password);

        return matcher.find();
    }
}
