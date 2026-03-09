package com.example.QuanLyPhongGym.app.phongtap.baiviet.command.update;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.example.QuanLyPhongGym.core.model.response.DataResponse;
import com.example.QuanLyPhongGym.domain.entity.app.baiviet.BaiViet;
import com.example.QuanLyPhongGym.domain.repository.app.baiviet.BaiVietRespository;

import lombok.RequiredArgsConstructor;

@Component
@Lazy
@RequiredArgsConstructor

public class UpdateBaiVietCommandHandler {
    private final BaiVietRespository respository;

    public DataResponse handle(UpdateBaiVietCommand request) {
        BaiViet baiViet = respository.findFirstById(request.getId());
        if (baiViet == null) {
            throw new RuntimeException("BaiViet not found");
        }
        var now = System.currentTimeMillis();
        baiViet.setTenBaiViet(request.getTenBaiViet());
        baiViet.setNoiDung(request.getNoiDung());
        baiViet.setIdNguoiDung(request.getIdNguoiDung());
        baiViet.setHinhAnh(request.getHinhAnh());
        baiViet.setNgayCapNhat(now);

        if (request.getHinhAnh() != null && request.getHinhAnh().startsWith("data:image")) {
            try {
                String base64 = request.getHinhAnh().split(",")[1]; // bỏ phần data:image/png;base64,
                byte[] bytes = Base64.getDecoder().decode(base64);

                // Lưu file vào thư mục uploads/user
                Path path = Paths.get("uploads/bai-viet/" + baiViet.getId() + ".png");
                Files.createDirectories(path.getParent());
                Files.write(path, bytes);

                // Lưu đường dẫn vào DB
                baiViet.setHinhAnh("/uploads/bai-viet/" + baiViet.getId() + ".png");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        respository.save(baiViet);

        return new DataResponse(baiViet.getId());
    }
}
