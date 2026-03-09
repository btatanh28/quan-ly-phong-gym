package com.example.QuanLyPhongGym.core.service;

import java.security.SecureRandom;

public class GenarateCode {
    private static final String CHARACTERS = "0123456789";
    private static final int LENGTH = 13;

    private static final SecureRandom random = new SecureRandom();

    public static String generate() {
        StringBuilder sb = new StringBuilder(LENGTH);
        for (int i = 0; i < LENGTH; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }
}
