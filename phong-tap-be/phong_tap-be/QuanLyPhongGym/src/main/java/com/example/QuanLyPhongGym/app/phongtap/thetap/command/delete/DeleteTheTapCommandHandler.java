package com.example.QuanLyPhongGym.app.phongtap.thetap.command.delete;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.example.QuanLyPhongGym.core.exception.CustomException;
import com.example.QuanLyPhongGym.core.model.response.DataResponse;
import com.example.QuanLyPhongGym.domain.entity.app.thetap.TheTap;
import com.example.QuanLyPhongGym.domain.repository.app.thetap.TheTapRespository;
import lombok.RequiredArgsConstructor;

@Component
@Lazy
@RequiredArgsConstructor

public class DeleteTheTapCommandHandler {
    private final TheTapRespository repository;

    public DataResponse handle(DeleteTheTapCommand request) {
        TheTap theTap = repository.findFirstById(request.getId());

        if (theTap == null) {
            throw new CustomException("404", "Không tìm thấy dữ liệu");
        }

        repository.delete(theTap);

        return new DataResponse(theTap.getId());
    }
}
