package com.habibi.cafemanagement.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class BaseService<T, ID> {

    protected abstract JpaRepository<T, ID> getRepository();

    public Page<T> getAllWithPagination(int page, int size, String[] sortParams) {
        Pageable pageable = PageableUtil.buildPageable(page, size, sortParams);
        return getRepository().findAll(pageable);
    }
}
