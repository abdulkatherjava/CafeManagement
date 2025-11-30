package com.habibi.cafemanagement.service;

import com.habibi.cafemanagement.dto.MenuItemRequest;
import com.habibi.cafemanagement.dto.MenuItemResponse;
import com.habibi.cafemanagement.dto.PageAndSortRequest;
import com.habibi.cafemanagement.dto.PagedResponse;

import java.util.List;

public interface MenuItemService {

    MenuItemResponse createMenuItem(MenuItemRequest request);

    MenuItemResponse updateMenuItem(Long id, MenuItemRequest request);

    List<MenuItemResponse> getAllMenuItems();

    MenuItemResponse getMenuItemById(Long id);

    void deleteMenuItemById(Long id);

    List<MenuItemResponse> searchMenuItemsByName(String namePart);

    PagedResponse<MenuItemResponse> getAllMenuItems(PageAndSortRequest request);
}
