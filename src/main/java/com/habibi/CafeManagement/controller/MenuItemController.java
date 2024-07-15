package com.habibi.CafeManagement.controller;

import com.habibi.CafeManagement.dto.MenuCategoryDto;
import com.habibi.CafeManagement.dto.MenuItemDto;
import com.habibi.CafeManagement.dto.MenuItemUpdateStatusDto;
import com.habibi.CafeManagement.entity.MenuCategory;
import com.habibi.CafeManagement.entity.MenuItem;
import com.habibi.CafeManagement.service.MenuItemService;
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
    MenuItemService menuItemService;

    @PostMapping("/item")
    public ResponseEntity<?> saveMenuItem(@Valid @RequestBody MenuItemDto menuItemDto) {
        return menuItemService.saveMenuItem(menuItemDto);
    }

    @GetMapping("/item/{menuItemId}")
    public MenuItem getMenuItemById(@PathVariable Integer menuItemId) {
        return menuItemService.getMenuItemBy(menuItemId);
    }

    @GetMapping("/item")
    public List<MenuItem> getAllMenuItem() {
        return menuItemService.getAllMenuItem();
    }

    @DeleteMapping("/item/{menuItemId}")
    public ResponseEntity<Void> deleteMenuItem(@PathVariable Integer menuItemId) {
        menuItemService.deleteMenuItem(menuItemId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/item/{menuItemId}")
    public ResponseEntity<MenuItem> updateMenuItem(@PathVariable Integer menuItemId, @Valid @RequestBody MenuItemDto menuItemDto) {
        MenuItem updateMenuItem = menuItemService.updateMenuItem(menuItemId, menuItemDto);
        return new ResponseEntity<>(updateMenuItem, HttpStatus.OK);
    }
    @PutMapping("/update_item_status/{menuItemId}")
    public ResponseEntity<MenuItem> updateMenuItem(@PathVariable Integer menuItemId, @Valid @RequestBody MenuItemUpdateStatusDto menuItemUpdateStatusDto) {
        MenuItem updateMenuItem = menuItemService.updateMenuItemStatus(menuItemId, menuItemUpdateStatusDto);
        return new ResponseEntity<>(updateMenuItem, HttpStatus.OK);
    }
}
