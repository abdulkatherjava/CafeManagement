package com.habibi.cafemanagement.service;

import com.habibi.cafemanagement.dto.CategoryRequest;
import com.habibi.cafemanagement.dto.CategoryResponse;
import com.habibi.cafemanagement.exception.ResourceNotFoundException;
import com.habibi.cafemanagement.model.Category;
import com.habibi.cafemanagement.repository.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    // Prefer constructor injection
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
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
        List<Category> categories = categoryRepository.findByCategoryNameContainingIgnoreCase(namePart);

        if (categories.isEmpty()) {
            throw new ResourceNotFoundException("No categories found matching: " + namePart);
        }

        return categories.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<CategoryResponse> getAllCategories(int page, int size, String[] sortParams) {
        try {
            List<Sort.Order> orders = sortUtil(sortParams);
            Pageable pageable = PageRequest.of(page, size, Sort.by(orders));
            Page<Category> categoryPage = categoryRepository.findAll(pageable);

            return categoryPage.getContent().stream()
                    .map(this::mapToResponse)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch paginated/sorted categories: " + e.getMessage(), e);
        }
    }

    // --- helpers ---

    private static List<Sort.Order> sortUtil(String[] sortParams) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortParams == null || sortParams.length == 0) return orders;

        for (String param : sortParams) {
            if (param == null || param.isBlank()) continue;

            String[] parts = param.split(",");
            String property = parts[0].trim();
            if (property.isEmpty()) continue;

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

    private CategoryResponse mapToResponse(Category category) {
        CategoryResponse response = new CategoryResponse();
        response.setId(category.getId());
        response.setCategoryName(category.getCategoryName());
        response.setCreatedAt(category.getCreatedAt());
        response.setUpdatedAt(category.getUpdatedAt());
        return response;
    }
}
