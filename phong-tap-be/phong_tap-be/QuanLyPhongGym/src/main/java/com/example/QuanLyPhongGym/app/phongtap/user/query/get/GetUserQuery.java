package com.example.QuanLyPhongGym.app.phongtap.user.query.get;

import com.example.QuanLyPhongGym.core.annotation.Response;

import lombok.Getter;
import lombok.Setter;

@Response(target = GetUserQueryDTO.class)
@Getter
@Setter

public class GetUserQuery {
    private String id;
}
