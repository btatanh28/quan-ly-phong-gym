package com.example.QuanLyPhongGym.domain.entity.app.comments;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "BINH_LUAN")
@Getter
@Setter

public class Comments {
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "ID_NGUOI_DUNG")
    private String idNguoiDung;

    @Column(name = "ID_KHACH_HANG")
    private String idKhachHang;

    @Column(name = "ID_DANH_GIA")
    private String idDanhGia;

    @Column(name = "NOI_DUNG")
    private String noiDung;

    @Column(name = "HINH_ANH")
    private String hinhAnh;

    @Column(name = "NGAY_BINH_LUAN")
    private Long ngayBinhLuan;
}
