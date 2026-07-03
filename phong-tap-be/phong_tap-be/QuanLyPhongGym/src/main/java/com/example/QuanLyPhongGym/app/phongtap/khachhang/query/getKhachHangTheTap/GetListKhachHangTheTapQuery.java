package com.example.QuanLyPhongGym.app.phongtap.khachhang.query.getKhachHangTheTap;

import com.example.QuanLyPhongGym.core.annotation.Response;
import com.example.QuanLyPhongGym.core.model.response.ListResponse;

import lombok.Getter;
import lombok.Setter;

@Response(target = ListResponse.class)
@Getter
@Setter

public class GetListKhachHangTheTapQuery {
    private String id;
    private String idKhachHang;
    private String idGoiTap;
    private String tenKhachHang;
    private String tenGoiTap;
    private String soDienThoai;
    private Integer page;
    private Integer size;
}
