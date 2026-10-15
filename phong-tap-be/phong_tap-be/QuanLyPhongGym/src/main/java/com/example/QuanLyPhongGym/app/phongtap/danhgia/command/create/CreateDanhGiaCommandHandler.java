package com.example.QuanLyPhongGym.app.phongtap.danhgia.command.create;

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

public class CreateDanhGiaCommandHandler {
    private final DanhGiaRespository respository;
    private final CloudinaryStorageService cloudinaryStorageService;

    public DataResponse handle(CreateDanhGiaCommand request) {
        var now = System.currentTimeMillis();

        DanhGia danhGia = new DanhGia();
        danhGia.setId(request.getId());
        danhGia.setIdKhachHang(request.getIdNguoiDung());
        danhGia.setHinhAnh(request.getHinhAnh());
        danhGia.setNgayDanhGia(now);
        danhGia.setIdGoiTap(request.getIdGoiTap());
        danhGia.setDanhGia(request.getDanhGia());
        danhGia.setBinhLuan(request.getBinhLuan());

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
