package com.example.QuanLyPhongGym.domain.entity.app.checkin;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "CHECK_IN")
@Getter
@Setter

public class CheckIn {
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "ID_KHACH_HANG")
    private String idKhachHang;

    @Column(name = "ID_THE_TAP")
    private String idTheTap;

    @Column(name = "THOI_GIAN")
    private Long thoiGian;

    @Column(name = "THIET_BI")
    private String thietBi;

    @Column(name = "TRANG_THAI")
    private Integer trangThai;
}
