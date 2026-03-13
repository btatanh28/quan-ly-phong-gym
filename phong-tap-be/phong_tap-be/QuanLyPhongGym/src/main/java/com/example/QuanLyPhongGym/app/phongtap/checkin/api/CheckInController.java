package com.example.QuanLyPhongGym.app.phongtap.checkin.api;

import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.QuanLyPhongGym.app.phongtap.checkin.command.CheckInCommand;
import com.example.QuanLyPhongGym.app.phongtap.checkin.command.CheckInCommandHandler;
import com.example.QuanLyPhongGym.core.model.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("check-in")
@Lazy
@RequiredArgsConstructor

public class CheckInController {
    private final CheckInCommandHandler checkInCommandHandler;

    @PostMapping()
    public ResponseEntity<ApiResponse<String>> checkIn(
            @RequestBody CheckInCommand request) {

        ApiResponse<String> response = checkInCommandHandler.handle(request);

        return ResponseEntity.ok(response);
    }
}
