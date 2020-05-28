package com.epam.hotel.validation;

import javax.servlet.http.Part;

public class ImageValidation {
    private static final String JPEG = "image/jpeg";

    public boolean isImageValid(Part part) {
        boolean result = false;

        if (part.getContentType().equals(JPEG))
            result = true;

        return result;
    }
}
