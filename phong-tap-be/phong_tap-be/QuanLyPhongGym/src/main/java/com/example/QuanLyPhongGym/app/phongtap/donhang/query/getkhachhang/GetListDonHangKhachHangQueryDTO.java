package com.example.QuanLyPhongGym.app.phongtap.donhang.query.getkhachhang;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class GetListDonHangKhachHangQueryDTO {
    private String id;
    private String idKhachHang;
    private String tenKhachHang;
    private String soDienThoai;
    private String email;
    private Long ngayMua;
    private Integer trangThaiSanPham;
    private Integer hinhThucThanhToan;
    private BigDecimal tongTien;
    private String trangThaiSanPhamLabel;
    private String hinhThucThanhToanLabel;
}
