package com.habibi.cafemanagement.service.category;

import com.habibi.cafemanagement.dto.category.CategoryRequest;
import com.habibi.cafemanagement.dto.category.CategoryResponse;
import com.habibi.cafemanagement.dto.common.SortRequest;
import java.util.List;
import org.springframework.data.domain.Page;

public interface CategoryService {

    CategoryResponse createCategory(CategoryRequest request);

    CategoryResponse updateCategory(Long id, CategoryRequest request);

    List<CategoryResponse> getAllCategories();

    CategoryResponse getCategoryById(Long id);

    void deleteCategoryById(Long id);

    List<CategoryResponse> searchCategoriesByName(String namePart);

    Page<CategoryResponse> getAllCategoriesPage(int page, int size, List<SortRequest> sortObjects);
}
