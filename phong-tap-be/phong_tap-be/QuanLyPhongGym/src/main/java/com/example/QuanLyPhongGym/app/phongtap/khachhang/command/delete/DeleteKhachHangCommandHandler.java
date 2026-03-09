package com.example.QuanLyPhongGym.app.phongtap.khachhang.command.delete;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.example.QuanLyPhongGym.core.exception.NotFoundException;
import com.example.QuanLyPhongGym.core.mediator.IRequestHandler;
import com.example.QuanLyPhongGym.core.model.response.DataResponse;
import com.example.QuanLyPhongGym.domain.entity.app.khachhang.KhachHang;
import com.example.QuanLyPhongGym.domain.repository.app.khachhang.KhachHangRespository;

@Component
@Lazy
@RequiredArgsConstructor

public class DeleteKhachHangCommandHandler implements IRequestHandler<DeleteKhachHangCommand, DataResponse> {

  private final KhachHangRespository repository;

  @Override
  public DataResponse handle(DeleteKhachHangCommand request) {
    KhachHang khachHang = repository.findFirstById(request.getId());

    if (khachHang == null) {
      throw new NotFoundException("Không tìm thấy dữ liệu");
    }

    repository.delete(khachHang);

    return new DataResponse(khachHang.getId());
  }
}
