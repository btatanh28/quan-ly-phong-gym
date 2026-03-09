package com.example.QuanLyPhongGym.core.service.momo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class MomoRequest {
    private String partnerCode;
    private String accessKey;
    private String requestId;
    private String amount;
    private String orderId;
    private String orderInfo;
    private String redirectUrl;
    private String ipnUrl;
    private String extraData;
    private String requestType;
    private String signature;
}
