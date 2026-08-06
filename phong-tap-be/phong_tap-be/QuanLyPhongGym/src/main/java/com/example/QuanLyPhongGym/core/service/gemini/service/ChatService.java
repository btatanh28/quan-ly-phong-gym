package com.example.QuanLyPhongGym.core.service.gemini.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

// import com.example.QuanLyPhongGym.core.service.gemini.GeminiService;
import com.example.QuanLyPhongGym.core.service.gemini.GroqService;
import com.example.QuanLyPhongGym.domain.entity.app.chitietdonhang.ChiTietDonHang;
import com.example.QuanLyPhongGym.domain.entity.app.dondangky.DonDangKy;
import com.example.QuanLyPhongGym.domain.entity.app.goitap.GoiTap;
import com.example.QuanLyPhongGym.domain.entity.app.khachhang.KhachHang;
import com.example.QuanLyPhongGym.domain.entity.app.thetap.TheTap;
import com.example.QuanLyPhongGym.domain.entity.app.thetapgoitap.TheTapGoiTap;
import com.example.QuanLyPhongGym.domain.repository.app.chitietdonhang.ChiTietDonHangRespository;
import com.example.QuanLyPhongGym.domain.repository.app.dondangky.DonDangKyRespository;
import com.example.QuanLyPhongGym.domain.repository.app.goitap.GoiTapRespository;
import com.example.QuanLyPhongGym.domain.repository.app.khachhang.KhachHangRespository;
import com.example.QuanLyPhongGym.domain.repository.app.thetap.TheTapRespository;
import com.example.QuanLyPhongGym.domain.repository.app.thetapgoitap.TheTapGoiTapRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatService {

        private final GroqService groqService;

        private final KhachHangRespository khachHangRepository;

        private final GoiTapRespository goiTapRepository;

        private final TheTapRespository theTapRepository;

        private final TheTapGoiTapRepository theTapGoiTapRepository;

        private final ChiTietDonHangRespository chiTietDonHangRespository;

        private final DonDangKyRespository donDangKyRespository;

        public String chat(
                        String id,
                        String message) {

                String lower = message.toLowerCase();

                boolean needMemberInfo = lower.contains("gói")
                                || lower.contains("thẻ")
                                || lower.contains("đăng ký")
                                || lower.contains("đã mua")
                                || lower.contains("gia hạn")
                                || lower.contains("hết hạn")
                                || lower.contains("còn bao nhiêu ngày");

                // Lấy thông tin khách hàng từ cơ sở dữ liệu
                KhachHang khachHang = khachHangRepository
                                .findById(id)
                                .orElseThrow(
                                                () -> new RuntimeException(
                                                                "Không tìm thấy khách hàng"));

                String thongTinThanhVien = "";
                if (needMemberInfo) {
                        List<GoiTap> danhSachGoiTap = goiTapRepository.findAll();

                        Map<String, GoiTap> danhSachGoiTapMap = danhSachGoiTap.stream()
                                        .collect(Collectors.toMap(GoiTap::getId, g -> g));

                        TheTap theTap = theTapRepository
                                        .findFirstByIdKhachHangAndTrangThai(
                                                        khachHang.getId(),
                                                        1);

                        // Tạo thông tin gói tập và thẻ tập để gửi cho chatbot
                        String thongTinGoiTap = danhSachGoiTap.stream()
                                        .map(g -> "- " + g.getTenGoiTap()
                                                        + ": "
                                                        + g.getGia()
                                                        + " VNĐ")
                                        .collect(Collectors.joining("\n"));

                        String thongTinTheTap = "Khách hàng chưa có gói tập";

                        // Nếu khách hàng có thẻ tập, lấy thông tin số ngày còn lại
                        if (theTap != null) {
                                List<TheTapGoiTap> danhSachTheTapGoiTap = theTapGoiTapRepository
                                                .findAllByIdTheTapAndTrangThai(
                                                                theTap.getId(),
                                                                1);

                                if (!danhSachTheTapGoiTap.isEmpty()) {
                                        int tongSoNgayConLai = danhSachTheTapGoiTap.stream()
                                                        .mapToInt(TheTapGoiTap::getSoNgayConLai)
                                                        .sum();

                                        thongTinTheTap = "- Số ngày còn lại: " + tongSoNgayConLai + " ngày";
                                }
                        }

                        List<DonDangKy> danhSachDonDangKy = donDangKyRespository
                                        .findAllByIdKhachHang(khachHang.getId());

                        String thongTinGoiTapDaMua = "Khách hàng chưa mua gói tập";

                        if (!danhSachDonDangKy.isEmpty()) {
                                List<String> idsDonHang = danhSachDonDangKy.stream()
                                                .map(DonDangKy::getId)
                                                .toList();

                                List<ChiTietDonHang> danhSachChiTiet = chiTietDonHangRespository
                                                .findAllByIdDonHangIn(idsDonHang);

                                if (!danhSachChiTiet.isEmpty()) {

                                        thongTinGoiTapDaMua = danhSachChiTiet.stream()
                                                        .map(ct -> {

                                                                GoiTap goiTap = danhSachGoiTapMap.get(ct.getIdGoiTap());

                                                                if (goiTap == null) {
                                                                        return null;
                                                                }

                                                                return "- "
                                                                                + goiTap.getTenGoiTap()
                                                                                + ": "
                                                                                + goiTap.getGia()
                                                                                + " VNĐ";

                                                        })
                                                        .filter(item -> item != null)
                                                        .collect(Collectors.joining("\n"));
                                }
                        }

                        thongTinThanhVien = """
                                        Thông tin khách hàng:
                                        - Họ tên: %s
                                        - Email: %s
                                        - Số điện thoại: %s

                                        Thông tin gói tập hiện tại của khách:
                                        %s

                                        Danh sách gói tập:
                                        %s

                                        Các gói tập đã mua:
                                        %s
                                        """
                                        .formatted(
                                                        khachHang.getTenKhachHang(),
                                                        khachHang.getEmail(),
                                                        khachHang.getSoDienThoai(),
                                                        thongTinTheTap,
                                                        thongTinGoiTap,
                                                        thongTinGoiTapDaMua);
                }

                String context = """
                                Bạn là chatbot tư vấn của phòng gym.

                                Câu hỏi:
                                %s

                                %s

                                Nhiệm vụ:
                                - Trả lời đúng trọng tâm câu hỏi.
                                - Tư vấn về gói tập, huấn luyện viên, lịch tập.
                                - Tư vấn dinh dưỡng, chế độ ăn và tập luyện.
                                - Hỗ trợ các mục tiêu: tăng cân, giảm cân, tăng cơ, giảm mỡ, giữ dáng và cải thiện sức khỏe.

                                Quy tắc:
                                - Trả lời ngắn gọn (không quá 7 dòng).
                                - Dễ hiểu, thân thiện, có thể dùng emoji.
                                - Không dùng markdown (*, **, #).
                                - Không giới thiệu gói tập nếu khách không hỏi.
                                - Không hỏi lại mục tiêu nếu khách đã nêu rõ.
                                - Nếu thiếu thông tin thì chỉ hỏi thêm những gì cần thiết.
                                - Không chẩn đoán bệnh; nếu có vấn đề sức khỏe hãy khuyên khách tham khảo bác sĩ.
                                """
                                .formatted(
                                                message,
                                                thongTinThanhVien);

                return groqService.chat(context);

        }
}
