package com.habibi.cafemanagement.controller;

import com.habibi.cafemanagement.common.PageableUtil;
import com.habibi.cafemanagement.dto.menuitem.MenuItemRequest;
import com.habibi.cafemanagement.dto.menuitem.MenuItemResponse;
import com.habibi.cafemanagement.dto.common.PageAndSortRequest;
import com.habibi.cafemanagement.dto.common.PagedResponse;
import com.habibi.cafemanagement.service.menuitem.MenuItemService;
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

    // ✅ Get All Menu Items with Pagination & Sorting (POST with JSON body)
    @PostMapping("/menu-items/list")
    public ResponseEntity<PagedResponse<MenuItemResponse>> getAllMenuItems(@RequestBody PageAndSortRequest request) {
        Page<MenuItemResponse> page = menuItemService.getAllMenuItemsPage(
                request.getPage(),
                request.getSize(),
                request.getSortObjects());
        PagedResponse<MenuItemResponse> resp = PageableUtil.toPagedResponse(page);
        return ResponseEntity.status(HttpStatus.OK).body(resp);
    }

    // ✅ Get All Menu Items with Pagination & Sorting (GET with query params - cache
    // friendly)
    @GetMapping("/menu-items")
    public ResponseEntity<PagedResponse<MenuItemResponse>> getAllMenuItemsQueryParams(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", required = false) List<String> sortParams) {

        var sortObjects = PageableUtil.parseSortParams(sortParams);
        Page<MenuItemResponse> pageResult = menuItemService.getAllMenuItemsPage(page, size, sortObjects);
        PagedResponse<MenuItemResponse> resp = PageableUtil.toPagedResponse(pageResult);
        return ResponseEntity.ok(resp);
    }

    // ✅ Search Menu Items by Name (like searchCategoriesByName)
    @GetMapping("/menu-items/search")
    public ResponseEntity<List<MenuItemResponse>> searchMenuItemsByName(@RequestParam("name") String namePart) {
        List<MenuItemResponse> result = menuItemService.searchMenuItemsByName(namePart);
        return ResponseEntity.status(HttpStatus.OK).body(result);
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
}
