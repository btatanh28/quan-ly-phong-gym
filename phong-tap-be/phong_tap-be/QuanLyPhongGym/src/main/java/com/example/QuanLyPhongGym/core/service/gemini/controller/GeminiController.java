package com.example.QuanLyPhongGym.core.service.gemini.controller;

import org.springframework.web.bind.annotation.*;

import com.example.QuanLyPhongGym.core.service.gemini.dto.ChatRequest;
import com.example.QuanLyPhongGym.core.service.gemini.service.ChatService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/gemini")
@RequiredArgsConstructor
public class GeminiController {

    private final ChatService chatService;

    @PostMapping
    public String chat(
            @RequestBody ChatRequest request) {

        return chatService.chat(
                request.getId(),
                request.getMessage());

    }
}