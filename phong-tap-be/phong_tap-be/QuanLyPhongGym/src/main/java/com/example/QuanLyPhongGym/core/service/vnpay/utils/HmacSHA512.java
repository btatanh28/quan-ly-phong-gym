package com.example.QuanLyPhongGym.core.service.vnpay.utils;

import java.nio.charset.StandardCharsets;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class HmacSHA512 {

    public static String sign(String key, String data) {

        try {

            Mac mac = Mac.getInstance("HmacSHA512");

            SecretKeySpec secretKey = new SecretKeySpec(
                    key.getBytes(StandardCharsets.UTF_8),
                    "HmacSHA512");

            mac.init(secretKey);

            byte[] raw = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));

            return bytesToHex(raw);

        } catch (Exception e) {
            throw new RuntimeException("Cannot generate HmacSHA512", e);
        }
    }

    private static String bytesToHex(byte[] bytes) {

        StringBuilder hex = new StringBuilder(bytes.length * 2);

        for (byte b : bytes) {

            String s = Integer.toHexString(b & 0xff);

            if (s.length() == 1) {
                hex.append('0');
            }

            hex.append(s);
        }

        return hex.toString();
    }
}