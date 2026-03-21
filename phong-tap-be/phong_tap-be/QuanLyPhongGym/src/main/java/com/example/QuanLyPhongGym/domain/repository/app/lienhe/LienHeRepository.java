package com.example.QuanLyPhongGym.domain.repository.app.lienhe;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.QuanLyPhongGym.domain.entity.app.lienhe.LienHe;

public interface LienHeRepository extends JpaRepository<LienHe, String> {
    LienHe findFirstById(String id);
}
