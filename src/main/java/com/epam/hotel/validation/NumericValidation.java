package com.epam.hotel.validation;

public class NumericValidation {
    public static boolean isNumeric(String stringNum) {
        if (stringNum == null) {
            return false;
        }
        try {
            Double.parseDouble(stringNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}