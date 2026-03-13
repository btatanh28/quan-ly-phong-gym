package com.example.QuanLyPhongGym.domain.repository.app.thetap;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.QuanLyPhongGym.domain.entity.app.thetap.TheTap;

public interface TheTapRespository extends JpaRepository<TheTap, String> {
    TheTap findFirstById(String id);

    TheTap findFirstByIdKhachHang(String idKhachHang);

    TheTap findFirstByQrCode(String qrCode);
}
