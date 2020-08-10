package com.epam.hotel.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CardValidator {
    private static final String CARD_REGEX = "^[0-9]{16}$";

    public static boolean isValid(String cardNumber) {
        Pattern pattern = Pattern.compile(CARD_REGEX);
        Matcher matcher = pattern.matcher(cardNumber);

        return matcher.find();
    }
}
