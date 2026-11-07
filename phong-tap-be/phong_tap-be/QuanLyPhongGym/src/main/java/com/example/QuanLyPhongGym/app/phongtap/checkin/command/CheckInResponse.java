package com.example.QuanLyPhongGym.app.phongtap.checkin.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class CheckInResponse {
    private String qrCode;
    private String tenKhachHang;
}
