package com.example.QuanLyPhongGym.domain.repository.app.baiviet;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.QuanLyPhongGym.domain.entity.app.baiviet.BaiViet;

public interface BaiVietRespository extends JpaRepository<BaiViet, String> {
    BaiViet findFirstById(String id);
}
