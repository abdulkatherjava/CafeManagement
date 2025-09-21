package com.habibi.cafemanagement.dto;

import java.util.List;

public class MenuItemPageRequest {
    private int page = 0;
    private int size = 10;
    private List<String> sort;

    // --- getters & setters ---
    public int getPage() { return page; }
    public void setPage(int page) { this.page = page; }

    public int getSize() { return size; }
    public void setSize(int size) { this.size = size; }

    public List<String> getSort() { return sort; }
    public void setSort(List<String> sort) { this.sort = sort; }
}
