package com.example.QuanLyPhongGym.app.phongtap.checkin.query.getlist;


import com.example.QuanLyPhongGym.core.annotation.Response;
import com.example.QuanLyPhongGym.core.model.response.ListResponse;
import lombok.Getter;
import lombok.Setter;

@Response(target = ListResponse.class)
@Getter
@Setter

public class GetListLichSuCheckIn {
    private String id;
    private String idKhachHang;
    private String idTheTap;
    private String qrCode;
    private String tenKhachHang;
    private String soDienThoai;
    private Long thoiGian;
//    private String tenGoiTap;
}
