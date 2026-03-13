package com.example.QuanLyPhongGym.domain.repository.app.checkin;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.QuanLyPhongGym.domain.entity.app.checkin.CheckIn;

public interface CheckInRepository extends JpaRepository<CheckIn, String> {
    CheckIn findFirstByIdTheTapAndThoiGianBetween(
            String idTheTap,
            Long start,
            Long end);

    CheckIn findFirstById(String id);
}
