package com.example.QuanLyPhongGym.app.phongtap.user.query.get;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.example.QuanLyPhongGym.core.exception.NotFoundException;
import com.example.QuanLyPhongGym.core.mediator.IRequestHandler;
import com.example.QuanLyPhongGym.domain.entity.app.user.User;
import com.example.QuanLyPhongGym.domain.repository.app.user.UserRespository;

import lombok.RequiredArgsConstructor;

@Component
@Lazy
@RequiredArgsConstructor

public class GetUserQueryHandler implements IRequestHandler<GetUserQuery, GetUserQueryDTO> {
    private final UserRespository respository;

    public GetUserQueryDTO handle(GetUserQuery request) {
        User user = respository.findFirstById(request.getId());

        if (user.getId() == null) {
            throw new NotFoundException("Không tìm thấy dữ liệu");
        }

        GetUserQueryDTO dto = new GetUserQueryDTO();

        dto.setId(user.getId());
        dto.setTenNguoiDung(user.getTenNguoiDung());
        dto.setEmail(user.getEmail());
        dto.setSoDienThoai(user.getSoDienThoai());
        dto.setDiaChi(user.getDiaChi());
        dto.setCccd(user.getCccd());
        dto.setLuong(user.getLuong());
        dto.setNgayVaoLam(user.getNgayVaoLam());
        dto.setHinhAnh(user.getHinhAnh());
        dto.setVaiTro(user.getVaiTro());

        return dto;
    }
}
