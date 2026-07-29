package com.example.QuanLyPhongGym.core.service.vnpay.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class VnpayRequest {
    private Long amount;

    private String orderId;
}
