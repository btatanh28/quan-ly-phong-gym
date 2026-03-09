package com.example.QuanLyPhongGym.app.phongtap.user.command.update;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.example.QuanLyPhongGym.core.exception.NotFoundException;
import com.example.QuanLyPhongGym.core.model.response.DataResponse;
import com.example.QuanLyPhongGym.domain.entity.app.user.User;
import com.example.QuanLyPhongGym.domain.repository.app.user.UserRespository;

import lombok.RequiredArgsConstructor;

@Component
@Lazy
@RequiredArgsConstructor

public class UpdateUserCommandHandler {
    private final UserRespository respository;

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

        if (request.getHinhAnh() != null && request.getHinhAnh().startsWith("data:image")) {
            try {
                String base64 = request.getHinhAnh().split(",")[1]; // bỏ phần data:image/png;base64,
                byte[] bytes = Base64.getDecoder().decode(base64);

                // Lưu file vào thư mục uploads/
                Path path = Paths.get("uploads/" + user.getId() + ".png");
                Files.createDirectories(path.getParent());
                Files.write(path, bytes);

                // Lưu đường dẫn vào DB
                user.setHinhAnh("/uploads/" + user.getId() + ".png");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        respository.save(user);

        return new DataResponse(user.getId());
    }
}
