package com.habibi.cafemanagement.service;

import com.habibi.cafemanagement.dto.MenuItemRequest;
import com.habibi.cafemanagement.dto.MenuItemResponse;

import java.util.List;

public interface MenuItemService {

    MenuItemResponse createMenuItem(MenuItemRequest request);

    MenuItemResponse updateMenuItem(Long id, MenuItemRequest request);

    List<MenuItemResponse> getAllMenuItems();

    MenuItemResponse getMenuItemById(Long id);

    void deleteMenuItemById(Long id);

    List<MenuItemResponse> searchMenuItemsByName(String namePart);

    org.springframework.data.domain.Page<MenuItemResponse> getAllMenuItemsPage(int page, int size,
            java.util.List<com.habibi.cafemanagement.dto.SortRequest> sortObjects);
}
