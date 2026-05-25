package com.example.QuanLyPhongGym.app.phongtap.checkin.command;

import com.example.QuanLyPhongGym.core.annotation.Response;
import com.example.QuanLyPhongGym.core.model.response.ApiResponse;

import lombok.Getter;
import lombok.Setter;

@Response(target = ApiResponse.class)
@Getter
@Setter

public class CheckInCommand {
    private String qrCode;
    private String thietBi;
}
