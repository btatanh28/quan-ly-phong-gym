package com.example.QuanLyPhongGym.core.model.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class PageResponse {
    private List<?> items;
    private long total;
    private int page;
    private int size;
}
