package com.example.QuanLyPhongGym.app.phongtap.user.command.delete;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.example.QuanLyPhongGym.core.exception.NotFoundException;
import com.example.QuanLyPhongGym.core.mediator.IRequestHandler;
import com.example.QuanLyPhongGym.core.model.response.DataResponse;
import com.example.QuanLyPhongGym.domain.entity.app.user.User;
import com.example.QuanLyPhongGym.domain.repository.app.user.UserRespository;



@Component
@Lazy
@RequiredArgsConstructor

public class DeleteUserCommandHandler implements IRequestHandler<DeleteUserCommand, DataResponse> {

  private final UserRespository repository;

  @Override
  public DataResponse handle(DeleteUserCommand request) {
    User user = repository.findFirstById(request.getId());

    if (user == null) {
      throw new NotFoundException("Không tìm thấy dữ liệu");
    }

    repository.delete(user);

    return new DataResponse(user.getId());
  }
}
