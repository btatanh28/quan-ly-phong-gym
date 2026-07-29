package com.example.QuanLyPhongGym.core.service.vnpay.utils;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class VnpayUtil {

    public static String buildQuery(Map<String, String> params) {

        List<String> fieldNames = new ArrayList<>(params.keySet());

        Collections.sort(fieldNames);

        StringBuilder query = new StringBuilder();

        for (String fieldName : fieldNames) {

            String fieldValue = params.get(fieldName);

            if (fieldValue != null && !fieldValue.isEmpty()) {

                query.append(fieldName)
                        .append("=")
                        .append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII))
                        .append("&");
            }
        }

        if (query.length() > 0) {
            query.deleteCharAt(query.length() - 1);
        }

        return query.toString();
    }
}