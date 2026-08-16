package com.example.QuanLyPhongGym.app.phongtap.sanphamgym.query.getComboBox;

import com.example.QuanLyPhongGym.core.annotation.Response;
import com.example.QuanLyPhongGym.core.model.response.ListResponse;

import lombok.Getter;
import lombok.Setter;

@Response(target = ListResponse.class)
@Getter
@Setter

public class GetSanPhamGymComboboxQuery {
    private String q;
}
