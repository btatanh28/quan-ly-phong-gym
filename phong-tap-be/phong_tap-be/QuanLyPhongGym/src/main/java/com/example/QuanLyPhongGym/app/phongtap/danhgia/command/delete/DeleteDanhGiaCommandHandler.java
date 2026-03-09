package com.example.QuanLyPhongGym.app.phongtap.danhgia.command.delete;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.example.QuanLyPhongGym.core.exception.NotFoundException;
import com.example.QuanLyPhongGym.core.model.response.DataResponse;
import com.example.QuanLyPhongGym.domain.entity.app.danhgia.DanhGia;
import com.example.QuanLyPhongGym.domain.repository.app.danhgia.DanhGiaRespository;

import lombok.RequiredArgsConstructor;

@Component
@Lazy
@RequiredArgsConstructor

public class DeleteDanhGiaCommandHandler {
    private final DanhGiaRespository respository;

    public DataResponse handle(DeleteDanhGiaCommand request) {
        DanhGia danhGia = respository.findFirstById(request.getId());

        if (request.getId() == null) {
            throw new NotFoundException("Không có dữ liệu");
        }

        respository.delete(danhGia);

        return new DataResponse("Xóa bài viết không thành công");
    }
}
