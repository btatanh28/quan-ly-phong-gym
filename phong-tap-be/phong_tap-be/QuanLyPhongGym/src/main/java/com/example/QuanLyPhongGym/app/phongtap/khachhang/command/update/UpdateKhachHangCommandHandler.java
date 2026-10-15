package com.example.QuanLyPhongGym.app.phongtap.khachhang.command.update;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.example.QuanLyPhongGym.core.exception.NotFoundException;
import com.example.QuanLyPhongGym.core.model.response.DataResponse;
import com.example.QuanLyPhongGym.core.service.Cloudinary.CloudinaryStorageService;
import com.example.QuanLyPhongGym.domain.entity.app.khachhang.KhachHang;
import com.example.QuanLyPhongGym.domain.repository.app.khachhang.KhachHangRespository;
import lombok.RequiredArgsConstructor;

@Component
@Lazy
@RequiredArgsConstructor

public class UpdateKhachHangCommandHandler {
    private final KhachHangRespository respository;
    private final CloudinaryStorageService cloudinaryStorageService;

    public DataResponse handle(UpdateKhachHangCommand request) {
        KhachHang khachHang = respository.findFirstById(request.getId());

        if (khachHang.getId() == null) {
            throw new NotFoundException("Không tìm thấy dữ liệu");
        }

        khachHang.setTenKhachHang(request.getTenKhachHang());
        khachHang.setEmail(request.getEmail());
        khachHang.setSoDienThoai(request.getSoDienThoai());
        khachHang.setDiaChi(request.getDiaChi());
        khachHang.setHinhAnh(request.getHinhAnh());

        String hinhAnh = request.getHinhAnh();

        if (hinhAnh != null && !hinhAnh.isBlank()) {
            try {
                if (hinhAnh.startsWith("data:image")) {
                    hinhAnh = cloudinaryStorageService.uploadBase64Image(
                            hinhAnh,
                            "quan-ly-phong-gym/khach-hang");
                }

                khachHang.setHinhAnh(hinhAnh);

            } catch (Exception e) {
                throw new RuntimeException(
                        "Không thể upload ảnh khách hàng lên Cloudinary", e);
            }
        }

        respository.save(khachHang);

        return new DataResponse(khachHang.getId());
    }
}
