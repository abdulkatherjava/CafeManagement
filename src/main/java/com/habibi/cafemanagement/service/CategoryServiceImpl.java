package com.habibi.cafemanagement.service;

import com.habibi.cafemanagement.dto.CategoryRequest;
import com.habibi.cafemanagement.dto.CategoryResponse;
import com.habibi.cafemanagement.exception.ResourceNotFoundException;
import com.habibi.cafemanagement.model.Category;
import com.habibi.cafemanagement.repository.CategoryRepository;
import com.habibi.cafemanagement.dto.PageAndSortRequest;
import com.habibi.cafemanagement.dto.PagedResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl extends BaseService<Category, Long> implements CategoryService {

    private final CategoryRepository categoryRepository;

    // Prefer constructor injection
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    protected JpaRepository<Category, Long> getRepository() {
        return categoryRepository;
    }

    @Transactional
    @Override
    public CategoryResponse createCategory(CategoryRequest request) {
        Category category = new Category();
        category.setCategoryName(request.getCategoryName());
        Category saved = categoryRepository.save(category);
        return mapToResponse(saved);
    }

    @Transactional
    @Override
    public CategoryResponse updateCategory(Long id, CategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Category not found with id: %s and name: %s ", id, request.getCategoryName())
                ));

        category.setCategoryName(request.getCategoryName());
        Category updated = categoryRepository.save(category);
        return mapToResponse(updated);
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        return Optional.ofNullable(categoryRepository.findAll())
                .orElse(Collections.emptyList())
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponse getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        return mapToResponse(category);
    }

    @Transactional
    @Override
    public void deleteCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        categoryRepository.delete(category);
    }

    @Override
    public List<CategoryResponse> searchCategoriesByName(String namePart) {
        if (namePart == null || namePart.trim().length() < 2) {
            throw new IllegalArgumentException("Search term must be at least 2 characters");
        }

        List<Category> categories = categoryRepository.findByCategoryNameContainingIgnoreCase(namePart);

        if (categories.isEmpty()) {
            throw new ResourceNotFoundException("No categories found matching: " + namePart);
        }

        return categories.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public PagedResponse<CategoryResponse> getAllCategories(PageAndSortRequest request) {
        try {
            Page<Category> categoryPage = getAllWithPagination(request);

            List<CategoryResponse> items = categoryPage.getContent().stream()
                    .map(this::mapToResponse)
                    .collect(Collectors.toList());

            return PageableUtil.toPagedResponse(categoryPage, items);

        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch paginated/sorted categories: " + e.getMessage(), e);
        }
    }

    private CategoryResponse mapToResponse(Category category) {
        CategoryResponse response = new CategoryResponse();
        response.setId(category.getId());
        response.setCategoryName(category.getCategoryName());
        response.setCreatedAt(category.getCreatedAt());
        response.setUpdatedAt(category.getUpdatedAt());
        return response;
    }
}
