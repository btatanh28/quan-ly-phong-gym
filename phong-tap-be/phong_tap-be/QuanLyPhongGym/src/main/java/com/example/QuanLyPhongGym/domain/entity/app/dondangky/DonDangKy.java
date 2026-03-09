package com.example.QuanLyPhongGym.domain.entity.app.dondangky;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "DON_DANG_KY")
@Getter
@Setter

public class DonDangKy {
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "ID_NGUOI_DUNG")
    private String idNguoiDung;

    @Column(name = "ID_KHACH_HANG")
    private String idKhachHang;

    // @Column(name = "ID_GOI_TAP")
    // private String idGoiTap;

    @Column(name = "NGAY_MUA")
    private Long ngayMua;

    @Column(name = "TRANG_THAI_SAN_PHAM")
    private Integer trangThaiSanPham;

    @Column(name = "NGAY_THANH_TOAN")
    private Long ngayThanhToan;

    @Column(name = "NGAY_CAP_NHAT")
    private Long ngayCapNhat;

    @Column(name = "HINH_THUC_THANH_TOAN")
    private Integer hinhThucThanhToan;

    @Column(name = "TONG_TIEN")
    private BigDecimal tongTien;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "SO_DIEN_THOAI")
    private String soDienThoai;
}
