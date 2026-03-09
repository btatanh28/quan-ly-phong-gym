package com.example.QuanLyPhongGym.app.phongtap.khachhang.command.verifyEmail;

import org.springframework.stereotype.Component;

import com.example.QuanLyPhongGym.core.exception.NotFoundException;
import com.example.QuanLyPhongGym.core.model.response.DataResponse;
import com.example.QuanLyPhongGym.domain.entity.app.khachhang.KhachHang;
import com.example.QuanLyPhongGym.domain.repository.app.khachhang.KhachHangRespository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor

public class VerifyEmailCommandHandler {
    private final KhachHangRespository respository;

    public DataResponse handle(VerifyEmailCommand requset) {
        KhachHang khachHang = respository.findFirstByEmail(requset.getEmail());

        if (khachHang.getEmail() == null) {
            throw new NotFoundException("Email không tồn tại");
        }

        if (!khachHang.getMaXacNhan().equals(requset.getMaXacNhan())) {
            throw new NotFoundException("Mã xác nhận không đúng");
        }

        khachHang.setDaXacNhan(true);
        khachHang.setMaXacNhan(null);
        respository.save(khachHang);

        return new DataResponse(khachHang.getId());
    }
}
