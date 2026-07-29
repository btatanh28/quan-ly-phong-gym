package com.example.QuanLyPhongGym.core.service.vnpay.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.QuanLyPhongGym.core.service.vnpay.utils.HmacSHA512;
import com.example.QuanLyPhongGym.core.service.vnpay.utils.VnpayUtil;

@Service

public class VnpayService {
    @Value("${vnpay.payUrl}")
    private String payUrl;

    @Value("${vnpay.tmnCode}")
    private String tmnCode;

    @Value("${vnpay.hashSecret}")
    private String secretKey;

    @Value("${vnpay.returnUrl}")
    private String returnUrl;

    public String createPayment(long amount, String orderId) {

        Map<String, String> params = new HashMap<>();

        params.put("vnp_Version", "2.1.0");
        params.put("vnp_Command", "pay");
        params.put("vnp_TmnCode", tmnCode);

        params.put("vnp_Amount", String.valueOf(amount * 100));

        params.put("vnp_CurrCode", "VND");

        params.put("vnp_TxnRef", orderId);

        params.put("vnp_OrderInfo", "Thanh toan goi tap");

        params.put("vnp_OrderType", "other");

        params.put("vnp_Locale", "vn");

        params.put("vnp_ReturnUrl", returnUrl);

        params.put("vnp_IpAddr", "127.0.0.1");

        params.put("vnp_CreateDate", createDate());

        params.put("vnp_ExpireDate", expireDate());

        String query = VnpayUtil.buildQuery(params);

        String secureHash = HmacSHA512.sign(secretKey, query);

        return payUrl + "?" + query + "&vnp_SecureHash=" + secureHash;
    }

    private String createDate() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        formatter.setTimeZone(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));

        return formatter.format(calendar.getTime());
    }

    private String expireDate() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
        calendar.add(Calendar.MINUTE, 15);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        formatter.setTimeZone(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));

        return formatter.format(calendar.getTime());
    }
}
