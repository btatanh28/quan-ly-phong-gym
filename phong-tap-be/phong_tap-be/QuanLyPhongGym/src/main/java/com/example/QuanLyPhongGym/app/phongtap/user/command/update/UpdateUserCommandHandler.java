package com.example.QuanLyPhongGym.app.phongtap.user.command.update;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.example.QuanLyPhongGym.core.exception.NotFoundException;
import com.example.QuanLyPhongGym.core.model.response.DataResponse;
import com.example.QuanLyPhongGym.core.service.Cloudinary.CloudinaryStorageService;
import com.example.QuanLyPhongGym.domain.entity.app.user.User;
import com.example.QuanLyPhongGym.domain.repository.app.user.UserRespository;

import lombok.RequiredArgsConstructor;

@Component
@Lazy
@RequiredArgsConstructor

public class UpdateUserCommandHandler {
    private final UserRespository respository;

    private final CloudinaryStorageService cloudinaryStorageService;

    public DataResponse handle(UpdateUserCommand request) {
        User user = respository.findFirstById(request.getId());

        if (user.getId() == null) {
            throw new NotFoundException("Không tìm thấy dữ liệu");
        }

        user.setTenNguoiDung(request.getTenNguoiDung());
        user.setEmail(request.getEmail());
        user.setSoDienThoai(request.getSoDienThoai());
        user.setDiaChi(request.getDiaChi());
        user.setCccd(request.getCccd());
        user.setLuong(request.getLuong());
        user.setNgayVaoLam(request.getNgayVaoLam());
        user.setHinhAnh(request.getHinhAnh());
        user.setVaiTro(request.getVaiTro());

        String hinhAnh = request.getHinhAnh();

        if (hinhAnh != null && !hinhAnh.isBlank()) {
            try {
                if (hinhAnh.startsWith("data:image")) {
                    hinhAnh = cloudinaryStorageService.uploadBase64Image(
                            hinhAnh,
                            "quan-ly-phong-gym/nguoi-dung");
                }

                user.setHinhAnh(hinhAnh);

            } catch (Exception e) {
                throw new RuntimeException(
                        "Không thể upload ảnh người dùng lên Cloudinary", e);
            }
        }

        respository.save(user);

        return new DataResponse(user.getId());
    }
}
