package com.example.QuanLyPhongGym.app.phongtap.danhgia.query.getlist;

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

public class GetListDanhGiaQueryHandler implements IRequestHandler<GetListDanhGiaQuery, ListResponse> {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public ListResponse handle(GetListDanhGiaQuery request) {
        StringBuilder sql = new StringBuilder(
                "SELECT " +
                        "    dg.ID, " +
                        "    dg.ID_KHACH_HANG, " +
                        "    dg.ID_GOI_TAP, " +
                        "    dg.NGAY_DANH_GIA, " +
                        "    dg.DANH_GIA, " +
                        "    dg.HINH_ANH, " +
                        "    dg.BINH_LUAN, " +
                        "    kh.TEN_KHACH_HANG, " +
                        "    kh.HINH_ANH as hinhAnhKhachHang, " +
                        "    gt.TEN_GOI_TAP " +
                        "FROM DANH_GIA dg " +
                        "JOIN KHACH_HANG kh " +
                        "    ON dg.ID_KHACH_HANG = kh.ID " +
                        "LEFT JOIN GOI_TAP gt " +
                        "    ON dg.ID_GOI_TAP = gt.ID " +
                        "WHERE 1=1 ");

        List<Object> params = new ArrayList<>();

        // if (request.getTenBaiViet() != null && !request.getTenBaiViet().isEmpty()) {
        // sql.append(" AND bv.TEN_BAI_VIET LIKE ?");
        // params.add("%" + request.getTenBaiViet() + "%");
        // }

        // if (request.getTenNguoiDung() != null &&
        // !request.getTenNguoiDung().isEmpty()) {
        // sql.append(" AND nd.TEN_NGUOI_DUNG LIKE ?");
        // params.add("%" + request.getTenNguoiDung() + "%");
        // }

        List<GetListDanhGiaQueryDTO> items = jdbcTemplate.query(
                sql.toString(),
                params.toArray(),
                (rs, rowNum) -> {
                    GetListDanhGiaQueryDTO dto = new GetListDanhGiaQueryDTO();
                    dto.setId(rs.getString("ID"));
                    dto.setIdKhachHang(rs.getString("ID_KHACH_HANG"));
                    dto.setBinhLuan(rs.getString("BINH_LUAN"));
                    dto.setDanhGia(rs.getInt("DANH_GIA"));
                    dto.setHinhAnh(rs.getString("HINH_ANH"));
                    dto.setIdGoiTap(rs.getString("ID_GOI_TAP"));
                    dto.setNgayDanhGia(rs.getLong("NGAY_DANH_GIA"));
                    dto.setTenKhachHang(rs.getString("TEN_KHACH_HANG"));
                    dto.setTenGoiTap(rs.getString("TEN_GOI_TAP"));
                    dto.setHinhAnhKhachHang(rs.getString("hinhAnhKhachHang"));

                    return dto;
                });

        // Bao bọc thành ListResponse
        ListResponse response = new ListResponse();
        response.setItems(items);

        return response;
    }
}
