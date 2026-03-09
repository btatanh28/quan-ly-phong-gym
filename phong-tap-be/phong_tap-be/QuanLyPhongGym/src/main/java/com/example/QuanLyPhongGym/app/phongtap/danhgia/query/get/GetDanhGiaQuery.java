package com.example.QuanLyPhongGym.app.phongtap.danhgia.query.get;

import com.example.QuanLyPhongGym.core.annotation.Response;

import lombok.Getter;
import lombok.Setter;

@Response(target = GetDanhGiaQueryDTO.class)
@Getter
@Setter

public class GetDanhGiaQuery {
    private String id;
}
