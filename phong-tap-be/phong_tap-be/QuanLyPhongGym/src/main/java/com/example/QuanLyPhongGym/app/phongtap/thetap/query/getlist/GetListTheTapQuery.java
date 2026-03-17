package com.example.QuanLyPhongGym.app.phongtap.thetap.query.getlist;

import com.example.QuanLyPhongGym.core.annotation.Response;
import com.example.QuanLyPhongGym.core.model.response.ListResponse;

import lombok.Getter;
import lombok.Setter;

@Response(target = ListResponse.class)
@Getter
@Setter

public class GetListTheTapQuery {
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
