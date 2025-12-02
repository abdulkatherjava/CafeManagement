package com.habibi.cafemanagement.controller;

import com.habibi.cafemanagement.dto.category.CategoryRequest;
import com.habibi.cafemanagement.dto.category.CategoryResponse;
import com.habibi.cafemanagement.dto.common.PageAndSortRequest;
import com.habibi.cafemanagement.dto.common.PagedResponse;
import com.habibi.cafemanagement.common.PageableUtil;
import com.habibi.cafemanagement.service.category.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    // ✅ Create the Category
    // Testing has done and working
    @PostMapping("/categories")
    public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CategoryRequest request) {
        CategoryResponse response = categoryService.createCategory(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response); // Only handles success, rest is handled
                                                                         // globally
    }

    // ✅ Update the Category
    // Testing has done and working
    @PutMapping("/categories/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryRequest request) {
        CategoryResponse updatedCategory = categoryService.updateCategory(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(updatedCategory);
    }

    // ✅ GET category by ID
    // Testing has done and working
    @GetMapping("/categories/{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Long id) {
        CategoryResponse response = categoryService.getCategoryById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // ✅ DELETE category by ID
    @DeleteMapping("/categories/{id}")
    public ResponseEntity<Void> deleteCategoryById(@PathVariable Long id) {
        categoryService.deleteCategoryById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // ✅ GET category by search string
    // Testing has done and working
    @GetMapping("/categories/search")
    public ResponseEntity<List<CategoryResponse>> searchCategoriesByName(@RequestParam("name") String namePart) {
        List<CategoryResponse> result = categoryService.searchCategoriesByName(namePart);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // ✅ GET All Categories with Pagination & Sorting (POST with JSON body)
    @PostMapping("/categories/list")
    public ResponseEntity<PagedResponse<CategoryResponse>> getAllCategoriesList(
            @RequestBody PageAndSortRequest request) {
        Page<CategoryResponse> page = categoryService.getAllCategoriesPage(
                request.getPage(),
                request.getSize(),
                request.getSortObjects());
        PagedResponse<CategoryResponse> resp = PageableUtil.toPagedResponse(page);
        return ResponseEntity.status(HttpStatus.OK).body(resp);
    }

    // ✅ GET All Categories with Pagination & Sorting (GET with query params - cache
    // friendly)
    @GetMapping("/categories")
    public ResponseEntity<PagedResponse<CategoryResponse>> getAllCategoriesQueryParams(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", required = false) List<String> sortParams) {

        var sortObjects = PageableUtil.parseSortParams(sortParams);
        Page<CategoryResponse> pageResult = categoryService.getAllCategoriesPage(page, size, sortObjects);
        PagedResponse<CategoryResponse> resp = PageableUtil.toPagedResponse(pageResult);
        return ResponseEntity.ok(resp);
    }
}
