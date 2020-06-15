package com.epam.hotel.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneValidation {
    private static final String REGEX = "^\\+([0-9\\-]?){9,11}[0-9]$";
    private static final Pattern pattern = Pattern.compile(REGEX);

    public static boolean isPhoneValid(String phone) {
        boolean result = false;
        Matcher matcher = pattern.matcher(phone);

        if (matcher.find())
            result = true;

        return result;
    }
}
