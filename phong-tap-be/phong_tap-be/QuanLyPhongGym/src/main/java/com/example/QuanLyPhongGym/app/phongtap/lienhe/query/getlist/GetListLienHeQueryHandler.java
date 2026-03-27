package com.example.QuanLyPhongGym.app.phongtap.lienhe.query.getlist;

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

public class GetListLienHeQueryHandler implements IRequestHandler<GetListLienHeQuery, ListResponse> {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public ListResponse handle(GetListLienHeQuery request) {
        StringBuilder sql = new StringBuilder("""
                    SELECT
                        lh.ID,
                        lh.ID_KHACH_HANG,
                        lh.TEN_KHACH_HANG,
                        kh.TEN_KHACH_HANG as khachHang,
                        lh.SO_DIEN_THOAI,
                        lh.EMAIL,
                        lh.NOI_DUNG,
                        lh.NGAY_GUI
                    FROM LIEN_HE lh
                    LEFT JOIN KHACH_HANG kh ON lh.ID_KHACH_HANG = kh.ID
                    WHERE 1=1
                """);

        List<Object> params = new ArrayList<>();

        if (request.getTenKhachHang() != null && !request.getTenKhachHang().isEmpty()) {
            sql.append(" AND lh.TEN_KHACH_HANG LIKE ?");
            params.add("%" + request.getTenKhachHang() + "%");
        }

        if (request.getEmail() != null && !request.getEmail().isEmpty()) {
            sql.append(" AND lh.EMAIL LIKE ?");
            params.add("%" + request.getEmail() + "%");
        }

        if (request.getSoDienThoai() != null && !request.getSoDienThoai().isEmpty()) {
            sql.append(" AND lh.SO_DIEN_THOAI LIKE ?");
            params.add("%" + request.getSoDienThoai() + "%");
        }

        sql.append(" ORDER BY lh.NGAY_GUI DESC");

        List<GetListLienHeQueryDTO> items = jdbcTemplate.query(
                sql.toString(),
                params.toArray(),
                (rs, rowNum) -> {
                    GetListLienHeQueryDTO dto = new GetListLienHeQueryDTO();
                    dto.setId(rs.getString("ID"));
                    dto.setTenKhachHang(rs.getString("TEN_KHACH_HANG"));
                    dto.setNoiDung(rs.getString("NOI_DUNG"));
                    dto.setEmail(rs.getString("EMAIL"));
                    dto.setSoDienThoai(rs.getString("SO_DIEN_THOAI"));
                    dto.setNgayGui(rs.getLong("NGAY_GUI"));
                    dto.setIdKhachHang(rs.getString("ID_KHACH_HANG"));
                    return dto;
                });

        // Bao bọc thành ListResponse
        ListResponse response = new ListResponse();
        response.setItems(items);

        return response;
    }
}
