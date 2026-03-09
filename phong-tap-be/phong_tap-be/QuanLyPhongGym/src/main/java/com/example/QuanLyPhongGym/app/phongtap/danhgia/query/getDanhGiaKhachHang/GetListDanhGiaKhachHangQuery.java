package com.example.QuanLyPhongGym.app.phongtap.danhgia.query.getDanhGiaKhachHang;

import com.example.QuanLyPhongGym.core.annotation.Response;
import com.example.QuanLyPhongGym.core.model.response.ListResponse;

import lombok.Getter;
import lombok.Setter;

@Response(target = ListResponse.class)
@Getter
@Setter

public class GetListDanhGiaKhachHangQuery {
    private String idKhachHang;
}
