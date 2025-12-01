package com.habibi.cafemanagement.dto;

import java.util.List;

public class PageAndSortRequest {
    private int page = 0;
    private int size = 10;
    private List<SortRequest> sortObjects; // e.g. [{"property":"categoryName","direction":"asc"}]

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

    public List<SortRequest> getSortObjects() {
        return sortObjects;
    }

    public void setSortObjects(List<SortRequest> sortObjects) {
        this.sortObjects = sortObjects;
    }
}
