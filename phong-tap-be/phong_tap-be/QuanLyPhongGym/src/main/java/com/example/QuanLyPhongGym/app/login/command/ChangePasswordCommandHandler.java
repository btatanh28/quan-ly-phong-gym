package com.example.QuanLyPhongGym.app.login.command;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.QuanLyPhongGym.core.model.response.DataResponse;
import com.example.QuanLyPhongGym.domain.entity.app.khachhang.KhachHang;
import com.example.QuanLyPhongGym.domain.entity.app.user.User;
import com.example.QuanLyPhongGym.domain.repository.app.khachhang.KhachHangRespository;
import com.example.QuanLyPhongGym.domain.repository.app.user.UserRespository;

import lombok.RequiredArgsConstructor;

@Component
@Lazy
@RequiredArgsConstructor

public class ChangePasswordCommandHandler {
    private final UserRespository userRespository;
    private final KhachHangRespository khachHangRespository;
    private final PasswordEncoder passwordEncoder;

    public DataResponse handle(ChangePasswordCommand request) {
        User user = userRespository.findFirstByEmail(request.getEmail());
        if (user != null) {
            if (!request.getMatKhau().equals(request.getXacNhanMatKhau())) {
                throw new RuntimeException("Mật khẩu xác nhận không khớp");
            }

            user.setMatKhau(passwordEncoder.encode(request.getMatKhau()));

            userRespository.save(user);

            return new DataResponse(user.getId());
        }

        KhachHang khachHang = khachHangRespository.findFirstByEmail(request.getEmail());
        if (khachHang != null) {
            if (!request.getMatKhau().equals(request.getXacNhanMatKhau())) {
                throw new RuntimeException("Mật khẩu xác nhận không khớp");
            }

            khachHang.setMatKhau(passwordEncoder.encode(request.getMatKhau()));

            khachHangRespository.save(khachHang);

            return new DataResponse(khachHang.getId());
        }

        throw new RuntimeException("Không tìm thấy tài khoản với email đã cung cấp");
    }
}
