package com.habibi.cafemanagement.service;

import com.habibi.cafemanagement.dto.PageAndSortRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class BaseService<T, ID> {

    protected abstract JpaRepository<T, ID> getRepository();

    public Page<T> getAllWithPagination(PageAndSortRequest request) {
        Pageable pageable = PageableUtil.buildPageable(request);
        return getRepository().findAll(pageable);
    }
}
