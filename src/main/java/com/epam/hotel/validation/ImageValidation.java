package com.epam.hotel.validation;

import javax.servlet.http.Part;

public class ImageValidation {
    private static final String JPEG = "image/jpeg";

    public static boolean isImageValid(Part part) {
        return part.getContentType().equals(JPEG);
    }
}
