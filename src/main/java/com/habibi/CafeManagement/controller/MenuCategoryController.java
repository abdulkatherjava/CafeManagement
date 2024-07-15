package com.habibi.CafeManagement.controller;

import com.habibi.CafeManagement.dto.MenuCategoryDto;
import com.habibi.CafeManagement.dto.OrderDto;
import com.habibi.CafeManagement.entity.MenuCategory;
import com.habibi.CafeManagement.entity.Order;
import com.habibi.CafeManagement.service.MenuCategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@Validated
public class MenuCategoryController {

    @Autowired
    MenuCategoryService menuCategoryService;

    @PostMapping("/category")
    public ResponseEntity<?> saveMenuCategory(@Valid @RequestBody MenuCategoryDto menucategoryDto) {
        return menuCategoryService.saveMenuCategory(menucategoryDto);
    }

    @GetMapping("/category/{menuCategoryId}")
    public MenuCategory getMenuCategoryById(@PathVariable Integer menuCategoryId) {
        return menuCategoryService.getMenuCategoryById(menuCategoryId);
    }

    @GetMapping("/category")
    public List<MenuCategory> getAllMenuCategory() {
        return menuCategoryService.getAllMenuCategory();
    }

    @DeleteMapping("/category/{menuCategoryId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Integer menuCategoryId) {
        menuCategoryService.deleteMenucategory(menuCategoryId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/category/{menuCategoryId}")
    public ResponseEntity<MenuCategory> updateMenuCategory(@PathVariable Integer menuCategoryId, @Valid @RequestBody MenuCategoryDto menuCategoryDto) {
        MenuCategory updateMenuCategory = menuCategoryService.updateMenuCategory(menuCategoryId, menuCategoryDto);
        return new ResponseEntity<>(updateMenuCategory, HttpStatus.OK);
    }
}
