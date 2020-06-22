package com.epam.hotel.util;

import java.util.Base64;

public class ImageEncoder {

    private final static String DATA_IMAGE_JPG_BASE_64 =  "data:image/jpg;base64,";

    public String encode(byte[] bytes) {

        Base64.Encoder encoder = Base64.getEncoder();

        String imageUrl = DATA_IMAGE_JPG_BASE_64 + encoder.encodeToString(bytes);

        return imageUrl;
    }
}