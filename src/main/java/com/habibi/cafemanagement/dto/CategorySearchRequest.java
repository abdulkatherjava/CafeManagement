package com.habibi.cafemanagement.dto;

import java.util.List;
import java.util.Map;

public class CategorySearchRequest {
    private Map<String, String> filters;
    private List<SortRequest> sort;
    private int page;
    private int size;

    public Map<String, String> getFilters() {
        return filters;
    }

    public void setFilters(Map<String, String> filters) {
        this.filters = filters;
    }

    public List<SortRequest> getSort() {
        return sort;
    }

    public void setSort(List<SortRequest> sort) {
        this.sort = sort;
    }

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
}
