package com.example.QuanLyPhongGym.app.phongtap.user.query.getlist;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.example.QuanLyPhongGym.core.mediator.IRequestHandler;
import com.example.QuanLyPhongGym.core.model.response.ListResponse;

import lombok.RequiredArgsConstructor;

@Component
@Lazy
@RequiredArgsConstructor

public class GetListUserQueryHandler implements IRequestHandler<GetListUserQuery, ListResponse> {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public ListResponse handle(GetListUserQuery request) {
        StringBuilder sql = new StringBuilder(
                "SELECT ID, TEN_NGUOI_DUNG, DIA_CHI, EMAIL, SO_DIEN_THOAI, NGAY_VAO_LAM, " +
                        "VAI_TRO, LUONG, CCCD FROM NGUOI_DUNG WHERE 1=1");

        List<Object> params = new ArrayList<>();

        if (request.getTenNguoiDung() != null && !request.getTenNguoiDung().isEmpty()) {
            sql.append(" AND TEN_NGUOI_DUNG LIKE ?");
            params.add("%" + request.getTenNguoiDung() + "%");
        }

        if (request.getEmail() != null && !request.getEmail().isEmpty()) {
            sql.append(" AND EMAIL LIKE ?");
            params.add("%" + request.getEmail() + "%");
        }

        if (request.getVaiTro() != null) {
            sql.append(" AND VAI_TRO = ?");
            params.add(request.getVaiTro());
        }

        if (request.getSoDienThoai() != null && !request.getSoDienThoai().isEmpty()) {
            sql.append(" AND SO_DIEN_THOAI LIKE ?");
            params.add("%" + request.getSoDienThoai() + "%");
        }

        if (request.getDiaChi() != null && !request.getDiaChi().isEmpty()) {
            sql.append(" AND DIA_CHI LIKE ?");
            params.add("%" + request.getDiaChi() + "%");
        }

        if (request.getCccd() != null && !request.getCccd().isEmpty()) {
            sql.append(" AND CCCD LIKE ?");
            params.add("%" + request.getCccd() + "%");
        }

        List<GetListUserQueryDTO> items = jdbcTemplate.query(
                sql.toString(),
                params.toArray(),
                (rs, rowNum) -> {
                    GetListUserQueryDTO dto = new GetListUserQueryDTO();
                    dto.setId(rs.getString("ID"));
                    dto.setTenNguoiDung(rs.getString("TEN_NGUOI_DUNG"));
                    dto.setDiaChi(rs.getString("DIA_CHI"));
                    dto.setEmail(rs.getString("EMAIL"));
                    dto.setSoDienThoai(rs.getString("SO_DIEN_THOAI"));
                    dto.setNgayVaoLam(rs.getLong("NGAY_VAO_LAM"));
                    dto.setLuong(rs.getInt("LUONG"));
                    dto.setVaiTro(rs.getInt("VAI_TRO"));
                    dto.setCccd(rs.getString("CCCD"));
                    return dto;
                });

        // Bao bọc thành ListResponse
        ListResponse response = new ListResponse();
        response.setItems(items);

        return response;
    }
}
