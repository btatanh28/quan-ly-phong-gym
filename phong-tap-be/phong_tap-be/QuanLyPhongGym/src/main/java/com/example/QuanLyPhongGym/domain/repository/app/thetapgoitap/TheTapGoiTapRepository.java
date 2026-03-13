package com.example.QuanLyPhongGym.domain.repository.app.thetapgoitap;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.QuanLyPhongGym.domain.entity.app.thetapgoitap.TheTapGoiTap;

public interface TheTapGoiTapRepository extends JpaRepository<TheTapGoiTap, String> {
    TheTapGoiTap findFirstById(String id);

    TheTapGoiTap findFirstByIdTheTap(String idTheTap);
}
