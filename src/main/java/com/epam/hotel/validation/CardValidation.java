package com.epam.hotel.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CardValidation {
    private static final String CARD_REGEX = "^[0-9]{16}$";

    public static boolean isValid(String cardNumber) {
        boolean result = false;
        Pattern pattern = Pattern.compile(CARD_REGEX);
        Matcher matcher = pattern.matcher(cardNumber);

        if (matcher.find())
            result = true;

        return result;
    }
}
