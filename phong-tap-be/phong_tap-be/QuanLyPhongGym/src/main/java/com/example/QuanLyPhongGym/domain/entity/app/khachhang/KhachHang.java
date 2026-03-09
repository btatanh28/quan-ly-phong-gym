package com.example.QuanLyPhongGym.domain.entity.app.khachhang;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "KHACH_HANG")
@Table(name = "KHACH_HANG")
@Getter
@Setter

public class KhachHang {
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "TEN_KHACH_HANG")
    private String tenKhachHang;

    @Column(name = "MAT_KHAU")
    private String matKhau;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "SO_DIEN_THOAI")
    private String soDienThoai;

    @Column(name = "DIA_CHI")
    private String diaChi;

    @Column(name = "HINH_ANH")
    private String hinhAnh;

    @Column(name = "VAI_TRO")
    private Integer vaiTro;

    @Column(name = "MA_XAC_NHAN")
    private String maXacNhan;

    @Column(name = "DA_XAC_NHAN")
    private Boolean daXacNhan;
}
