package com.example.QuanLyPhongGym.app.phongtap.donhang.query.getkhachhang;

import com.example.QuanLyPhongGym.core.annotation.Response;
import com.example.QuanLyPhongGym.core.model.response.PageResponse;

import lombok.Getter;
import lombok.Setter;

@Response(target = PageResponse.class)
@Getter
@Setter

public class GetListDonHangKhachHangQuery {
    private String idKhachHang;
}
