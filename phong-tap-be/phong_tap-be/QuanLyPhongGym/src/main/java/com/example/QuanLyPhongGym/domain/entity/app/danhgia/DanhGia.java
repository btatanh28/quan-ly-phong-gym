package com.example.QuanLyPhongGym.domain.entity.app.danhgia;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "DANH_GIA")
@Getter
@Setter

public class DanhGia {
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "ID_KHACH_HANG")
    private String idKhachHang;

    @Column(name = "ID_GOI_TAP")
    private String idGoiTap;

    @Column(name = "DANH_GIA")
    private Integer danhGia;

    @Column(name = "BINH_LUAN")
    private String binhLuan;

    @Column(name = "HINH_ANH")
    private String hinhAnh;

    @Column(name = "NGAY_DANH_GIA")
    private Long ngayDanhGia;
}
