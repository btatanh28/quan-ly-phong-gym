package com.example.QuanLyPhongGym.app.phongtap.huanluyenvien.query.get;

import com.example.QuanLyPhongGym.core.annotation.Response;

import lombok.Getter;
import lombok.Setter;

@Response(target = GetHuanLuyenVienQueryDTO.class)
@Getter
@Setter

public class GetHuanLuyenVienQuery {
    private String id;
}
