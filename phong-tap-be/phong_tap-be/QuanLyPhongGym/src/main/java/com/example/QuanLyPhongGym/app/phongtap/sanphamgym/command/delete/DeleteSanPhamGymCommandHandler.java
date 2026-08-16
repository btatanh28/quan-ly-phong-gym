package com.example.QuanLyPhongGym.app.phongtap.sanphamgym.command.delete;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.example.QuanLyPhongGym.core.exception.NotFoundException;
import com.example.QuanLyPhongGym.core.model.response.DataResponse;
import com.example.QuanLyPhongGym.domain.entity.app.goitap.GoiTap;
import com.example.QuanLyPhongGym.domain.entity.app.sanpham.SanPham;
import com.example.QuanLyPhongGym.domain.repository.app.goitap.GoiTapRespository;
import com.example.QuanLyPhongGym.domain.repository.app.sanpham.SanPhamRepository;

import lombok.RequiredArgsConstructor;

@Component
@Lazy
@RequiredArgsConstructor

public class DeleteSanPhamGymCommandHandler {
    private final SanPhamRepository respository;

    public DataResponse handle(DeleteSanPhamGymCommand request) {
        SanPham sanPham = respository.findFirstById(request.getId());

        if (request.getId() == null) {
            throw new NotFoundException("Không có dữ liệu");
        }

        respository.delete(sanPham);

        return new DataResponse("Xóa sản phẩm không thành công");
    }
}
