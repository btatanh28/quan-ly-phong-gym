package com.example.QuanLyPhongGym.app.phongtap.comments.query.getlist;

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

public class GetListCommentQueryHandler implements IRequestHandler<GetListCommentQuery, ListResponse> {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public ListResponse handle(GetListCommentQuery request) {
        StringBuilder sql = new StringBuilder("""
                    SELECT
                        bl.ID,
                        bl.ID_NGUOI_DUNG,
                        bl.ID_KHACH_HANG,
                        bl.ID_DANH_GIA,
                        bl.NOI_DUNG,
                        bl.HINH_ANH,
                        bl.NGAY_BINH_LUAN,
                        kh.TEN_KHACH_HANG,
                        nd.TEN_NGUOI_DUNG
                    FROM BINH_LUAN bl
                    LEFT JOIN KHACH_HANG kh ON bl.ID_KHACH_HANG = kh.ID
                    LEFT JOIN NGUOI_DUNG nd ON bl.ID_NGUOI_DUNG = nd.ID
                    WHERE 1=1
                """);

        StringBuilder countSql = new StringBuilder("""
                    SELECT COUNT(*)
                    FROM BINH_LUAN bl
                    WHERE 1=1
                """);

        List<Object> params = new ArrayList<>();
        List<Object> countParams = new ArrayList<>();

        // Filter theo ID_DANH_GIA
        if (request.getIdDanhGia() != null && !request.getIdDanhGia().isEmpty()) {
            sql.append(" AND bl.ID_DANH_GIA = ?");
            countSql.append(" AND bl.ID_DANH_GIA = ?");

            params.add(request.getIdDanhGia());
            countParams.add(request.getIdDanhGia());
        }

        // Phân trang
        int page = request.getPage() != null ? request.getPage() : 0;
        int size = request.getSize() != null ? request.getSize() : 10;
        int offset = page * size;

        sql.append(" ORDER BY bl.NGAY_BINH_LUAN DESC LIMIT ? OFFSET ?");
        params.add(size);
        params.add(offset);

        List<GetListCommentQueryDTO> items = jdbcTemplate.query(
                sql.toString(),
                params.toArray(),
                (rs, rowNum) -> {
                    GetListCommentQueryDTO dto = new GetListCommentQueryDTO();
                    dto.setId(rs.getString("ID"));
                    dto.setIdNguoiDung(rs.getString("ID_NGUOI_DUNG"));
                    dto.setIdKhachHang(rs.getString("ID_KHACH_HANG"));
                    dto.setIdDanhGia(rs.getString("ID_DANH_GIA"));
                    dto.setNoiDung(rs.getString("NOI_DUNG"));
                    dto.setHinhAnh(rs.getString("HINH_ANH"));
                    dto.setNgayBinhLuan(rs.getLong("NGAY_BINH_LUAN"));
                    dto.setTenKhachHang(rs.getString("TEN_KHACH_HANG"));
                    dto.setTenNguoiDung(rs.getString("TEN_NGUOI_DUNG"));
                    return dto;
                });

        Long totalItems = jdbcTemplate.queryForObject(
                countSql.toString(),
                countParams.toArray(),
                Long.class);

        ListResponse response = new ListResponse();
        response.setItems(items);
        response.setTotalItems(totalItems);
        response.setPage(page);
        response.setSize(size);
        response.setTotalPages((int) Math.ceil((double) totalItems / size));

        return response;
    }
}
