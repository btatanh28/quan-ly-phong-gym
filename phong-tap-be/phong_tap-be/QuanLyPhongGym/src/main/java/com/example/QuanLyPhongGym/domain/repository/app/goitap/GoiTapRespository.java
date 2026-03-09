package com.example.QuanLyPhongGym.domain.repository.app.goitap;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.QuanLyPhongGym.domain.entity.app.goitap.GoiTap;


public interface GoiTapRespository extends JpaRepository<GoiTap, String> {
    GoiTap findFirstById(String id);
}
