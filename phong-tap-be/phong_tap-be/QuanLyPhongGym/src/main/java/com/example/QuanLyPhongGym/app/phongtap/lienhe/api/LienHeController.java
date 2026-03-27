package com.example.QuanLyPhongGym.app.phongtap.lienhe.api;

import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.QuanLyPhongGym.app.phongtap.khachhang.command.delete.DeleteKhachHangCommand;
import com.example.QuanLyPhongGym.app.phongtap.khachhang.query.getlist.GetListKhachHangQuery;
import com.example.QuanLyPhongGym.app.phongtap.lienhe.command.create.CreateLienHeCommand;
import com.example.QuanLyPhongGym.app.phongtap.lienhe.command.create.CreateLienHeCommandHandler;
import com.example.QuanLyPhongGym.app.phongtap.lienhe.command.delete.DeleteLienHeCommand;
import com.example.QuanLyPhongGym.app.phongtap.lienhe.command.delete.DeleteLienHeCommandHandler;
import com.example.QuanLyPhongGym.app.phongtap.lienhe.query.getlist.GetListLienHeQuery;
import com.example.QuanLyPhongGym.app.phongtap.lienhe.query.getlist.GetListLienHeQueryHandler;
import com.example.QuanLyPhongGym.core.model.response.DataResponse;
import com.example.QuanLyPhongGym.core.model.response.ListResponse;

import lombok.RequiredArgsConstructor;

@RestController
@Lazy
@RequestMapping("lien-he")
@RequiredArgsConstructor

public class LienHeController {
    private final CreateLienHeCommandHandler createLienHeCommandHandler;
    private final GetListLienHeQueryHandler getListLienHeQueryHandler;
    private final DeleteLienHeCommandHandler deleteLienHeCommandHandler;

    @GetMapping("list")
    public ResponseEntity<ListResponse> getList(@ModelAttribute GetListLienHeQuery request) {
        ListResponse response = getListLienHeQueryHandler.handle(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping()
    public ResponseEntity<DataResponse> create(@RequestBody CreateLienHeCommand request) {
        DataResponse response = createLienHeCommandHandler.handle(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<DataResponse> delete(DeleteLienHeCommand request) {
        DataResponse response = deleteLienHeCommandHandler.handle(request);
        return ResponseEntity.ok(response);
    }
}
