package com.example.QuanLyPhongGym.app.phongtap.chitietdonhang.query.get;

import com.example.QuanLyPhongGym.core.annotation.Response;

import lombok.Getter;
import lombok.Setter;

@Response(target = GetChiTietDonHangQueryDTO.class)
@Getter
@Setter

public class GetChiTietDonHangQuery {
    private String id;
}
