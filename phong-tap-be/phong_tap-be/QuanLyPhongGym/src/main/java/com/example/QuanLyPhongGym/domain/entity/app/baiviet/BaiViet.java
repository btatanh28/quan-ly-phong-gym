package com.example.QuanLyPhongGym.domain.entity.app.baiviet;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "BAI_VIET")
@Getter
@Setter

public class BaiViet {
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "ID_NGUOI_DUNG")
    private String idNguoiDung;

    @Column(name = "NOI_DUNG")
    private String noiDung;

    @Column(name = "HINH_ANH")
    private String hinhAnh;

    @Column(name = "NGAY_DANG_BAI")
    private Long ngayDangBai;

    @Column(name = "NGAY_CAP_NHAT")
    private Long ngayCapNhat;

    @Column(name = "TEN_BAI_VIET")
    private String tenBaiViet;
}
