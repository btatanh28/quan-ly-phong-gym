package com.example.QuanLyPhongGym.app.phongtap.baiviet.command.delete;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.example.QuanLyPhongGym.core.exception.NotFoundException;
import com.example.QuanLyPhongGym.core.model.response.DataResponse;
import com.example.QuanLyPhongGym.domain.entity.app.baiviet.BaiViet;
import com.example.QuanLyPhongGym.domain.repository.app.baiviet.BaiVietRespository;

import lombok.RequiredArgsConstructor;

@Component
@Lazy
@RequiredArgsConstructor

public class DeleteBaiVietCommandHandler {
    private final BaiVietRespository respository;

    public DataResponse handle(DeleteBaiVietCommand request) {
        BaiViet baiViet = respository.findFirstById(request.getId());

        if (request.getId() == null) {
            throw new NotFoundException("Không có dữ liệu");
        }

        respository.delete(baiViet);

        return new DataResponse("Xóa bài viết không thành công");
    }
}
