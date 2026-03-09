package com.example.QuanLyPhongGym.core.model.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ListResponse {

    private List<?> items;

    private long totalItems;
    private int page;
    private int size;
    private int totalPages;
}
