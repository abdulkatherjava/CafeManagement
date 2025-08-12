package com.habibi.cafemanagement.controller;

import com.habibi.cafemanagement.dto.CategoryRequest;
import com.habibi.cafemanagement.dto.CategoryResponse;
import com.habibi.cafemanagement.dto.CategorySearchRequest;
import com.habibi.cafemanagement.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Validated
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/categories")
    public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CategoryRequest request) {
        CategoryResponse response = categoryService.createCategory(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response); // Only handles success, rest is handled globally
    }

    // ✅ Update the Category
    @PutMapping("/categories/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryRequest request) {
        CategoryResponse updatedCategory = categoryService.updateCategory(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(updatedCategory);
    }

    // ✅ GET category by ID
    @GetMapping("/categories/{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Long id) {
        CategoryResponse response = categoryService.getCategoryById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // ✅ DELETE category by ID
    @DeleteMapping("/categories/{id}")
    public ResponseEntity<Void> deleteCategoryById(@PathVariable Long id) {
        categoryService.deleteCategoryById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // ✅ GET category by ID
    @GetMapping("/categories/search")
    public ResponseEntity<List<CategoryResponse>> searchCategoriesByName(@RequestParam("name") String namePart) {
        List<CategoryResponse> result = categoryService.searchCategoriesByName(namePart);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // ✅ GET All Categories
    @GetMapping("/categories")
    public ResponseEntity<List<CategoryResponse>> getAllCategories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "categoryName,asc") String[] sort) {
        List<CategoryResponse> categories = categoryService.getAllCategories(page, size, sort);
        return ResponseEntity.status(HttpStatus.OK).body(categories);
    }

    // ✅ POST Filtered Categories with pagination and sorting
    @PostMapping("/categories/filter")
    public ResponseEntity<List<CategoryResponse>> getCategories(@RequestBody CategorySearchRequest request) {
        List<CategoryResponse> categories = categoryService.getCategories(request);
        return ResponseEntity.status(HttpStatus.OK).body(categories);
    }
}
