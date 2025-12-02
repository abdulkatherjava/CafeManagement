package com.habibi.cafemanagement.service.menuitem;

import com.habibi.cafemanagement.dto.menuitem.MenuItemRequest;
import com.habibi.cafemanagement.dto.menuitem.MenuItemResponse;
import com.habibi.cafemanagement.dto.common.SortRequest;
import java.util.List;
import org.springframework.data.domain.Page;

public interface MenuItemService {

    MenuItemResponse createMenuItem(MenuItemRequest request);

    MenuItemResponse updateMenuItem(Long id, MenuItemRequest request);

    List<MenuItemResponse> getAllMenuItems();

    MenuItemResponse getMenuItemById(Long id);

    void deleteMenuItemById(Long id);

    List<MenuItemResponse> searchMenuItemsByName(String namePart);

    Page<MenuItemResponse> getAllMenuItemsPage(int page, int size, List<SortRequest> sortObjects);
}
