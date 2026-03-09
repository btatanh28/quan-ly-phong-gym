package com.example.QuanLyPhongGym.app.phongtap.comments.command.delete;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.example.QuanLyPhongGym.core.exception.NotFoundException;
import com.example.QuanLyPhongGym.core.model.response.DataResponse;
import com.example.QuanLyPhongGym.domain.entity.app.comments.Comments;
import com.example.QuanLyPhongGym.domain.repository.app.comments.CommentsRespository;

import lombok.RequiredArgsConstructor;

@Component
@Lazy
@RequiredArgsConstructor

public class DeleteCommentCommandHandler {
    private final CommentsRespository respository;

    public DataResponse handle(DeleteCommentCommand request) {
        Comments comments = respository.findFirstById(request.getId());

        if (request.getId() == null) {
            throw new NotFoundException("Không có dữ liệu");
        }

        respository.delete(comments);

        return new DataResponse("Xóa bài viết không thành công");
    }
}
