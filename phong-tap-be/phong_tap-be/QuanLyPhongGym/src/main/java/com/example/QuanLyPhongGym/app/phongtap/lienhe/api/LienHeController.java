package com.example.QuanLyPhongGym.app.phongtap.lienhe.api;

import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.QuanLyPhongGym.app.phongtap.lienhe.command.create.CreateLienHeCommand;
import com.example.QuanLyPhongGym.app.phongtap.lienhe.command.create.CreateLienHeCommandHandler;
import com.example.QuanLyPhongGym.core.model.response.DataResponse;

import lombok.RequiredArgsConstructor;

@RestController
@Lazy
@RequestMapping("lien-he")
@RequiredArgsConstructor

public class LienHeController {
    private final CreateLienHeCommandHandler createLienHeCommandHandler;

    @PostMapping()
    public ResponseEntity<DataResponse> create(@RequestBody CreateLienHeCommand request) {
        DataResponse response = createLienHeCommandHandler.handle(request);
        return ResponseEntity.ok(response);
    }
}
