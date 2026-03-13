package com.example.QuanLyPhongGym.app.phongtap.thetap.api;

import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.QuanLyPhongGym.app.phongtap.thetap.query.getQrCodeKhachHang.GetQrCodeKhachHangQuery;
import com.example.QuanLyPhongGym.app.phongtap.thetap.query.getQrCodeKhachHang.GetQrCodeKhachHangQueryHandler;
import com.example.QuanLyPhongGym.app.phongtap.thetap.query.getQrCodeKhachHang.GetQrCodeResponse;
import com.example.QuanLyPhongGym.core.model.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@Lazy
@RequestMapping("the-tap")
@RequiredArgsConstructor

public class TheTapController {
    private final GetQrCodeKhachHangQueryHandler getQrCodeKhachHangQueryHandler;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<GetQrCodeResponse>> get(@PathVariable String id) {

        GetQrCodeKhachHangQuery request = new GetQrCodeKhachHangQuery();
        request.setId(id);

        ApiResponse<GetQrCodeResponse> response = getQrCodeKhachHangQueryHandler.handle(request);

        return ResponseEntity.ok(response);
    }
}
