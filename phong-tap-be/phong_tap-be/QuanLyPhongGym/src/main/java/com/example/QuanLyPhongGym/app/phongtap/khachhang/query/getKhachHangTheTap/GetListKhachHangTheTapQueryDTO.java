package com.example.QuanLyPhongGym.app.phongtap.khachhang.query.getKhachHangTheTap;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class GetListKhachHangTheTapQueryDTO {
    private String id;
    private String idKhachHang;
    private String idGoiTap;
    private String tenKhachHang;
    private String tenGoiTap;
    private String soDienThoai;
    private Integer page;
    private Integer size;
}
