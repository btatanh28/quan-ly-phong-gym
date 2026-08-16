package com.example.QuanLyPhongGym.app.phongtap.sanphamgym.query.get;

import com.example.QuanLyPhongGym.core.annotation.Response;

import lombok.Getter;
import lombok.Setter;

@Response(target = GetSanPhamGymQueryDTO.class)
@Getter
@Setter

public class GetSanPhamGymQuery {
    private String id;
}
