package com.example.QuanLyPhongGym.app.phongtap.baiviet.query.get;

import com.example.QuanLyPhongGym.core.annotation.Response;

import lombok.Getter;
import lombok.Setter;

@Response(target = GetBaiVietQueryDTO.class)
@Getter
@Setter

public class GetBaiVietQuery {
    private String id;
}
