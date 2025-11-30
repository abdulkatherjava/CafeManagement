package com.habibi.cafemanagement.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;

import java.util.List;

public class PageAndSortRequest {
    @Min(value = 0, message = "Page index must not be negative")
    private int page = 0;

    @Positive(message = "Page size must be greater than zero")
    private int size = 10;
    private List<String> sort; // e.g. ["categoryName,asc", "createdAt,desc"]

    // getters and setters
    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<String> getSort() {
        return sort;
    }

    public void setSort(List<String> sort) {
        this.sort = sort;
    }
}
