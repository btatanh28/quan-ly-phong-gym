package com.example.QuanLyPhongGym.core.service.vnpay.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.QuanLyPhongGym.core.service.ThanhToanService;
import com.example.QuanLyPhongGym.core.service.vnpay.dto.VnpayRequest;
import com.example.QuanLyPhongGym.core.service.vnpay.service.VnpayService;
import com.example.QuanLyPhongGym.domain.entity.app.dondangky.DonDangKy;
import com.example.QuanLyPhongGym.domain.enums.TrangThaiSanPhamEnums;
import com.example.QuanLyPhongGym.domain.repository.app.dondangky.DonDangKyRespository;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/vnpay")
@RequiredArgsConstructor
public class VnpayController {

    private final VnpayService vnpayService;

    private final DonDangKyRespository donDangKyRepository;

    private final ThanhToanService thanhToanService;

    @PostMapping("/pay")
    public ResponseEntity<?> pay(@RequestBody VnpayRequest request) {

        String payUrl = vnpayService.createPayment(
                request.getAmount(),
                request.getOrderId());

        Map<String, String> result = new HashMap<>();

        result.put("payUrl", payUrl);

        return ResponseEntity.ok(result);
    }

    @PostMapping("/ipn")
    public ResponseEntity<?> paymentReturn(@RequestBody Map<String, Object> body) {

        String orderId = body.get("orderId").toString();
        String responseCode = body.get("responseCode").toString();

        DonDangKy donDangKy = donDangKyRepository.findById(orderId).orElse(null);

        if (donDangKy == null) {
            return ResponseEntity.ok().build();
        }

        if ("00".equals(responseCode)) {
            thanhToanService.xuLyThanhToanThanhCong(orderId);
        } else {
            // Chỉ hủy nếu đơn chưa thanh toán
            if (!TrangThaiSanPhamEnums.DATHANHTOAN.value
                    .equals(donDangKy.getTrangThaiSanPham())) {

                donDangKy.setTrangThaiSanPham(
                        TrangThaiSanPhamEnums.DAHUY.value);

                donDangKyRepository.save(donDangKy);
            }
        }

        return ResponseEntity.ok().build();
    }
}