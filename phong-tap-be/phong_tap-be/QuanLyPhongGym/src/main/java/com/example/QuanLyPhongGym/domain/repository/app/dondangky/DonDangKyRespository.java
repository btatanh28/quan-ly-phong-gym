package com.example.QuanLyPhongGym.domain.repository.app.dondangky;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.QuanLyPhongGym.domain.entity.app.dondangky.DonDangKy;

public interface DonDangKyRespository extends JpaRepository<DonDangKy, String> {
    DonDangKy findFirstById(String id);

    List<DonDangKy> findAllByIdKhachHang(String idKhachHang);
}
