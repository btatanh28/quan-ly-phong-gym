package com.example.QuanLyPhongGym.app.phongtap.baiviet.api;

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

import com.example.QuanLyPhongGym.app.phongtap.baiviet.command.create.CreateBaiVietCommand;
import com.example.QuanLyPhongGym.app.phongtap.baiviet.command.create.CreateBaiVietCommandHandler;
import com.example.QuanLyPhongGym.app.phongtap.baiviet.command.delete.DeleteBaiVietCommand;
import com.example.QuanLyPhongGym.app.phongtap.baiviet.command.delete.DeleteBaiVietCommandHandler;
import com.example.QuanLyPhongGym.app.phongtap.baiviet.command.update.UpdateBaiVietCommand;
import com.example.QuanLyPhongGym.app.phongtap.baiviet.command.update.UpdateBaiVietCommandHandler;
import com.example.QuanLyPhongGym.app.phongtap.baiviet.query.get.GetBaiVietQuery;
import com.example.QuanLyPhongGym.app.phongtap.baiviet.query.get.GetBaiVietQueryDTO;
import com.example.QuanLyPhongGym.app.phongtap.baiviet.query.get.GetBaiVietQueryHandler;
import com.example.QuanLyPhongGym.app.phongtap.baiviet.query.getlist.GetListBaiVietQuery;
import com.example.QuanLyPhongGym.app.phongtap.baiviet.query.getlist.GetListBaiVietQueryHandler;

import com.example.QuanLyPhongGym.core.model.response.DataResponse;
import com.example.QuanLyPhongGym.core.model.response.ListResponse;

import lombok.RequiredArgsConstructor;

@RestController
@Lazy
@RequestMapping("bai-viet")
@RequiredArgsConstructor

public class BaiVietController {
    private final GetListBaiVietQueryHandler getListBaiVietQueryHandler;
    private final GetBaiVietQueryHandler getBaiVietQueryHandler;
    private final CreateBaiVietCommandHandler createBaiVietCommandHandler;
    private final UpdateBaiVietCommandHandler updateBaiVietCommandHandler;
    private final DeleteBaiVietCommandHandler deleteBaiVietCommandHandler;

    @GetMapping("{id}")
    public ResponseEntity<GetBaiVietQueryDTO> get(@PathVariable String id) {
        GetBaiVietQuery request = new GetBaiVietQuery(); // để handler nhận
        request.setId(id);
        GetBaiVietQueryDTO response = getBaiVietQueryHandler.handle(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("list")
    public ResponseEntity<ListResponse> getList(@ModelAttribute GetListBaiVietQuery request) {
        ListResponse response = getListBaiVietQueryHandler.handle(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping()
    public ResponseEntity<DataResponse> create(@RequestBody CreateBaiVietCommand request) {
        DataResponse response = createBaiVietCommandHandler.handle(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("edit/{id}")
    public ResponseEntity<DataResponse> update(@RequestBody UpdateBaiVietCommand request) {
        DataResponse response = updateBaiVietCommandHandler.handle(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<DataResponse> delete(DeleteBaiVietCommand request) {
        DataResponse response = deleteBaiVietCommandHandler.handle(request);
        return ResponseEntity.ok(response);
    }
}
