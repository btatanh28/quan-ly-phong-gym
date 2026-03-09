package com.example.QuanLyPhongGym.domain.entity.app.chitietdonhang;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "Chi_TIET_DON_HANG")
@Getter
@Setter

public class ChiTietDonHang {
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "ID_DON_HANG")
    private String idDonHang;

    @Column(name = "ID_GOI_TAP")
    private String idGoiTap;

    @Column(name = "SO_LUONG")
    private Integer soLuong;

    @Column(name = "GIA")
    private BigDecimal gia;

    @Column(name = "GIAM_GIA")
    private Integer giamGia;

    @Column(name = "TONG_TIEN", insertable = false, updatable = false)
    private BigDecimal tongTien;
}
