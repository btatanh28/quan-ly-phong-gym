package com.example.QuanLyPhongGym.app.phongtap.baiviet.query.getlist;

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

public class GetListBaiVietQueryHandler implements IRequestHandler<GetListBaiVietQuery, ListResponse> {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public ListResponse handle(GetListBaiVietQuery request) {
        StringBuilder sql = new StringBuilder(
                "SELECT " +
                        "    bv.ID, " +
                        "    bv.ID_NGUOI_DUNG, " +
                        "    bv.TEN_BAI_VIET, " +
                        "    bv.NGAY_DANG_BAI, " +
                        "    bv.NGAY_CAP_NHAT, " +
                        "    bv.HINH_ANH, " +
                        "    nd.TEN_NGUOI_DUNG " +
                        "FROM BAI_VIET bv " +
                        "JOIN NGUOI_DUNG nd " +
                        "    ON bv.ID_NGUOI_DUNG = nd.ID " +
                        "WHERE 1=1 ");

        List<Object> params = new ArrayList<>();

        if (request.getTenBaiViet() != null && !request.getTenBaiViet().isEmpty()) {
            sql.append(" AND bv.TEN_BAI_VIET LIKE ?");
            params.add("%" + request.getTenBaiViet() + "%");
        }

        if (request.getTenNguoiDung() != null && !request.getTenNguoiDung().isEmpty()) {
            sql.append(" AND nd.TEN_NGUOI_DUNG LIKE ?");
            params.add("%" + request.getTenNguoiDung() + "%");
        }

        sql.append(" ORDER BY bv.NGAY_DANG_BAI DESC");

        List<GetListBaiVietQueryDTO> items = jdbcTemplate.query(
                sql.toString(),
                params.toArray(),
                (rs, rowNum) -> {
                    GetListBaiVietQueryDTO dto = new GetListBaiVietQueryDTO();
                    dto.setId(rs.getString("ID"));
                    dto.setIdNguoiDung(rs.getString("ID_NGUOI_DUNG"));
                    dto.setTenBaiViet(rs.getString("TEN_BAI_VIET"));
                    dto.setTenNguoiDung(rs.getString("TEN_NGUOI_DUNG"));
                    dto.setNgayDangBai(rs.getLong("NGAY_DANG_BAI"));
                    dto.setNgayCapNhat(rs.getLong("NGAY_CAP_NHAT"));
                    dto.setHinhAnh(rs.getString("HINH_ANH"));
                    return dto;
                });

        // Bao bọc thành ListResponse
        ListResponse response = new ListResponse();
        response.setItems(items);

        return response;
    }
}
