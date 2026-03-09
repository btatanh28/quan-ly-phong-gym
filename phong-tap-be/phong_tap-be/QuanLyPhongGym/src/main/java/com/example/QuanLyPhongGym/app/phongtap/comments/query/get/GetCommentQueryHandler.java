package com.example.QuanLyPhongGym.app.phongtap.comments.query.get;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.example.QuanLyPhongGym.core.exception.NotFoundException;
import com.example.QuanLyPhongGym.domain.entity.app.comments.Comments;
import com.example.QuanLyPhongGym.domain.repository.app.comments.CommentsRespository;

import lombok.RequiredArgsConstructor;

@Component
@Lazy
@RequiredArgsConstructor

public class GetCommentQueryHandler {
    private final CommentsRespository respository;

    public GetCommentQueryDTO handle(GetCommentQuery request) {
        Comments comments = respository.findFirstById(request.getId());

        if (comments.getId() == null) {
            throw new NotFoundException("Không tìm thấy sản phẩm");
        }

        GetCommentQueryDTO dto = new GetCommentQueryDTO();

        dto.setId(comments.getId());
        dto.setNoiDung(comments.getNoiDung());

        return dto;
    }
}
