package com.example.QuanLyPhongGym.app.phongtap.baiviet.query.getlist;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class GetListBaiVietQueryDTO {
    private String id;
    private String idNguoiDung;
    private String tenNguoiDung;
    private Long ngayDangBai;
    private String tenBaiViet;
    private Long ngayCapNhat;
    private String hinhAnh;
}
