package com.example.QuanLyPhongGym.domain.entity.app.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "NGUOI_DUNG")
@Getter
@Setter

public class User {
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "TEN_NGUOI_DUNG")
    private String tenNguoiDung;

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

    @Column(name = "CCCD")
    private String cccd;

    @Column(name = "VAI_TRO")
    private Integer vaiTro;

    @Column(name = "LUONG")
    private Integer luong;

    @Column(name = "NGAY_VAO_LAM")
    private Long ngayVaoLam;
}
