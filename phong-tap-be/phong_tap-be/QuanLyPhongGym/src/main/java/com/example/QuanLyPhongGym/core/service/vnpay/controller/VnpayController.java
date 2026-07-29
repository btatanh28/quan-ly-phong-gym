package com.example.QuanLyPhongGym.core.service.vnpay.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.QuanLyPhongGym.core.service.GenarateCode;
import com.example.QuanLyPhongGym.core.service.Generator;
import com.example.QuanLyPhongGym.core.service.vnpay.dto.VnpayRequest;
import com.example.QuanLyPhongGym.core.service.vnpay.service.VnpayService;
import com.example.QuanLyPhongGym.domain.entity.app.chitietdonhang.ChiTietDonHang;
import com.example.QuanLyPhongGym.domain.entity.app.dondangky.DonDangKy;
import com.example.QuanLyPhongGym.domain.entity.app.goitap.GoiTap;
import com.example.QuanLyPhongGym.domain.entity.app.thetap.TheTap;
import com.example.QuanLyPhongGym.domain.entity.app.thetapgoitap.TheTapGoiTap;
import com.example.QuanLyPhongGym.domain.enums.TrangThaiSanPhamEnums;
import com.example.QuanLyPhongGym.domain.repository.app.chitietdonhang.ChiTietDonHangRespository;
import com.example.QuanLyPhongGym.domain.repository.app.dondangky.DonDangKyRespository;
import com.example.QuanLyPhongGym.domain.repository.app.goitap.GoiTapRespository;
import com.example.QuanLyPhongGym.domain.repository.app.thetap.TheTapRespository;
import com.example.QuanLyPhongGym.domain.repository.app.thetapgoitap.TheTapGoiTapRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/vnpay")
@RequiredArgsConstructor
public class VnpayController {

    private final VnpayService vnpayService;

    private final DonDangKyRespository donDangKyRepository;
    private final TheTapRespository theTapRespository;
    private final TheTapGoiTapRepository theTapGoiTapRepository;
    private final GoiTapRespository goiTapRespository;
    private final ChiTietDonHangRespository chiTietDonHangRepository;

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

            donDangKy.setTrangThaiSanPham(
                    TrangThaiSanPhamEnums.DATHANHTOAN.value);

            donDangKy.setNgayThanhToan(System.currentTimeMillis());

            List<ChiTietDonHang> chiTietDonHangs = chiTietDonHangRepository.findByIdDonHang(orderId);

            taoTheTap(donDangKy, chiTietDonHangs);

        } else {

            donDangKy.setTrangThaiSanPham(
                    TrangThaiSanPhamEnums.DAHUY.value);
        }

        donDangKyRepository.save(donDangKy);

        return ResponseEntity.ok().build();
    }

    public void taoTheTap(
            DonDangKy donDangKy,
            List<ChiTietDonHang> chiTietHangs) {

        Long now = System.currentTimeMillis();

        TheTap theTap = theTapRespository.findFirstByIdKhachHang(
                donDangKy.getIdKhachHang());

        if (theTap == null) {

            theTap = new TheTap();

            theTap.setId(Generator.generate());

            theTap.setIdKhachHang(donDangKy.getIdKhachHang());

            theTap.setQrCode(GenarateCode.generate());

            theTap.setTrangThai(1);

            theTap.setNgayTao(now);

            theTapRespository.save(theTap);
        }

        for (ChiTietDonHang ct : chiTietHangs) {

            GoiTap goiTap = goiTapRespository.findFirstById(ct.getIdGoiTap());

            TheTapGoiTap theTapGoiTap = new TheTapGoiTap();

            theTapGoiTap.setId(Generator.generate());

            theTapGoiTap.setIdTheTap(theTap.getId());

            theTapGoiTap.setIdGoiTap(ct.getIdGoiTap());

            theTapGoiTap.setNgayBatDau(now);

            theTapGoiTap.setNgayKetThuc(
                    now + goiTap.getSoNgay() * 24 * 60 * 60 * 1000L);

            theTapGoiTap.setSoNgayConLai(goiTap.getSoNgay());

            theTapGoiTap.setTrangThai(1);

            theTapGoiTap.setNgayTao(now);

            theTapGoiTapRepository.save(theTapGoiTap);
        }
    }

}