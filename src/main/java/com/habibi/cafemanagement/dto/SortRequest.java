package com.habibi.cafemanagement.dto;

import java.util.List;

public class SortRequest {
    private String property;
    private String direction;

    public SortRequest() {
    }

    public SortRequest(String property, String direction) {
        this.property = property;
        this.direction = direction;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
