package com.example.QuanLyPhongGym.core.service.gemini.service;

import java.util.List;
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
                // Lấy thông tin khách hàng từ cơ sở dữ liệu
                KhachHang khachHang = khachHangRepository
                                .findById(id)
                                .orElseThrow(
                                                () -> new RuntimeException(
                                                                "Không tìm thấy khách hàng"));

                // Lấy danh sách gói tập từ cơ sở dữ liệu
                List<GoiTap> danhSachGoiTap = goiTapRepository.findAll();

                // Lấy thông tin thẻ tập của khách hàng từ cơ sở dữ liệu
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

                                thongTinTheTap = danhSachTheTapGoiTap.stream()
                                                .map(ttgt -> {

                                                        GoiTap goiTap = goiTapRepository
                                                                        .findById(ttgt.getIdGoiTap())
                                                                        .orElse(null);

                                                        int tongSoNgayConLai = danhSachTheTapGoiTap.stream()
                                                                        .mapToInt(TheTapGoiTap::getSoNgayConLai)
                                                                        .sum();

                                                        if (goiTap == null) {
                                                                return "";
                                                        }

                                                        return "- "
                                                                        + "  Số ngày còn lại: "
                                                                        + tongSoNgayConLai
                                                                        + " ngày";

                                                })
                                                .collect(Collectors.joining("\n"));
                        }
                }

                List<DonDangKy> danhSachDonDangKy = donDangKyRespository
                                .findAllByIdKhachHang(khachHang.getId());

                String thongTinGoiTapDaMua = "Khách hàng chưa mua gói tập";

                if (!danhSachDonDangKy.isEmpty()) {

                        List<ChiTietDonHang> danhSachChiTiet = danhSachDonDangKy
                                        .stream()
                                        .flatMap(don -> chiTietDonHangRespository
                                                        .findAllByIdDonHang(don.getId())
                                                        .stream())
                                        .toList();

                        if (!danhSachChiTiet.isEmpty()) {

                                thongTinGoiTapDaMua = danhSachChiTiet.stream()
                                                .map(ct -> {

                                                        GoiTap goiTap = goiTapRepository
                                                                        .findById(ct.getIdGoiTap())
                                                                        .orElse(null);

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

                String context = """

                                Bạn là chatbot chăm sóc khách hàng của phòng gym.

                                Thông tin khách hàng:

                                - Họ tên: %s
                                - Email: %s
                                - Số điện thoại: %s

                                Danh sách gói tập hiện có:
                                %s


                                Thông tin gói tập khách hàng:
                                %s


                                Nhiệm vụ:
                                - Tư vấn các dịch vụ của phòng gym.
                                - Hỗ trợ khách hàng về gói tập, lịch tập, huấn luyện viên.
                                - Trả lời thân thiện như nhân viên tư vấn thật.


                                Quy tắc trả lời:
                                - Trả lời ngắn gọn, dễ đọc trên ứng dụng chat.
                                - Không dùng ký hiệu markdown như *, **, #.
                                - Có thể sử dụng emoji phù hợp.
                                - Không trả lời quá 7 dòng.
                                - Nếu khách hỏi giới thiệu phòng gym, trả lời theo mẫu:


                                Xin chào anh/chị %s 👋

                                💪 Phòng gym bên em có:
                                • Không gian tập luyện hiện đại, sạch sẽ.
                                • Trang thiết bị đầy đủ, phù hợp nhiều mục tiêu.
                                • Các lớp Yoga, Zumba, Group X.
                                • Huấn luyện viên hỗ trợ tận tình.

                                🎁 Hiện phòng gym đang có chương trình trải nghiệm miễn phí.
                                Anh/chị muốn đăng ký lịch tập thử vào thời gian nào ạ?


                                Câu hỏi của khách hàng:
                                %s


                                """
                                .formatted(

                                                khachHang.getTenKhachHang(),

                                                khachHang.getEmail(),

                                                khachHang.getSoDienThoai(),

                                                thongTinGoiTap,

                                                thongTinTheTap,

                                                thongTinGoiTapDaMua,

                                                message

                                );

                return groqService.chat(context);

        }

}