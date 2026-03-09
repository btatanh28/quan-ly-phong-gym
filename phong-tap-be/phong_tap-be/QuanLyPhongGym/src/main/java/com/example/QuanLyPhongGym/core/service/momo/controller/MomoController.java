package com.example.QuanLyPhongGym.core.service.momo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.QuanLyPhongGym.core.service.momo.service.MomoService;

@RestController
@RequestMapping("/momo")

public class MomoController {
    @Autowired
    private MomoService momoService;

    @PostMapping("/pay")
    public ResponseEntity<?> pay(@RequestParam long amount,
            @RequestParam String orderId) throws Exception {

        return ResponseEntity.ok(
                momoService.createPayment(amount, orderId));
    }
}
