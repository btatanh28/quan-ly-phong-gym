package com.example.QuanLyPhongGym.app.phongtap.huanluyenvien.command.delete;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.example.QuanLyPhongGym.core.exception.CustomException;
import com.example.QuanLyPhongGym.core.model.response.DataResponse;
import com.example.QuanLyPhongGym.domain.entity.app.huanluyenvien.HuanLuyenVien;
import com.example.QuanLyPhongGym.domain.repository.app.huanluyenvien.HuanLuyenVienRespository;

import lombok.RequiredArgsConstructor;

@Component
@Lazy
@RequiredArgsConstructor

public class DeleteHuanLuyenVienCommandHandler {
    private final HuanLuyenVienRespository repository;

    public DataResponse handle(DeleteHuanLuyenVienCommand request) {
        HuanLuyenVien huanLuyenVien = repository.findFirstById(request.getId());

        if (huanLuyenVien == null) {
            throw new CustomException("404", "Không tìm thấy dữ liệu");
        }

        repository.delete(huanLuyenVien);

        return new DataResponse(request.getId());
    }
}
