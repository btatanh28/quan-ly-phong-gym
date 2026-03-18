package com.example.QuanLyPhongGym.domain.repository.app.thetapgoitap;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.QuanLyPhongGym.domain.entity.app.thetapgoitap.TheTapGoiTap;

public interface TheTapGoiTapRepository extends JpaRepository<TheTapGoiTap, String> {
    TheTapGoiTap findFirstById(String id);

    List<TheTapGoiTap> findAllByIdTheTap(String idTheTap);

    TheTapGoiTap findFirstByIdTheTap(String idTheTap);

}
