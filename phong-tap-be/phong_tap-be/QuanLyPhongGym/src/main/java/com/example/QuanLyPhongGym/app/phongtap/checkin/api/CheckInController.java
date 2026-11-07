package com.example.QuanLyPhongGym.app.phongtap.checkin.api;

import com.example.QuanLyPhongGym.app.phongtap.checkin.command.CheckInResponse;
import com.example.QuanLyPhongGym.app.phongtap.checkin.query.getlist.GetListLichSuCheckIn;
import com.example.QuanLyPhongGym.app.phongtap.checkin.query.getlist.GetListLichSuCheckInHandler;
import com.example.QuanLyPhongGym.core.model.response.ListResponse;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    private final GetListLichSuCheckInHandler getListLichSuCheckInHandler;

    @PostMapping()
    public ResponseEntity<ApiResponse<CheckInResponse>> checkIn(
            @RequestBody CheckInCommand request) {

        ApiResponse<CheckInResponse> response = checkInCommandHandler.handle(request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("list")
    public ResponseEntity<ListResponse> getList(@ModelAttribute GetListLichSuCheckIn request) {
        ListResponse response = getListLichSuCheckInHandler.handle(request);
        return ResponseEntity.ok(response);
    }
}
