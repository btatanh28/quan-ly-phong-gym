package com.example.QuanLyPhongGym.app.phongtap.danhgia.command.create;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.example.QuanLyPhongGym.core.model.response.DataResponse;
import com.example.QuanLyPhongGym.domain.entity.app.danhgia.DanhGia;
import com.example.QuanLyPhongGym.domain.repository.app.danhgia.DanhGiaRespository;

import lombok.RequiredArgsConstructor;

@Component
@Lazy
@RequiredArgsConstructor

public class CreateDanhGiaCommandHandler {
    private final DanhGiaRespository respository;

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

        if (request.getHinhAnh() != null && request.getHinhAnh().startsWith("data:image")) {
            try {
                String base64 = request.getHinhAnh().split(",")[1]; // bỏ phần data:image/png;base64,
                byte[] bytes = Base64.getDecoder().decode(base64);

                // Lưu file vào thư mục uploads/user
                Path path = Paths.get("uploads/bai-viet/" + danhGia.getId() + ".png");
                Files.createDirectories(path.getParent());
                Files.write(path, bytes);

                // Lưu đường dẫn vào DB
                danhGia.setHinhAnh("/uploads/bai-viet/" + danhGia.getId() + ".png");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        respository.save(danhGia);

        return new DataResponse(danhGia.getId());
    }
}
