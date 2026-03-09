package com.example.QuanLyPhongGym.app.phongtap.khachhang.query.getlist;

import com.example.QuanLyPhongGym.core.annotation.Response;
import com.example.QuanLyPhongGym.core.model.response.ListResponse;

import lombok.Getter;
import lombok.Setter;

@Response(target = ListResponse.class)
@Getter
@Setter

public class GetListKhachHangQuery {
    private String id;
    private String tenKhachHang;
    private String matKhau;
    private String email;
    private String soDienThoai;
    private String diaChi;
    private String hinhAnh;
}
