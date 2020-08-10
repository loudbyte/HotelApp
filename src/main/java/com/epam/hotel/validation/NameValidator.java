package com.epam.hotel.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NameValidator {
    private static final String REGEX_EN = "^[A-Z][a-z]+$";
    private static final String REGEX_RU = "^[А-Я][а-я]+$";
    private static final Pattern patternEn = Pattern.compile(REGEX_EN);
    private static final Pattern patternRu = Pattern.compile(REGEX_RU);

    public static boolean isNameValid(String name) {
        boolean result = false;
        Matcher matcherEn = patternEn.matcher(name);
        Matcher matcherRu = patternRu.matcher(name);

        if (matcherEn.find() || matcherRu.find())
            result = true;

        return result;
    }
}
