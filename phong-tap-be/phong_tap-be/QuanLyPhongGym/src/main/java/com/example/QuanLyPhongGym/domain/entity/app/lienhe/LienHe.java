package com.example.QuanLyPhongGym.domain.entity.app.lienhe;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "LIEN_HE")
@Getter
@Setter

public class LienHe {
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "ID_KHACH_HANG")
    private String idKhachHang;

    @Column(name = "TEN_KHACH_HANG")
    private String tenKhachHang;

    @Column(name = "SO_DIEN_THOAI")
    private String soDienThoai;

    @Column(name = "NOI_DUNG")
    private String noiDung;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "NGAY_GUI")
    private Long ngayGui;
}
