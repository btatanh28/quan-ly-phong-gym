package com.example.QuanLyPhongGym.app.phongtap.sanphamgym.query.getComboBox;

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

public class GetSanPhamGymComboboxQueryHandler
        implements IRequestHandler<GetSanPhamGymComboboxQuery, ListResponse> {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public ListResponse handle(GetSanPhamGymComboboxQuery request) {
        StringBuilder sql = new StringBuilder("""
                SELECT ID, TEN_SAN_PHAM
                FROM SAN_PHAM
                WHERE 1=1
                """);

        List<Object> params = new ArrayList<>();

        // search optional
        if (request.getQ() != null && !request.getQ().isEmpty()) {
            sql.append(" AND TEN_SAN_PHAM LIKE ?");
            params.add("%" + request.getQ() + "%");
        }

        List<GetSanPhamGymComboboxQueryDTO> items = jdbcTemplate.query(
                sql.toString(),
                params.toArray(),
                (rs, rowNum) -> {
                    GetSanPhamGymComboboxQueryDTO dto = new GetSanPhamGymComboboxQueryDTO();
                    dto.setValue(rs.getString("ID"));
                    dto.setLabel(rs.getString("TEN_SAN_PHAM"));
                    return dto;
                });
        ListResponse response = new ListResponse();
        response.setItems(items);
        return response;
    }
}
