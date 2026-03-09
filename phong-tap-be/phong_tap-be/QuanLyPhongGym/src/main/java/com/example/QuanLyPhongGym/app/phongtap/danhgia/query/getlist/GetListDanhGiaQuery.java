package com.example.QuanLyPhongGym.app.phongtap.danhgia.query.getlist;

import com.example.QuanLyPhongGym.core.annotation.Response;
import com.example.QuanLyPhongGym.core.model.response.ListResponse;

import lombok.Getter;
import lombok.Setter;

@Response(target = ListResponse.class)
@Getter
@Setter

public class GetListDanhGiaQuery {
    private String id;
    private String idKhachHang;
    private String idGoiTap;
    private Long ngayDanhGia;
    private String danhGia;
    private String binhLuan;
    private String hinhAnh;
    private String tenGoiTap;
    private String tenKhachHang;
}
