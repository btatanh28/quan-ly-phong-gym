package com.example.QuanLyPhongGym.domain.repository.app.danhgia;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.QuanLyPhongGym.domain.entity.app.danhgia.DanhGia;

public interface DanhGiaRespository extends JpaRepository<DanhGia, String> {
    DanhGia findFirstById(String id);
}
