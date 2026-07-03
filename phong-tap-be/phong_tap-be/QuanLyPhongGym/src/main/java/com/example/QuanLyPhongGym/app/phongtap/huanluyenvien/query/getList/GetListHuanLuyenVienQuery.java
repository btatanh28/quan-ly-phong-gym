package com.example.QuanLyPhongGym.app.phongtap.huanluyenvien.query.getList;

import com.example.QuanLyPhongGym.core.annotation.Response;
import com.example.QuanLyPhongGym.core.model.response.PageResponse;

import lombok.Getter;
import lombok.Setter;

@Response(target = PageResponse.class)
@Getter
@Setter

public class GetListHuanLuyenVienQuery {
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
