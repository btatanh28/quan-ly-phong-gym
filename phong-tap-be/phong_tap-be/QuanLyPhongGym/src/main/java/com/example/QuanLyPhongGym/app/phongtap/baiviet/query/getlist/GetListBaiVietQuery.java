package com.example.QuanLyPhongGym.app.phongtap.baiviet.query.getlist;

import com.example.QuanLyPhongGym.core.annotation.Response;
import com.example.QuanLyPhongGym.core.model.response.ListResponse;

import lombok.Getter;
import lombok.Setter;

@Response(target = ListResponse.class)
@Getter
@Setter

public class GetListBaiVietQuery {
    private String id;
    private String idNguoiDung;
    private String tenNguoiDung;
    private Long ngayDangBai;
    private String tenBaiViet;
    private Long ngayCapNhat;
    private String hinhAnh;
}
