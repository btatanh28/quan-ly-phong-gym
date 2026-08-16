package com.example.QuanLyPhongGym.domain.entity.app.sanpham;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "SAN_PHAM")
@Getter
@Setter

public class SanPham {
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "TEN_SAN_PHAM")
    private String tenSanPham;

    @Column(name = "HINH_ANH")
    private String hinhAnh;

    @Column(name = "GIA")
    private BigDecimal gia;

    @Column(name = "SO_TON_KHO")
    private Integer soTonKho;

    @Column(name = "MO_TA")
    private String moTa;

    @Column(name = "GIAM_GIA")
    private Integer giamGia;

    @Column(name = "GIA_SAU_GIAM")
    private BigDecimal giaSauGiam;
}
