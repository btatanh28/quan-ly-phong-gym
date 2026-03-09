package com.example.QuanLyPhongGym.app.phongtap.sanpham.command.delete;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.example.QuanLyPhongGym.core.exception.NotFoundException;
import com.example.QuanLyPhongGym.core.model.response.DataResponse;
import com.example.QuanLyPhongGym.domain.entity.app.goitap.GoiTap;
import com.example.QuanLyPhongGym.domain.repository.app.goitap.GoiTapRespository;

import lombok.RequiredArgsConstructor;

@Component
@Lazy
@RequiredArgsConstructor

public class DeleteSanPhamCommandHandler {
    private final GoiTapRespository respository;

    public DataResponse handle(DeleteSanPhamCommand request) {
        GoiTap sanPham = respository.findFirstById(request.getId());

        if (request.getId() == null) {
            throw new NotFoundException("Không có dữ liệu");
        }

        respository.delete(sanPham);

        return new DataResponse("Xóa sản phẩm không thành công");
    }
}
