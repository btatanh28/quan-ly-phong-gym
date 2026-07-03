package com.example.QuanLyPhongGym.app.phongtap.huanluyenvien.query.getList;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class GetListHuanLuyenVienQueryDTO {
    private String id;
    private String idKhachHang;
    private String idNguoiDung;
    private String idGoiTap;
    private String soDienThoai;
    private String soDienThoaiHLV;
    private String emailHLV;
    private Integer vaiTro;
    private String tenNguoiDung;
    private String tenGoiTap;
    private String tenKhachHang;
    private Integer page;
    private Integer size;
}
