package com.example.QuanLyPhongGym.app.phongtap.sanpham.query.get;

import com.example.QuanLyPhongGym.core.annotation.Response;

import lombok.Getter;
import lombok.Setter;

@Response(target = GetSanPhamQueryDTO.class)
@Getter
@Setter

public class GetSanPhamQuery {
    private String id;
}
