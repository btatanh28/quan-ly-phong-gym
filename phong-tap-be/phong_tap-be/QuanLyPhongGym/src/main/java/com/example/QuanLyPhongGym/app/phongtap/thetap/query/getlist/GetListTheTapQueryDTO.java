package com.example.QuanLyPhongGym.app.phongtap.thetap.query.getlist;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class GetListTheTapQueryDTO {
    private String id;
    private String idKhachHang;
    private String idGoiTap;
    private String tenKhachHang;
    private String tenGoiTap;
    private String soDienThoai;
    private String email;
    private Integer soNgayConLai;
    private Integer page;
    private Integer size;
}
