package com.habibi.cafemanagement.service;

import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class SortingUtil {

    private SortingUtil() {
    }

    public static List<Sort.Order> parseSortParams(String[] sortParams) {
        if (sortParams == null || sortParams.length == 0) {
            return Collections.emptyList();
        }

        List<Sort.Order> orders = new ArrayList<>();
        for (String param : sortParams) {
            if (param == null || param.isBlank()) {
                continue;
            }

            String[] parts = param.split(",");
            String property = parts[0].trim();
            if (property.isEmpty()) {
                continue;
            }

            Sort.Direction direction = Sort.Direction.ASC;
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

    public static List<String> describeSort(Sort sort) {
        if (sort == null || sort.isUnsorted()) {
            return Collections.emptyList();
        }

        List<String> sortDescriptions = new ArrayList<>();
        for (Sort.Order order : sort) {
            sortDescriptions.add(order.getProperty() + "," + order.getDirection().name().toLowerCase());
        }
        return sortDescriptions;
    }
}
