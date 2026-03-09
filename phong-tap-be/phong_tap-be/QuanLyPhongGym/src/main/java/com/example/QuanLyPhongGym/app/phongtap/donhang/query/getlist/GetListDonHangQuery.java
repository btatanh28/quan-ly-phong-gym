package com.example.QuanLyPhongGym.app.phongtap.donhang.query.getlist;

import java.math.BigDecimal;

import com.example.QuanLyPhongGym.core.annotation.Response;
import com.example.QuanLyPhongGym.core.model.response.PageResponse;

import lombok.Getter;
import lombok.Setter;

@Response(target = PageResponse.class)
@Getter
@Setter

public class GetListDonHangQuery {
    private String id;
    private String idKhachHang;
    private String idNguoiDung;
    private String tenNguoiDung;
    private String tenKhachHang;
    private String soDienThoai;
    private String email;
    private Long ngayMua;
    private BigDecimal tongTien;
    private Integer trangThaiSanPham;
    private Integer hinhThucThanhToan;
    private Long ngayCapNhat;
}
