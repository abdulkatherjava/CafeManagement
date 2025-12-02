package com.habibi.cafemanagement.service;

import com.habibi.cafemanagement.common.PageableUtil;
import com.habibi.cafemanagement.dto.MenuItemRequest;
import com.habibi.cafemanagement.dto.MenuItemResponse;
import com.habibi.cafemanagement.dto.SortRequest;
import com.habibi.cafemanagement.exception.ResourceNotFoundException;
import com.habibi.cafemanagement.model.Category;
import com.habibi.cafemanagement.model.MenuItem;
import com.habibi.cafemanagement.repository.CategoryRepository;
import com.habibi.cafemanagement.repository.MenuItemRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MenuItemServiceImpl implements MenuItemService {

    private final MenuItemRepository menuItemRepository;
    private final CategoryRepository categoryRepository;

    // ✅ Prefer constructor injection
    public MenuItemServiceImpl(MenuItemRepository menuItemRepository, CategoryRepository categoryRepository) {
        this.menuItemRepository = menuItemRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    @Override
    public MenuItemResponse createMenuItem(MenuItemRequest request) {
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Category not found with id: " + request.getCategoryId()));

        MenuItem menuItem = new MenuItem();
        menuItem.setName(request.getName());
        menuItem.setDescription(request.getDescription());
        menuItem.setPrice(request.getPrice());
        menuItem.setBarcode(request.getBarcode());
        menuItem.setImagePath(request.getImagePath());
        menuItem.setAvailable(request.getIsAvailable());
        menuItem.setCategory(category);

        MenuItem saved = menuItemRepository.save(menuItem);
        return mapToResponse(saved);
    }

    @Transactional
    @Override
    public MenuItemResponse updateMenuItem(Long id, MenuItemRequest request) {
        MenuItem menuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("MenuItem not found with id: %s and name: %s", id, request.getName())));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Category not found with id: " + request.getCategoryId()));

        menuItem.setName(request.getName());
        menuItem.setDescription(request.getDescription());
        menuItem.setPrice(request.getPrice());
        menuItem.setBarcode(request.getBarcode());
        menuItem.setImagePath(request.getImagePath());
        menuItem.setAvailable(request.getIsAvailable());
        menuItem.setCategory(category);

        MenuItem updated = menuItemRepository.save(menuItem);
        return mapToResponse(updated);
    }

    @Override
    public List<MenuItemResponse> getAllMenuItems() {
        return Optional.ofNullable(menuItemRepository.findAll())
                .orElse(Collections.emptyList())
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public MenuItemResponse getMenuItemById(Long id) {
        MenuItem menuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MenuItem not found with id: " + id));
        return mapToResponse(menuItem);
    }

    @Transactional
    @Override
    public void deleteMenuItemById(Long id) {
        MenuItem menuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MenuItem not found with id: " + id));
        menuItemRepository.delete(menuItem);
    }

    @Override
    public List<MenuItemResponse> searchMenuItemsByName(String namePart) {
        List<MenuItem> items = menuItemRepository.findByNameContainingIgnoreCase(namePart);

        if (items.isEmpty()) {
            throw new ResourceNotFoundException("No menu items found matching: " + namePart);
        }

        return items.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ✅ Get paginated menu items with sorting
    @Override
    public Page<MenuItemResponse> getAllMenuItemsPage(int page, int size, List<SortRequest> sortObjects) {
        try {
            Pageable pageable = PageableUtil.toPageable(page, size, sortObjects);
            Page<MenuItem> itemPage = menuItemRepository.findAll(pageable);
            return itemPage.map(this::mapToResponse);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch paginated/sorted menu items: " + e.getMessage(), e);
        }
    }

    // --- helpers ---

    private MenuItemResponse mapToResponse(MenuItem menuItem) {
        MenuItemResponse response = new MenuItemResponse();
        response.setId(menuItem.getId());
        response.setName(menuItem.getName());
        response.setDescription(menuItem.getDescription());
        response.setPrice(menuItem.getPrice());
        response.setBarcode(menuItem.getBarcode());
        response.setImagePath(menuItem.getImagePath());
        response.setIsAvailable(menuItem.getAvailable());
        response.setCategoryId(menuItem.getCategory().getId());
        response.setCategoryName(menuItem.getCategory().getCategoryName());
        response.setCreatedAt(menuItem.getCreatedAt());
        response.setUpdatedAt(menuItem.getUpdatedAt());
        return response;
    }
}
