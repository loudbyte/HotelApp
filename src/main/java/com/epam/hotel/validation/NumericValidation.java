package com.epam.hotel.validation;

public class NumericValidation {
    public static boolean isNumeric(String stringNum) {
        boolean result = true;
        if (stringNum == null) {
            result = false;
        } else {
            try {
                Double.parseDouble(stringNum);
            } catch (NumberFormatException nfe) {
                result = false;
            }
        }
        return result;
    }
}