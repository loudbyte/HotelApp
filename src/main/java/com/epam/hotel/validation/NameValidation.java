package com.epam.hotel.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NameValidation {
    private static final String REGEX_EN = "^[A-Z]{1}[a-z]{1,}$";
    private static final String REGEX_RU = "^[А-Я]{1}[а-я]{1,}$";
    private Pattern patternEn = Pattern.compile(REGEX_EN);
    private Pattern patternRu = Pattern.compile(REGEX_RU);
    private Matcher matcherEn;
    private Matcher matcherRu;
    private boolean result;

    public boolean isNameValid(String name) {
        matcherEn = patternEn.matcher(name);
        matcherRu = patternRu.matcher(name);

        if (matcherEn.find() || matcherRu.find()) {
            result = true;
        } else {
            result = false;
        }
        return result;
    }
}
