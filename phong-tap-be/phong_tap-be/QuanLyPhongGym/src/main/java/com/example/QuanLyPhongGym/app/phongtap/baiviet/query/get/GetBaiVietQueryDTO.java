package com.example.QuanLyPhongGym.app.phongtap.baiviet.query.get;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class GetBaiVietQueryDTO {
    private String id;
    private String idNguoiDung;
    private String noiDung;
    private String hinhAnh;
    private Long ngayDangBai;
    private String tenBaiViet;
}
