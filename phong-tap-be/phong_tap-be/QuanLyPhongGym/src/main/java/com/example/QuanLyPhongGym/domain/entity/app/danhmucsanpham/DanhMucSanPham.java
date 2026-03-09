package com.example.QuanLyPhongGym.domain.entity.app.danhmucsanpham;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "DANH_MUC_SAN_PHAM")
@Getter
@Setter

public class DanhMucSanPham {
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "TEN_DANH_MUC")
    private String tenDanhMuc;
}
