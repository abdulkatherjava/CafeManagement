package com.habibi.cafemanagement.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

public class PageableUtil {

    public static Pageable buildPageable(int page, int size, String[] sortParams) {
        if (sortParams == null || sortParams.length == 0) {
            return PageRequest.of(page, size, Sort.unsorted());
        }

        List<Sort.Order> orders = CategoryService1.sortUtil(sortParams);

        return PageRequest.of(page, size, Sort.by(orders));
    }
}
