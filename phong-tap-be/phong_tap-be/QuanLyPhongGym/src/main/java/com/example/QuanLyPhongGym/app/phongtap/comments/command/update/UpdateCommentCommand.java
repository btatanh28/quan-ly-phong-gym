package com.example.QuanLyPhongGym.app.phongtap.comments.command.update;

import com.example.QuanLyPhongGym.core.annotation.Response;
import com.example.QuanLyPhongGym.core.model.response.DataResponse;

import lombok.Getter;
import lombok.Setter;

@Response(target = DataResponse.class)
@Getter
@Setter

public class UpdateCommentCommand {
    private String id;
    private String hinhAnh;
    private String noiDung;
    private Long ngayBinhLuan;
}
