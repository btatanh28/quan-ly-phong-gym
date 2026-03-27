package com.example.QuanLyPhongGym.app.phongtap.lienhe.query.getlist;

import com.example.QuanLyPhongGym.core.annotation.Response;
import com.example.QuanLyPhongGym.core.model.response.ListResponse;

import lombok.Getter;
import lombok.Setter;

@Response(target = ListResponse.class)
@Getter
@Setter

public class GetListLienHeQuery {
    private String id;
    private String idKhachHang;
    private String tenKhachHang;
    private String soDienThoai;
    private String noiDung;
    private String email;
    private Long ngayGui;
}
