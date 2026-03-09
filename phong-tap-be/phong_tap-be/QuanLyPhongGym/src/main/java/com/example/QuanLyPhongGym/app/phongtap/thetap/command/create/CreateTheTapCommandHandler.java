package com.example.QuanLyPhongGym.app.phongtap.thetap.command.create;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.example.QuanLyPhongGym.domain.repository.app.dondangky.DonDangKyRespository;
import com.example.QuanLyPhongGym.domain.repository.app.thetap.TheTapRespository;

import lombok.RequiredArgsConstructor;

@Component
@Lazy
@RequiredArgsConstructor

public class CreateTheTapCommandHandler {
    private final DonDangKyRespository dangKyRespository;
    private final TheTapRespository theTapRespository;
    
}
