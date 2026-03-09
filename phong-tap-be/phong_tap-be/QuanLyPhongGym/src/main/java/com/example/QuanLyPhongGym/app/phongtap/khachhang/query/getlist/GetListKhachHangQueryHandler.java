package com.example.QuanLyPhongGym.app.phongtap.khachhang.query.getlist;

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

public class GetListKhachHangQueryHandler implements IRequestHandler<GetListKhachHangQuery, ListResponse> {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public ListResponse handle(GetListKhachHangQuery request) {
        StringBuilder sql = new StringBuilder(
                "SELECT ID, TEN_KHACH_HANG, DIA_CHI, EMAIL, SO_DIEN_THOAI " +
                        "FROM KHACH_HANG WHERE 1=1");

        List<Object> params = new ArrayList<>();

        if (request.getTenKhachHang() != null && !request.getTenKhachHang().isEmpty()) {
            sql.append(" AND TEN_KHACH_HANG LIKE ?");
            params.add("%" + request.getTenKhachHang() + "%");
        }

        if (request.getEmail() != null && !request.getEmail().isEmpty()) {
            sql.append(" AND EMAIL LIKE ?");
            params.add("%" + request.getEmail() + "%");
        }

        if (request.getSoDienThoai() != null && !request.getSoDienThoai().isEmpty()) {
            sql.append(" AND SO_DIEN_THOAI LIKE ?");
            params.add("%" + request.getSoDienThoai() + "%");
        }

        if (request.getDiaChi() != null && !request.getDiaChi().isEmpty()) {
            sql.append(" AND DIA_CHI LIKE ?");
            params.add("%" + request.getDiaChi() + "%");
        }

        List<GetListKhachHangQueryDTO> items = jdbcTemplate.query(
                sql.toString(),
                params.toArray(),
                (rs, rowNum) -> {
                    GetListKhachHangQueryDTO dto = new GetListKhachHangQueryDTO();
                    dto.setId(rs.getString("ID"));
                    dto.setTenKhachHang(rs.getString("TEN_KHACH_HANG"));
                    dto.setDiaChi(rs.getString("DIA_CHI"));
                    dto.setEmail(rs.getString("EMAIL"));
                    dto.setSoDienThoai(rs.getString("SO_DIEN_THOAI"));
                    return dto;
                });

        // Bao bọc thành ListResponse
        ListResponse response = new ListResponse();
        response.setItems(items);

        return response;
    }
}
