package com.example.QuanLyPhongGym.domain.entity.app.thetapgoitap;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "THE_TAP_GOI_TAP")
@Getter
@Setter

public class TheTapGoiTap {
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "ID_THE_TAP")
    private String idTheTap;

    @Column(name = "ID_GOI_TAP")
    private String idGoiTap;

    @Column(name = "NGAY_BAT_DAU")
    private Long ngayBatDau;

    @Column(name = "NGAY_KET_THUC")
    private Long ngayKetThuc;

    @Column(name = "SO_NGAY_CON_LAI")
    private Integer soNgayConLai;

    @Column(name = "TRANG_THAI")
    private Integer trangThai;

    @Column(name = "NGAY_TAO")
    private Long ngayTao;
}
