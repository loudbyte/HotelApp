package com.epam.hotel.password;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncodePassword {

    private static final String MD5 = "MD5";
    private static final String STRING_ZERO = "0";

    public String getHashPassword (String input) {

        MessageDigest messageDigest;
        byte[] byteDigest = new byte[0];

        try {
            messageDigest = MessageDigest.getInstance(MD5);
            messageDigest.reset();
            byteDigest = messageDigest.digest(input.getBytes());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        BigInteger number = new BigInteger(1, byteDigest);
        String hashHex = number.toString(16);
        while (hashHex.length() < 32) {
            hashHex = STRING_ZERO + hashHex;
        }
        return hashHex;
    }
}