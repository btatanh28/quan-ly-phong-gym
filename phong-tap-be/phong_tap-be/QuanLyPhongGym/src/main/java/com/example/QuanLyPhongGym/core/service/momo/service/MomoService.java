package com.example.QuanLyPhongGym.core.service.momo.service;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.QuanLyPhongGym.core.service.momo.dto.MomoRequest;
import com.example.QuanLyPhongGym.core.service.momo.utils.HmacSHA256;

@Service

public class MomoService {
    @Value("${momo.endpoint}")
    private String endpoint;

    @Value("${momo.partnerCode}")
    private String partnerCode;

    @Value("${momo.accessKey}")
    private String accessKey;

    @Value("${momo.secretKey}")
    private String secretKey;

    @Value("${momo.redirectUrl}")
    private String redirectUrl;

    @Value("${momo.ipnUrl}")
    private String ipnUrl;

    public Map<String, Object> createPayment(long amount, String orderId) {

        String requestId = UUID.randomUUID().toString();
        String orderInfo = "Thanh toan don hang " + orderId;
        String requestType = "captureWallet";
        String extraData = "";

        String rawHash = "accessKey=" + accessKey +
                "&amount=" + amount +
                "&extraData=" + extraData +
                "&ipnUrl=" + ipnUrl +
                "&orderId=" + orderId +
                "&orderInfo=" + orderInfo +
                "&partnerCode=" + partnerCode +
                "&redirectUrl=" + redirectUrl +
                "&requestId=" + requestId +
                "&requestType=" + requestType;

        String signature;
        try {
            signature = HmacSHA256.sign(secretKey, rawHash);
        } catch (Exception e) {
            throw new RuntimeException("Cannot sign MoMo request", e);
        }

        MomoRequest req = new MomoRequest();
        req.setPartnerCode(partnerCode);
        req.setAccessKey(accessKey);
        req.setRequestId(requestId);
        req.setAmount(String.valueOf(amount));
        req.setOrderId(orderId);
        req.setOrderInfo(orderInfo);
        req.setRedirectUrl(redirectUrl);
        req.setIpnUrl(ipnUrl);
        req.setExtraData(extraData);
        req.setRequestType(requestType);
        req.setSignature(signature);

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(
                endpoint,
                req,
                Map.class);
    }
}
