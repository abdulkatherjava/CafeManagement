package com.habibi.cafemanagement.service;

import com.habibi.cafemanagement.dto.CategoryRequest;
import com.habibi.cafemanagement.dto.CategoryResponse;

import java.util.List;

public interface CategoryService {

    CategoryResponse createCategory(CategoryRequest request);

    CategoryResponse updateCategory(Long id, CategoryRequest request);

    List<CategoryResponse> getAllCategories();

    CategoryResponse getCategoryById(Long id);

    void deleteCategoryById(Long id);

    List<CategoryResponse> searchCategoriesByName(String namePart);

    List<CategoryResponse> getAllCategories(int page, int size, String[] sortParams);

    org.springframework.data.domain.Page<CategoryResponse> getAllCategoriesPage(int page, int size,
            String[] sortParams);
}
