package com.example.QuanLyPhongGym.app.phongtap.lienhe.command.create;

import com.example.QuanLyPhongGym.core.annotation.Response;
import com.example.QuanLyPhongGym.core.model.response.DataResponse;

import lombok.Getter;
import lombok.Setter;

@Response(target = DataResponse.class)
@Getter
@Setter

public class CreateLienHeCommand {
    private String id;
    private String idKhachHang;
    private String tenKhachHang;
    private String soDienThoai;
    private String email;
    private String noiDung;
}
