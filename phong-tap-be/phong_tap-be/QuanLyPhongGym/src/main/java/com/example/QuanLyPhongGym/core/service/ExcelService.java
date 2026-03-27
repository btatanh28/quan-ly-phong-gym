package com.example.QuanLyPhongGym.core.service;

import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.function.Function;

@Service
public class ExcelService {

    public <T> InputStreamResource exportCsv(
            String title,
            List<String> headers,
            List<T> data,
            Function<T, List<String>> mapper) {
        StringBuilder sb = new StringBuilder();

        // Title
        sb.append(title).append("\n\n");

        // Header
        sb.append(String.join(",", headers)).append("\n");

        // Data
        for (T item : data) {
            List<String> row = mapper.apply(item);
            sb.append(String.join(",", row)).append("\n");
        }

        return new InputStreamResource(
                new ByteArrayInputStream(sb.toString().getBytes(StandardCharsets.UTF_8)));
    }
}
