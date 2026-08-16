package com.example.QuanLyPhongGym.app.phongtap.chitietdonhang.query.get;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class GetChiTietDonHangQueryDTO {
    private String id;
    private String idDonHang;
    private String idGoiTap;
    private String idSanPham;
    private String tenSanPham;
    private String tenGoiTap;
    private Integer soLuong;
    private Integer soLuongSanPham;
    private BigDecimal gia;
    private BigDecimal giaSanPham;
    private BigDecimal tongTien;
}
