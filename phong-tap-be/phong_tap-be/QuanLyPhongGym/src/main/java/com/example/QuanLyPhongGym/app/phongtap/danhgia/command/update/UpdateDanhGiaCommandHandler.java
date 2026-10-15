package com.example.QuanLyPhongGym.app.phongtap.danhgia.command.update;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.example.QuanLyPhongGym.core.model.response.DataResponse;
import com.example.QuanLyPhongGym.core.service.Cloudinary.CloudinaryStorageService;
import com.example.QuanLyPhongGym.domain.entity.app.danhgia.DanhGia;
import com.example.QuanLyPhongGym.domain.repository.app.danhgia.DanhGiaRespository;

import lombok.RequiredArgsConstructor;

@Component
@Lazy
@RequiredArgsConstructor

public class UpdateDanhGiaCommandHandler {
    private final DanhGiaRespository respository;

    private final CloudinaryStorageService cloudinaryStorageService;

    public DataResponse handle(UpdateDanhGiaCommand request) {
        DanhGia danhGia = respository.findFirstById(request.getId());
        if (danhGia == null) {
            throw new RuntimeException("danhGia not found");
        }
        // var now = System.currentTimeMillis();

        danhGia.setBinhLuan(request.getBinhLuan());
        danhGia.setDanhGia(request.getDanhGia());
        danhGia.setHinhAnh(request.getHinhAnh());
        danhGia.setIdGoiTap(request.getIdGoiTap());

        String hinhAnh = request.getHinhAnh();

        if (hinhAnh != null && !hinhAnh.isBlank()) {
            try {
                if (hinhAnh.startsWith("data:image")) {
                    hinhAnh = cloudinaryStorageService.uploadBase64Image(
                            hinhAnh,
                            "quan-ly-phong-gym/danh-gia");
                }

                danhGia.setHinhAnh(hinhAnh);

            } catch (Exception e) {
                throw new RuntimeException(
                        "Không thể upload ảnh đánh giá lên Cloudinary", e);
            }
        }

        respository.save(danhGia);

        return new DataResponse(danhGia.getId());
    }
}
