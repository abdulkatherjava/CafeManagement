package com.habibi.cafemanagement.common;

import com.habibi.cafemanagement.dto.PagedResponse;
import com.habibi.cafemanagement.dto.SortRequest;
import org.springframework.data.domain.Page;
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

    // Convert SortRequest list to Pageable
    public static Pageable toPageable(int page, int size, List<SortRequest> sortObjects) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortObjects != null) {
            for (SortRequest s : sortObjects) {
                String dir = s.getDirection() == null ? "asc" : s.getDirection().toLowerCase();
                orders.add(new Sort.Order(Sort.Direction.fromString(dir), s.getProperty()));
            }
        }
        return orders.isEmpty() ? PageRequest.of(page, size) : PageRequest.of(page, size, Sort.by(orders));
    }

    // Parse query param sort strings to SortRequest list
    public static List<SortRequest> parseSortParams(List<String> sortParams) {
        if (sortParams == null || sortParams.isEmpty()) {
            return null;
        }
        List<SortRequest> sortObjects = new ArrayList<>();
        for (String param : sortParams) {
            if (param == null || param.trim().isEmpty())
                continue;
            String[] parts = param.split(",");
            String prop = parts[0].trim();
            String dir = parts.length > 1 ? parts[1].trim() : "asc";
            SortRequest sr = new SortRequest(prop, dir);
            sortObjects.add(sr);
        }
        return sortObjects;
    }

    // Convert Page<T> to PagedResponse<T>
    public static <T> PagedResponse<T> toPagedResponse(Page<T> page) {
        PagedResponse<T> resp = new PagedResponse<>();
        resp.setData(page.getContent());
        resp.setPage(page.getNumber());
        resp.setSize(page.getSize());
        resp.setTotalElements(page.getTotalElements());
        resp.setTotalPages(page.getTotalPages());

        List<SortRequest> sortRequests = new ArrayList<>();
        page.getSort()
                .forEach(order -> sortRequests.add(new SortRequest(order.getProperty(), order.getDirection().name())));
        resp.setSort(sortRequests);

        return resp;
    }
}
