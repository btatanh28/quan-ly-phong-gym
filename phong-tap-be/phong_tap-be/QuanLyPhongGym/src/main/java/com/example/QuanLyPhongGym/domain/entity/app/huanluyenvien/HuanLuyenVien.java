package com.example.QuanLyPhongGym.domain.entity.app.huanluyenvien;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "HUAN_LUYEN_VIEN")
@Getter
@Setter

public class HuanLuyenVien {
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "ID_NGUOI_DUNG")
    private String idNguoiDung;

    @Column(name = "ID_KHACH_HANG")
    private String idKhachHang;

    @Column(name = "ID_GOI_TAP")
    private String idGoiTap;

    @Column(name = "SO_DIEN_THOAI")
    private String soDienThoai;

    @Column(name = "SO_DIEN_THOAI_HLV")
    private String soDienThoaiHLV;

    @Column(name = "EMAIL_HLV")
    private String emailHLV;

    @Column(name = "VAI_TRO")
    private Integer vaiTro;
}
