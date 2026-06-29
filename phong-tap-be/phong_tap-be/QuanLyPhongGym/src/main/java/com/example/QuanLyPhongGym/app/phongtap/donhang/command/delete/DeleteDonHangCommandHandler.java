package com.example.QuanLyPhongGym.app.phongtap.donhang.command.delete;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.example.QuanLyPhongGym.core.exception.CustomException;
import com.example.QuanLyPhongGym.core.model.response.DataResponse;
import com.example.QuanLyPhongGym.domain.entity.app.dondangky.DonDangKy;
import com.example.QuanLyPhongGym.domain.repository.app.dondangky.DonDangKyRespository;
import lombok.RequiredArgsConstructor;

@Component
@Lazy
@RequiredArgsConstructor

public class DeleteDonHangCommandHandler {
    private final DonDangKyRespository repository;

    public DataResponse handle(DeleteDonHangCommand request) {
        DonDangKy donDangKy = repository.findFirstById(request.getId());

        if (donDangKy == null) {
            throw new CustomException("404", "Không tìm thấy dữ liệu");
        }

        repository.delete(donDangKy);

        return new DataResponse(donDangKy.getId());
    }
}
