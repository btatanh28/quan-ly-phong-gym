package com.example.QuanLyPhongGym.app.phongtap.khachhang.command.create;

import java.util.Random;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.QuanLyPhongGym.core.exception.NotFoundException;
import com.example.QuanLyPhongGym.core.model.response.DataResponse;
import com.example.QuanLyPhongGym.core.service.EmailService;
import com.example.QuanLyPhongGym.domain.entity.app.khachhang.KhachHang;
import com.example.QuanLyPhongGym.domain.enums.VaiTroEnums;
import com.example.QuanLyPhongGym.domain.repository.app.khachhang.KhachHangRespository;

import lombok.RequiredArgsConstructor;

@Component
@Lazy
@RequiredArgsConstructor

public class CreateKhachHangCommandHandler {
    private final KhachHangRespository respository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public DataResponse handle(CreateKhachHangCommand request) {
        if (respository.existsByEmail(request.getEmail())) {
            throw new NotFoundException("Email này đã có");
        }

        if (respository.existsBySoDienThoai(request.getSoDienThoai())) {
            throw new NotFoundException("Số điện thoại này đã có");
        }

        KhachHang khachHang = new KhachHang();
        khachHang.setId(request.getId());
        khachHang.setTenKhachHang(request.getTenKhachHang());
        khachHang.setEmail(request.getEmail());
        khachHang.setSoDienThoai(request.getSoDienThoai());
        khachHang.setDiaChi(request.getDiaChi());
        khachHang.setMatKhau(passwordEncoder.encode(request.getMatKhau()));
        khachHang.setVaiTro(VaiTroEnums.KHACHHANG.value);

        String code = String.format("%06d", new Random().nextInt(999999));
        khachHang.setMaXacNhan(code);
        khachHang.setDaXacNhan(false);

        respository.save(khachHang);

        emailService.sendVerificationEmail(khachHang.getEmail(), code);

        return new DataResponse(khachHang.getId());
    }
}
