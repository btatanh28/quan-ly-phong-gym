package com.example.QuanLyPhongGym.app.phongtap.khachhang.command.create;

import com.example.QuanLyPhongGym.core.annotation.Response;
import com.example.QuanLyPhongGym.core.model.response.DataResponse;

import lombok.Getter;
import lombok.Setter;

@Response(target = DataResponse.class)
@Getter
@Setter

public class CreateKhachHangCommand {
    private String id;
    private String tenKhachHang;
    private String matKhau;
    private String email;
    private String soDienThoai;
    private String diaChi;
    private String hinhAnh;
    private String vaiTro;
    private String maXacNhan;
    private Boolean daXacNhan;
}
