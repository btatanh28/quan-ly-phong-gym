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
    private String tenGoiTap;
    private Integer soLuong;
    private BigDecimal gia;
    private BigDecimal tongTien;
}
