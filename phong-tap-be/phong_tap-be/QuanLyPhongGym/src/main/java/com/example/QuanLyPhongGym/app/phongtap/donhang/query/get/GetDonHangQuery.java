package com.example.QuanLyPhongGym.app.phongtap.donhang.query.get;

import com.example.QuanLyPhongGym.core.annotation.Response;
import com.example.QuanLyPhongGym.core.model.response.DataResponse;

import lombok.Getter;
import lombok.Setter;

@Response(target = DataResponse.class)
@Getter
@Setter

public class GetDonHangQuery {
    private String id;
}
