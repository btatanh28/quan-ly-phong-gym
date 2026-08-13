package com.example.QuanLyPhongGym.app.login.command;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.QuanLyPhongGym.core.exception.CustomException;
import com.example.QuanLyPhongGym.core.security.JwtTokenUtil;
import com.example.QuanLyPhongGym.domain.entity.app.khachhang.KhachHang;
import com.example.QuanLyPhongGym.domain.entity.app.user.User;
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
        User user = userRespository.findFirstByEmail(request.getUserAccount());

        User userSoDienThoai = userRespository.findFirstBySoDienThoai(request.getUserAccount());

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

        if (userSoDienThoai != null) {
            if (!passwordEncoder.matches(request.getMatKhau(), userSoDienThoai.getMatKhau())) {
                throw new RuntimeException("Sai mật khẩu");
            }

            String token = jwtTokenUtil.generateToken(userSoDienThoai.getEmail(), userSoDienThoai.getId(),
                    userSoDienThoai.getVaiTro());

            return new LoginResponse(
                    "Đăng nhập thành công",
                    token,
                    userSoDienThoai.getVaiTro(),
                    userSoDienThoai.getId(),
                    userSoDienThoai.getTenNguoiDung(),
                    userSoDienThoai.getSoDienThoai(),
                    userSoDienThoai.getDiaChi());
        }

        // 2. Check in KhachHang
        KhachHang khachHang = khachHangRespository.findFirstByEmail(request.getUserAccount());

        KhachHang khachHangSoDienThoai = khachHangRespository.findFirstBySoDienThoai(request.getUserAccount());

        if (khachHang != null) {
            if (!passwordEncoder.matches(request.getMatKhau(), khachHang.getMatKhau())) {
                throw new CustomException("404", "Sai mật khẩu");
            }

            if (khachHang.getDaXacNhan() == false) {
                throw new CustomException("404", "Tài khoản chưa kích hoạt");
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

        if (khachHangSoDienThoai != null) {
            if (!passwordEncoder.matches(request.getMatKhau(), khachHangSoDienThoai.getMatKhau())) {
                throw new CustomException("404", "Sai mật khẩu");
            }

            if (khachHangSoDienThoai.getDaXacNhan() == false) {
                throw new CustomException("404", "Tài khoản chưa kích hoạt");
            }

            String token = jwtTokenUtil.generateToken(khachHangSoDienThoai.getEmail(), khachHangSoDienThoai.getId(),
                    khachHangSoDienThoai.getVaiTro());

            return new LoginResponse(
                    "Đăng nhập thành công",
                    token,
                    khachHangSoDienThoai.getVaiTro(),
                    khachHangSoDienThoai.getId(),
                    khachHangSoDienThoai.getTenKhachHang(),
                    khachHangSoDienThoai.getSoDienThoai(),
                    khachHangSoDienThoai.getDiaChi());

        }

        throw new CustomException("404", "Email hoặc số điện thoại không đúng");
    }
}
