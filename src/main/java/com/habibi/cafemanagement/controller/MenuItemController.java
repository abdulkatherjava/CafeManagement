package com.habibi.cafemanagement.controller;

import com.habibi.cafemanagement.dto.MenuItemRequest;
import com.habibi.cafemanagement.dto.MenuItemResponse;
import com.habibi.cafemanagement.dto.PageAndSortRequest;
import com.habibi.cafemanagement.service.MenuItemService;
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
public class MenuItemController {

    @Autowired
    private MenuItemService menuItemService;

    // ✅ Create Menu Item
    // This is tested and working fine.
    @PostMapping("/menu-items")
    public ResponseEntity<MenuItemResponse> createMenuItem(@Valid @RequestBody MenuItemRequest request) {
        MenuItemResponse response = menuItemService.createMenuItem(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // ✅ Update Menu Item
    @PutMapping("/menu-items/{id}")
    public ResponseEntity<MenuItemResponse> updateMenuItem(
            @PathVariable Long id,
            @Valid @RequestBody MenuItemRequest request) {
        MenuItemResponse updatedMenuItem = menuItemService.updateMenuItem(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(updatedMenuItem);
    }

    // ✅ Get Menu Item by ID
    @GetMapping("/menu-items/{id}")
    public ResponseEntity<MenuItemResponse> getMenuItemById(@PathVariable Long id) {
        MenuItemResponse response = menuItemService.getMenuItemById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // ✅ Delete Menu Item
    @DeleteMapping("/menu-items/{id}")
    public ResponseEntity<Void> deleteMenuItemById(@PathVariable Long id) {
        menuItemService.deleteMenuItemById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // ✅ Search Menu Items by Name (like searchCategoriesByName)
    @GetMapping("/menu-items/search")
    public ResponseEntity<List<MenuItemResponse>> searchMenuItemsByName(@RequestParam("name") String namePart) {
        List<MenuItemResponse> result = menuItemService.searchMenuItemsByName(namePart);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // ✅ Get All Menu Items with Pagination & Sorting/
    // This is tested and working fine.
    @PostMapping("/menu-item-list")
    public ResponseEntity<List<MenuItemResponse>> getAllMenuItems(@RequestBody PageAndSortRequest request) {
        String[] sortParams = null;
        if (request.getSortObjects() != null && !request.getSortObjects().isEmpty()) {
            sortParams = request.getSortObjects().stream()
                    .map(s -> s.getProperty() + ","
                            + (s.getDirection() != null ? s.getDirection().toLowerCase() : "asc"))
                    .toArray(String[]::new);
        }

        List<MenuItemResponse> menuItems = menuItemService.getAllMenuItems(
                request.getPage(),
                request.getSize(),
                sortParams);
        return ResponseEntity.status(HttpStatus.OK).body(menuItems);
    }
}
