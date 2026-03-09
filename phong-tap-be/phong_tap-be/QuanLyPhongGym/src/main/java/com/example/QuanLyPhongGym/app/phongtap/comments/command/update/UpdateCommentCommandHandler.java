package com.example.QuanLyPhongGym.app.phongtap.comments.command.update;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.example.QuanLyPhongGym.core.model.response.DataResponse;
import com.example.QuanLyPhongGym.domain.entity.app.comments.Comments;
import com.example.QuanLyPhongGym.domain.repository.app.comments.CommentsRespository;

import lombok.RequiredArgsConstructor;

@Component
@Lazy
@RequiredArgsConstructor

public class UpdateCommentCommandHandler {
    private final CommentsRespository respository;

    public DataResponse handle(UpdateCommentCommand request) {
        Comments comments = respository.findFirstById(request.getId());

        if (comments == null) {
            throw new RuntimeException("BaiViet not found");
        }
        var now = System.currentTimeMillis();
        comments.setNoiDung(request.getNoiDung());
        comments.setHinhAnh(request.getHinhAnh());
        comments.setNgayBinhLuan(now);

        if (request.getHinhAnh() != null && request.getHinhAnh().startsWith("data:image")) {
            try {
                String base64 = request.getHinhAnh().split(",")[1]; // bỏ phần data:image/png;base64,
                byte[] bytes = Base64.getDecoder().decode(base64);

                // Lưu file vào thư mục uploads/user
                Path path = Paths.get("uploads/comments/" + comments.getId() + ".png");
                Files.createDirectories(path.getParent());
                Files.write(path, bytes);

                // Lưu đường dẫn vào DB
                comments.setHinhAnh("/uploads/comments/" + comments.getId() + ".png");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        respository.save(comments);

        return new DataResponse(comments.getId());
    }
}
