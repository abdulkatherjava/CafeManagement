package com.habibi.cafemanagement.controller;

import com.habibi.cafemanagement.dto.CategoryRequest;
import com.habibi.cafemanagement.dto.CategoryResponse;
import com.habibi.cafemanagement.dto.PageAndSortRequest;
import com.habibi.cafemanagement.dto.PagedResponse;
import com.habibi.cafemanagement.dto.SortInfo;
import com.habibi.cafemanagement.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    // @GetMapping("/categories")
    // public ResponseEntity<List<CategoryResponse>> getAllCategories(
    // @RequestParam(defaultValue = "0") int page,
    // @RequestParam(defaultValue = "10") int size,
    // @RequestParam(defaultValue = "categoryName,asc") String[] sort) {
    // List<CategoryResponse> categories = categoryService.getAllCategories(page,
    // size, sort);
    // return ResponseEntity.status(HttpStatus.OK).body(categories);
    // }

    // ✅ GET All Categories (paged)
    @GetMapping("/categories")
    public ResponseEntity<PagedResponse<CategoryResponse>> getAllCategories(@RequestBody PageAndSortRequest request) {
        // Convert sortObjects to string array if provided
        String[] sortParams = null;
        if (request.getSortObjects() != null && !request.getSortObjects().isEmpty()) {
            sortParams = request.getSortObjects().stream()
                    .map(s -> s.getProperty() + ","
                            + (s.getDirection() != null ? s.getDirection().toLowerCase() : "asc"))
                    .toArray(String[]::new);
        }

        Page<CategoryResponse> page = categoryService.getAllCategoriesPage(
                request.getPage(),
                request.getSize(),
                sortParams);

        PagedResponse<CategoryResponse> resp = new PagedResponse<>();
        resp.setData(page.getContent());
        resp.setPage(page.getNumber());
        resp.setSize(page.getSize());
        resp.setTotalElements(page.getTotalElements());
        resp.setTotalPages(page.getTotalPages());

        // Build sort metadata
        List<SortInfo> sortInfos = new ArrayList<>();
        page.getSort().forEach(order -> sortInfos.add(new SortInfo(order.getProperty(), order.getDirection().name())));
        resp.setSort(sortInfos);

        return ResponseEntity.status(HttpStatus.OK).body(resp);
    }
}
