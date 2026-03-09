package com.example.QuanLyPhongGym.app.phongtap.danhgia.command.update;

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

public class UpdateDanhGiaCommandHandler {
    private final DanhGiaRespository respository;

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

        if (request.getHinhAnh() != null && request.getHinhAnh().startsWith("data:image")) {
            try {
                String base64 = request.getHinhAnh().split(",")[1];
                byte[] bytes = Base64.getDecoder().decode(base64);

                Path path = Paths.get("uploads/bai-viet/" + danhGia.getId() + ".png");
                Files.createDirectories(path.getParent());
                Files.write(path, bytes);

                danhGia.setHinhAnh("/uploads/bai-viet/" + danhGia.getId() + ".png");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        respository.save(danhGia);

        return new DataResponse(danhGia.getId());
    }
}
