package com.example.QuanLyPhongGym.app.phongtap.khachhang.api;

import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.QuanLyPhongGym.app.phongtap.khachhang.command.create.CreateKhachHangCommand;
import com.example.QuanLyPhongGym.app.phongtap.khachhang.command.create.CreateKhachHangCommandHandler;
import com.example.QuanLyPhongGym.app.phongtap.khachhang.command.delete.DeleteKhachHangCommand;
import com.example.QuanLyPhongGym.app.phongtap.khachhang.command.delete.DeleteKhachHangCommandHandler;
import com.example.QuanLyPhongGym.app.phongtap.khachhang.command.update.UpdateKhachHangCommand;
import com.example.QuanLyPhongGym.app.phongtap.khachhang.command.update.UpdateKhachHangCommandHandler;
import com.example.QuanLyPhongGym.app.phongtap.khachhang.command.verifyEmail.VerifyEmailCommand;
import com.example.QuanLyPhongGym.app.phongtap.khachhang.command.verifyEmail.VerifyEmailCommandHandler;
import com.example.QuanLyPhongGym.app.phongtap.khachhang.query.get.GetKhachHangQuery;
import com.example.QuanLyPhongGym.app.phongtap.khachhang.query.get.GetKhachHangQueryDTO;
import com.example.QuanLyPhongGym.app.phongtap.khachhang.query.get.GetKhachHangQueryHandler;
import com.example.QuanLyPhongGym.app.phongtap.khachhang.query.getlist.GetListKhachHangQuery;
import com.example.QuanLyPhongGym.app.phongtap.khachhang.query.getlist.GetListKhachHangQueryHandler;
import com.example.QuanLyPhongGym.app.phongtap.user.command.update.UpdateUserCommand;
import com.example.QuanLyPhongGym.core.model.response.DataResponse;
import com.example.QuanLyPhongGym.core.model.response.ListResponse;

import lombok.RequiredArgsConstructor;

@Lazy
@RestController
@RequestMapping("khach-hang")
@RequiredArgsConstructor

public class KhachHangController {
    private final GetListKhachHangQueryHandler getListKhachHangQueryHandler;
    private final CreateKhachHangCommandHandler createKhachHangCommandHandler;
    private final VerifyEmailCommandHandler verifyEmailCommandHandler;
    private final DeleteKhachHangCommandHandler deleteKhachHangCommandHandler;
    private final GetKhachHangQueryHandler getKhachHangQueryHandler;
    private final UpdateKhachHangCommandHandler updateKhachHangCommandHandler;

    @GetMapping("list")
    public ResponseEntity<ListResponse> getList(@ModelAttribute GetListKhachHangQuery request) {
        ListResponse response = getListKhachHangQueryHandler.handle(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<GetKhachHangQueryDTO> get(@PathVariable String id) {
        GetKhachHangQuery request = new GetKhachHangQuery(); // để handler nhận
        request.setId(id);
        GetKhachHangQueryDTO response = getKhachHangQueryHandler.handle(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("register-client")
    public ResponseEntity<DataResponse> create(@RequestBody CreateKhachHangCommand request) {
        DataResponse response = createKhachHangCommandHandler.handle(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("verify")
    public ResponseEntity<DataResponse> verify(@RequestBody VerifyEmailCommand request) {
        DataResponse response = verifyEmailCommandHandler.handle(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("edit/{id}")
    public ResponseEntity<DataResponse> update(@RequestBody UpdateKhachHangCommand request) {
        DataResponse response = updateKhachHangCommandHandler.handle(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<DataResponse> delete(DeleteKhachHangCommand request) {
        DataResponse response = deleteKhachHangCommandHandler.handle(request);
        return ResponseEntity.ok(response);
    }
}
