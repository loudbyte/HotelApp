package com.epam.hotel.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IINValidation {
    private static final String IIN_REGEX = "^[0-9]{12}$";

    public static boolean isIINValid(String iin) {
        boolean result = false;
        Pattern pattern = Pattern.compile(IIN_REGEX);
        Matcher matcher = pattern.matcher(iin);

        if (matcher.find())
            result = true;

        return result;
    }
}
