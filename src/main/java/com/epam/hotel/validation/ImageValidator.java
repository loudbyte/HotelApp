package com.epam.hotel.validation;

import javax.servlet.http.Part;

public class ImageValidator {
    private static final String JPEG = "image/jpeg";

    public static boolean isImageValid(Part part) {
        boolean result = false;
        if (part != null)
            result = part.getContentType().equals(JPEG);

        return result;
    }
}