package com.example.QuanLyPhongGym.app.phongtap.lienhe.command.delete;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.example.QuanLyPhongGym.core.exception.NotFoundException;
import com.example.QuanLyPhongGym.core.model.response.DataResponse;
import com.example.QuanLyPhongGym.domain.entity.app.lienhe.LienHe;
import com.example.QuanLyPhongGym.domain.repository.app.lienhe.LienHeRepository;

import lombok.RequiredArgsConstructor;

@Component
@Lazy
@RequiredArgsConstructor

public class DeleteLienHeCommandHandler {
    private final LienHeRepository repository;

    public DataResponse handle(DeleteLienHeCommand request) {
        LienHe lienHe = repository.findFirstById(request.getId());

        if (lienHe == null) {
            throw new NotFoundException("Không tìm thấy dữ liệu");
        }

        repository.delete(lienHe);

        return new DataResponse(lienHe.getId());
    }
}
