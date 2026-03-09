package com.example.QuanLyPhongGym.domain.entity.app.thetap;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "THE_TAP")
@Getter
@Setter

public class TheTap {
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "ID_KHACH_HANG")
    private String idKhachHang;

    @Column(name = "QR_CODE")
    private Long qrCode;

    @Column(name = "TRANG_THAI")
    private Integer trangThai;

    @Column(name = "NGAY_TAO")
    private Long ngayTao;
}
