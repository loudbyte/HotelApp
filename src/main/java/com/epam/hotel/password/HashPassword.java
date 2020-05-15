package com.epam.hotel.password;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashPassword {

    public String getHashPassword (String input) {

        MessageDigest messageDigest;
        byte[] byteDigest = new byte[0];
        String MD5 = "MD5";

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
            hashHex = "0" + hashHex;
        }
        return hashHex;
    }

}
