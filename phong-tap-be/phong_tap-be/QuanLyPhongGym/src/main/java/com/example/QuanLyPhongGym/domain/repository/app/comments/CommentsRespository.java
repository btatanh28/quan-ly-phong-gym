package com.example.QuanLyPhongGym.domain.repository.app.comments;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.QuanLyPhongGym.domain.entity.app.comments.Comments;

public interface CommentsRespository extends JpaRepository<Comments, String> {
    Comments findFirstById(String id);

    List<Comments> findAllByIdDanhGia(String idDanhGia);
}
