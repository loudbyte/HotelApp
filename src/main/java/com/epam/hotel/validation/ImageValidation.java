package com.epam.hotel.validation;

import javax.servlet.http.Part;

public class ImageValidation {
    private boolean result = false;
    String jpeg = "image/jpeg";
    public boolean isImageValid(Part part) {
        if (part.getContentType().equals(jpeg)) {
            result = true;
        }
        return result;
    }
}
