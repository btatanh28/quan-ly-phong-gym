package com.example.QuanLyPhongGym.domain.repository.app.sanpham;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.QuanLyPhongGym.domain.entity.app.sanpham.SanPham;

public interface SanPhamRepository extends JpaRepository<SanPham, String> {
    SanPham findFirstById(String id);

    List<SanPham> findAllById(String id);

}
