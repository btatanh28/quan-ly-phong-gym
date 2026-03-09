package com.example.QuanLyPhongGym.app.phongtap.danhgia.query.getlist;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class GetListDanhGiaQueryDTO {
    private String id;
    private String idKhachHang;
    private String idGoiTap;
    private Long ngayDanhGia;
    private Integer danhGia;
    private String binhLuan;
    private String hinhAnh;
    private String tenGoiTap;
    private String tenKhachHang;
    private String hinhAnhKhachHang;
}
