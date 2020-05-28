package com.epam.hotel.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IINValidation {
    private static final String IIN_REGEX = "^[0-9]{12}$";
    private Pattern pattern = Pattern.compile(IIN_REGEX);
    private Matcher matcher;

    public boolean isIINValid(String iin) {
        boolean result = false;
        pattern = Pattern.compile(IIN_REGEX);
        matcher = pattern.matcher(iin);

        if (matcher.find())
            result = true;

        return result;
    }
}
