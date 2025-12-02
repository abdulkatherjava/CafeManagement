package com.habibi.cafemanagement.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CategoryRequest {

    @NotNull(message = "Category name must not be empty")
    @NotBlank(message = "Category name must not be blank")
    @Size(min = 4, message = "Category name must be at least 4 characters long")
    private String categoryName;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
