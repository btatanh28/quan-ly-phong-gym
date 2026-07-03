package com.example.QuanLyPhongGym.domain.repository.app.huanluyenvien;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.QuanLyPhongGym.domain.entity.app.huanluyenvien.HuanLuyenVien;

public interface HuanLuyenVienRespository extends JpaRepository<HuanLuyenVien, String> {
    HuanLuyenVien findFirstById(String id);

    List<HuanLuyenVien> findAllByIdKhachHang(String idKhachHang);
}
