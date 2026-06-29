package com.example.QuanLyPhongGym.core.service.momo.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.QuanLyPhongGym.core.service.momo.dto.MomoRequest;
import com.example.QuanLyPhongGym.core.service.momo.service.MomoService;
import com.example.QuanLyPhongGym.domain.entity.app.dondangky.DonDangKy;
import com.example.QuanLyPhongGym.domain.enums.TrangThaiSanPhamEnums;
import com.example.QuanLyPhongGym.domain.repository.app.dondangky.DonDangKyRespository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/momo")
@RequiredArgsConstructor
public class MomoController {

    private final MomoService momoService;

    private final DonDangKyRespository donDangKyRepository;

    /**
     * Tạo thanh toán MoMo
     */
    @PostMapping("/pay")
    public ResponseEntity<?> pay(
            @RequestBody MomoRequest request) {

        return ResponseEntity.ok(
                momoService.createPayment(
                        request.getAmount(),
                        request.getOrderId()));

    }

    /**
     * MoMo gọi về sau khi thanh toán
     */
    @PostMapping("/ipn")
    public ResponseEntity<?> ipn(
            @RequestBody Map<String, Object> body) {

        System.out.println(
                "MoMo IPN: "
                        + body);

        String orderId = body.get("orderId")
                .toString();

        Integer resultCode = Integer.parseInt(
                body.get("resultCode")
                        .toString());

        DonDangKy donDangKy = donDangKyRepository
                .findById(orderId)
                .orElse(null);

        if (donDangKy == null) {

            return ResponseEntity.ok()
                    .build();

        }

        if (resultCode == 0) {

            /*
             * Thanh toán thành công
             */

            donDangKy.setTrangThaiSanPham(
                    TrangThaiSanPhamEnums.DATHANHTOAN.value);

            donDangKy.setNgayThanhToan(
                    System.currentTimeMillis());

        } else {

            /*
             * Người dùng hủy
             * hoặc thanh toán thất bại
             */

            donDangKy.setTrangThaiSanPham(
                    TrangThaiSanPhamEnums.DAHUY.value);

        }

        donDangKyRepository.save(
                donDangKy);

        return ResponseEntity.ok()
                .build();

    }

}