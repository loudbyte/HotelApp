package com.epam.hotel.util;

import java.util.Base64;

public class ImageEncoder {
    public String encode(byte[] bytes) {

        Base64.Encoder encoder = Base64.getEncoder();

        String imageUrl = "data:image/jpg;base64," + encoder.encodeToString(bytes);

        return imageUrl;
    }
}
