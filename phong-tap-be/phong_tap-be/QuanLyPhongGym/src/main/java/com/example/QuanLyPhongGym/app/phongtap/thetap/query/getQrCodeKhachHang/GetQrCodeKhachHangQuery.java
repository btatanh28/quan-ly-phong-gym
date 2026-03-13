package com.example.QuanLyPhongGym.app.phongtap.thetap.query.getQrCodeKhachHang;

import com.example.QuanLyPhongGym.core.annotation.Response;
import com.example.QuanLyPhongGym.core.model.response.ApiResponse;

import lombok.Getter;
import lombok.Setter;

@Response(target = ApiResponse.class)
@Getter
@Setter

public class GetQrCodeKhachHangQuery {
    private String id;
}
