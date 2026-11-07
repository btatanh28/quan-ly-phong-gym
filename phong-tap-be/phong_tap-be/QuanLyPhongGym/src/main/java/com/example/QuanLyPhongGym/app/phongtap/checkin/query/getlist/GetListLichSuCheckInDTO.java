package com.example.QuanLyPhongGym.app.phongtap.checkin.query.getlist;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class GetListLichSuCheckInDTO {
    private String id;
    private String idKhachHang;
    private String idTheTap;
    private String qrCode;
    private String tenKhachHang;
    private String soDienThoai;
    private Long thoiGian;
//    private String tenGoiTap;
}
