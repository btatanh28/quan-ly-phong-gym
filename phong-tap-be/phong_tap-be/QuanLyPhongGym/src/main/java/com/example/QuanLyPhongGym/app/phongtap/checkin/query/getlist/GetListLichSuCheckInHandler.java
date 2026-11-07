package com.example.QuanLyPhongGym.app.phongtap.checkin.query.getlist;

import com.example.QuanLyPhongGym.core.model.response.ListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import com.example.QuanLyPhongGym.core.mediator.IRequestHandler;


import java.util.ArrayList;
import java.util.List;

@Component
@Lazy
@RequiredArgsConstructor
public class GetListLichSuCheckInHandler implements IRequestHandler<GetListLichSuCheckIn, ListResponse> {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public ListResponse handle(GetListLichSuCheckIn request) {
        StringBuilder sql = new StringBuilder(
                "SELECT " +
                        "    cn.ID, " +
                        "    cn.ID_THE_TAP, " +
                        "    tp.QR_CODE, " +
                        "    cn.ID_KHACH_HANG, " +
                        "    kh.TEN_KHACH_HANG, " +
                        "    kh.SO_DIEN_THOAI, " +
                        "    cn.THOI_GIAN " +
                        "FROM CHECK_IN cn " +
                        "LEFT JOIN KHACH_HANG kh " +
                        "    ON cn.ID_KHACH_HANG = kh.ID " +
                        "LEFT JOIN THE_TAP tp " +
                        "    ON cn.ID_THE_TAP = tp.ID " +
                        "WHERE 1=1 ");

        List<Object> params = new ArrayList<>();

        if (request.getTenKhachHang() != null && !request.getTenKhachHang().isEmpty()) {
            sql.append(" AND kh.TEN_KHACH_HANG LIKE ?");
            params.add("%" + request.getTenKhachHang() + "%");
        }

        if (request.getSoDienThoai() != null && !request.getSoDienThoai().isEmpty()) {
            sql.append(" AND kh.SO_DIEN_THOAI LIKE ?");
            params.add("%" + request.getSoDienThoai() + "%");
        }

        if (request.getQrCode() != null && !request.getQrCode().isEmpty()) {
            sql.append(" AND tp.QR_CODE LIKE ?");
            params.add("%" + request.getQrCode() + "%");
        }


        sql.append(" ORDER BY cn.THOI_GIAN DESC");

        List<GetListLichSuCheckInDTO> items = jdbcTemplate.query(
                sql.toString(),
                params.toArray(),
                (rs, rowNum) -> {
                    GetListLichSuCheckInDTO dto = new GetListLichSuCheckInDTO();
                    dto.setId(rs.getString("ID"));
                    dto.setIdKhachHang(rs.getString("ID_KHACH_HANG"));
                    dto.setTenKhachHang(rs.getString("TEN_KHACH_HANG"));
//                    dto.setTenGoiTap(rs.getString("TEN_GOI_TAP"));
                    dto.setSoDienThoai(rs.getString("SO_DIEN_THOAI"));
                    dto.setQrCode(rs.getString("QR_CODE"));
                    dto.setThoiGian(rs.getLong("THOI_GIAN"));
                    return dto;
                });

        // Bao bọc thành ListResponse
        ListResponse response = new ListResponse();
        response.setItems(items);

        return response;
    }
}
