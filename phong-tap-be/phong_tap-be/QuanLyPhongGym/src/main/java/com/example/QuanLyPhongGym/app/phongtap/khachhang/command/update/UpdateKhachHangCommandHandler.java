package com.example.QuanLyPhongGym.app.phongtap.khachhang.command.update;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.example.QuanLyPhongGym.core.exception.NotFoundException;
import com.example.QuanLyPhongGym.core.model.response.DataResponse;
import com.example.QuanLyPhongGym.domain.entity.app.khachhang.KhachHang;
import com.example.QuanLyPhongGym.domain.repository.app.khachhang.KhachHangRespository;
import lombok.RequiredArgsConstructor;

@Component
@Lazy
@RequiredArgsConstructor

public class UpdateKhachHangCommandHandler {
    private final KhachHangRespository respository;

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

        if (request.getHinhAnh() != null && request.getHinhAnh().startsWith("data:image")) {
            try {
                String base64 = request.getHinhAnh().split(",")[1]; // bỏ phần data:image/png;base64,
                byte[] bytes = Base64.getDecoder().decode(base64);

                // Lưu file vào thư mục uploads/
                Path path = Paths.get("uploads/" + khachHang.getId() + ".png");
                Files.createDirectories(path.getParent());
                Files.write(path, bytes);

                // Lưu đường dẫn vào DB
                khachHang.setHinhAnh("/uploads/" + khachHang.getId() + ".png");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        respository.save(khachHang);

        return new DataResponse(khachHang.getId());
    }
}
