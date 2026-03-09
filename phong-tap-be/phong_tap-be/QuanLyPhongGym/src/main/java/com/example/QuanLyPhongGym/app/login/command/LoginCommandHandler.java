package com.example.QuanLyPhongGym.app.login.command;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.QuanLyPhongGym.core.exception.NotFoundException;
import com.example.QuanLyPhongGym.core.security.JwtTokenUtil;
import com.example.QuanLyPhongGym.domain.repository.app.khachhang.KhachHangRespository;
import com.example.QuanLyPhongGym.domain.repository.app.user.UserRespository;

import lombok.RequiredArgsConstructor;

@Component
@Lazy
@RequiredArgsConstructor

public class LoginCommandHandler {
    private final UserRespository userRespository;
    private final KhachHangRespository khachHangRespository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    public LoginResponse handle(LoginCommand request) {

        // 1. Check in User
        var user = userRespository.findFirstByEmail(request.getEmail());
        if (user != null) {
            if (!passwordEncoder.matches(request.getMatKhau(), user.getMatKhau())) {
                throw new RuntimeException("Sai mật khẩu");
            }

            String token = jwtTokenUtil.generateToken(user.getEmail(), user.getId(), user.getVaiTro());

            return new LoginResponse(
                    "Đăng nhập thành công",
                    token,
                    user.getVaiTro(),
                    user.getId(),
                    user.getTenNguoiDung(),
                    user.getSoDienThoai(),
                    user.getDiaChi());
        }

        // 2. Check in KhachHang
        var khachHang = khachHangRespository.findFirstByEmail(request.getEmail());
        if (khachHang != null) {
            if (!passwordEncoder.matches(request.getMatKhau(), khachHang.getMatKhau())) {
                throw new RuntimeException("Sai mật khẩu");
            }

            if (khachHang.getDaXacNhan() == false) {
                throw new NotFoundException("Tài khoản chưa kích hoạt");
            }

            String token = jwtTokenUtil.generateToken(khachHang.getEmail(), khachHang.getId(), khachHang.getVaiTro());

            return new LoginResponse(
                    "Đăng nhập thành công",
                    token,
                    khachHang.getVaiTro(),
                    khachHang.getId(),
                    khachHang.getTenKhachHang(),
                    khachHang.getSoDienThoai(),
                    khachHang.getDiaChi());

        }

        throw new RuntimeException("Email không tồn tại");
    }
}
