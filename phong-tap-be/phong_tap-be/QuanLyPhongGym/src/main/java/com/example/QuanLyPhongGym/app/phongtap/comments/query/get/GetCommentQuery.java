package com.example.QuanLyPhongGym.app.phongtap.comments.query.get;

import com.example.QuanLyPhongGym.core.annotation.Response;

import lombok.Getter;
import lombok.Setter;

@Response(target = GetCommentQueryDTO.class)
@Getter
@Setter

public class GetCommentQuery {
    private String id;
}
