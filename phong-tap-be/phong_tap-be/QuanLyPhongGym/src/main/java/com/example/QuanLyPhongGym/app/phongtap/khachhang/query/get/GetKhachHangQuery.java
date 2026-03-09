package com.example.QuanLyPhongGym.app.phongtap.khachhang.query.get;

import com.example.QuanLyPhongGym.core.annotation.Response;

import lombok.Getter;
import lombok.Setter;

@Response(target = GetKhachHangQueryDTO.class)
@Getter
@Setter

public class GetKhachHangQuery {
    private String id;
}
