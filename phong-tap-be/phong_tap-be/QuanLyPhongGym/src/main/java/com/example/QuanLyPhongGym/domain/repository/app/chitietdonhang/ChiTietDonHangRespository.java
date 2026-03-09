package com.example.QuanLyPhongGym.domain.repository.app.chitietdonhang;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.QuanLyPhongGym.domain.entity.app.chitietdonhang.ChiTietDonHang;

public interface ChiTietDonHangRespository extends JpaRepository<ChiTietDonHang, String> {
    ChiTietDonHang findFirstById(String id);

    List<ChiTietDonHang> findAllByIdDonHang(String idDonHang);
}
