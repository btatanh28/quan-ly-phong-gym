package com.example.QuanLyPhongGym.app.phongtap.khachhang.command.update;

import com.example.QuanLyPhongGym.core.annotation.Response;
import com.example.QuanLyPhongGym.core.model.response.DataResponse;

import lombok.Getter;
import lombok.Setter;

@Response(target = DataResponse.class)
@Getter
@Setter

public class UpdateKhachHangCommand {
    private String id;
    private String tenKhachHang;
    private String email;
    private String soDienThoai;
    private String diaChi;
    private String hinhAnh;
}
