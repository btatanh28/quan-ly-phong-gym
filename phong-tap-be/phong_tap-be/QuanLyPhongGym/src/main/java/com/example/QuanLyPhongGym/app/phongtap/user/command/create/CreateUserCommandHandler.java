package com.example.QuanLyPhongGym.app.phongtap.user.command.create;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.QuanLyPhongGym.core.exception.NotFoundException;
import com.example.QuanLyPhongGym.core.model.response.DataResponse;
import com.example.QuanLyPhongGym.domain.entity.app.user.User;
import com.example.QuanLyPhongGym.domain.repository.app.user.UserRespository;

import lombok.RequiredArgsConstructor;

@Component
@Lazy
@RequiredArgsConstructor

public class CreateUserCommandHandler {
    private final UserRespository respository;
    private final PasswordEncoder passwordEncoder;

    public DataResponse handle(CreateUserCommand request) {
        if (respository.existsByEmail(request.getEmail())) {
            throw new NotFoundException("Email này đã có");
        }

        if (respository.existsBySoDienThoai(request.getSoDienThoai())) {
            throw new NotFoundException("Số điện thoại này đã có");
        }

        User user = new User();
        user.setId(request.getId());
        user.setTenNguoiDung(request.getTenNguoiDung());
        user.setEmail(request.getEmail());
        user.setMatKhau(passwordEncoder.encode(request.getMatKhau()));
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

                // Lưu file vào thư mục uploads/user
                Path path = Paths.get("uploads/user/" + user.getId() + ".png");
                Files.createDirectories(path.getParent());
                Files.write(path, bytes);

                // Lưu đường dẫn vào DB
                user.setHinhAnh("/uploads/user/" + user.getId() + ".png");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        respository.save(user);

        return new DataResponse(user.getId());
    }
}
