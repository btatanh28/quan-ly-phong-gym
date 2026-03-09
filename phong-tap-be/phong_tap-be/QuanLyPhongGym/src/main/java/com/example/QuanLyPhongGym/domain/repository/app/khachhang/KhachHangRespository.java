package com.example.QuanLyPhongGym.domain.repository.app.khachhang;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.QuanLyPhongGym.domain.entity.app.khachhang.KhachHang;


public interface KhachHangRespository extends JpaRepository<KhachHang, String> {
    KhachHang findFirstById(String id);

    boolean existsByEmail(String email);

    boolean existsBySoDienThoai(String soDienThoai);

    KhachHang findFirstByEmail(String email);
}
