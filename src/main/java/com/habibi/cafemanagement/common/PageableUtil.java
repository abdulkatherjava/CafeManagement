package com.habibi.cafemanagement.common;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

public class PageableUtil {

    public static Pageable buildPageable(int page, int size, String[] sortParams) {
        if (sortParams == null || sortParams.length == 0) {
            return PageRequest.of(page, size, Sort.unsorted());
        }

        List<Sort.Order> orders = sortUtil(sortParams);

        return PageRequest.of(page, size, Sort.by(orders));
    }

    // Move sort utility here so we don't depend on another service class
    public static List<Sort.Order> sortUtil(String[] sortParams) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortParams == null || sortParams.length == 0)
            return orders;

        for (String param : sortParams) {
            if (param == null || param.isBlank())
                continue;

            String[] parts = param.split(",");
            String property = parts[0].trim();
            if (property.isEmpty())
                continue;

            Sort.Direction direction = Sort.Direction.ASC; // default
            if (parts.length > 1 && !parts[1].isBlank()) {
                try {
                    direction = Sort.Direction.fromString(parts[1].trim());
                } catch (IllegalArgumentException ignored) {
                    // keep default ASC if direction is invalid
                }
            }

            orders.add(new Sort.Order(direction, property));
        }

        return orders;
    }
}
