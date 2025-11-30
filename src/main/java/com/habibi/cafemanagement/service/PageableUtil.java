package com.habibi.cafemanagement.service;

import com.habibi.cafemanagement.dto.PageAndSortRequest;
import com.habibi.cafemanagement.dto.PagedResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public class PageableUtil {

    private PageableUtil() {
    }

    public static Pageable buildPageable(int page, int size, String[] sortParams) {
        int safePage = Math.max(page, 0);
        int safeSize = size > 0 ? size : 10;

        List<Sort.Order> orders = SortingUtil.parseSortParams(sortParams);
        Sort sort = orders.isEmpty() ? Sort.unsorted() : Sort.by(orders);
        return PageRequest.of(safePage, safeSize, sort);
    }

    public static Pageable buildPageable(PageAndSortRequest request) {
        if (request == null) {
            return buildPageable(0, 10, null);
        }
        String[] sortParams = request.getSort() != null ? request.getSort().toArray(new String[0]) : null;
        return buildPageable(request.getPage(), request.getSize(), sortParams);
    }

    public static <T> PagedResponse<T> toPagedResponse(Page<?> page, List<T> content) {
        return new PagedResponse<>(
                content,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast(),
                SortingUtil.describeSort(page.getSort())
        );
    }
}
