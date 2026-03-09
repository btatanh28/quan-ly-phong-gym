package com.example.QuanLyPhongGym.domain.entity.app.goitap;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "GOI_TAP")
@Getter
@Setter

public class GoiTap {
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "TEN_GOI_TAP")
    private String tenGoiTap;

    @Column(name = "THOI_HAN_NGAY")
    private Integer thoiHanNgay;

    @Column(name = "SO_NGAY")
    private Integer soNgay;

    @Column(name = "HINH_ANH")
    private String hinhAnh;

    @Column(name = "GIA")
    private BigDecimal gia;

    @Column(name = "GIA_SAU_GIAM")
    private BigDecimal giaSauGiam;

    @Column(name = "MO_TA")
    private String moTa;

    @Column(name = "GIAM_GIA")
    private Integer giamGia;
}
