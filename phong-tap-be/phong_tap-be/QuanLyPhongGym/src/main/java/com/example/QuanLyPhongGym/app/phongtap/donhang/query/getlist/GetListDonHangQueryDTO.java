package com.example.QuanLyPhongGym.app.phongtap.donhang.query.getlist;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class GetListDonHangQueryDTO {
    private String id;
    private String idKhachHang;
    private String idNguoiDung;
    private String tenNguoiDung;
    private String tenKhachHang;
    private String soDienThoai;
    private String email;
    private Long ngayMua;
    private Integer trangThaiSanPham;
    private Integer hinhThucThanhToan;
    private BigDecimal tongTien;
    private Long ngayCapNhat;
    private String trangThaiSanPhamLabel;
    private String hinhThucThanhToanLabel;
}
