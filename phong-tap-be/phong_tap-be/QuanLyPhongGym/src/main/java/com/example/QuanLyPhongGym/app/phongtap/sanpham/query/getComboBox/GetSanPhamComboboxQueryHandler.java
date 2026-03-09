package com.example.QuanLyPhongGym.app.phongtap.sanpham.query.getComboBox;

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

public class GetSanPhamComboboxQueryHandler
        implements IRequestHandler<GetSanPhamComboboxQuery, ListResponse> {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public ListResponse handle(GetSanPhamComboboxQuery request) {
        StringBuilder sql = new StringBuilder("""
                SELECT ID, TEN_GOI_TAP
                FROM GOI_TAP
                WHERE 1=1
                """);

        List<Object> params = new ArrayList<>();

        // search optional
        if (request.getQ() != null && !request.getQ().isEmpty()) {
            sql.append(" AND TEN_GOI_TAP LIKE ?");
            params.add("%" + request.getQ() + "%");
        }

        List<GetSanPhamComboboxQueryDTO> items = jdbcTemplate.query(
                sql.toString(),
                params.toArray(),
                (rs, rowNum) -> {
                    GetSanPhamComboboxQueryDTO dto = new GetSanPhamComboboxQueryDTO();
                    dto.setValue(rs.getString("ID"));
                    dto.setLabel(rs.getString("TEN_GOI_TAP"));
                    return dto;
                });
        ListResponse response = new ListResponse();
        response.setItems(items);
        return response;
    }
}
