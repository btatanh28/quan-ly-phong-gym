package com.example.QuanLyPhongGym.app.phongtap.comments.query.getlist;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class GetListCommentQueryDTO {
    private String id;
    private String idNguoiDung;
    private String idKhachHang;
    private String idDanhGia;
    private String hinhAnh;
    private String noiDung;
    private Long ngayBinhLuan;
    private String tenKhachHang;
    private String tenNguoiDung;
    private Integer page;
    private Integer size;
}
