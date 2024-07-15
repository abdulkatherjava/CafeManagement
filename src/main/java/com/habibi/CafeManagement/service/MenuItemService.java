package com.habibi.CafeManagement.service;

import com.habibi.CafeManagement.dto.MenuItemDto;
import com.habibi.CafeManagement.dto.MenuItemUpdateStatusDto;
import com.habibi.CafeManagement.entity.MenuCategory;
import com.habibi.CafeManagement.entity.MenuItem;
import com.habibi.CafeManagement.repository.MenuCategoryRepository;
import com.habibi.CafeManagement.repository.MenuItemRepository;
import com.habibi.CafeManagement.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MenuItemService {
    @Autowired
    MenuItemRepository menuItemRepository;

    @Autowired
    MenuCategoryRepository menuCategoryRepository;

    public ResponseEntity<?> saveMenuItem(MenuItemDto menuItemDto) {

        MenuCategory menuCategory = menuCategoryRepository.findById(menuItemDto.getMenuItemCategoryId()).get();
        MenuItem menuItem = new MenuItem();

        menuItem.setMenuItemName(menuItemDto.getMenuItemName());
        menuItem.setMenuItemDescription(menuItemDto.getMenuItemDescription());
        menuItem.setMenuItemPrice(menuItemDto.getMenuItemPrice());
        menuItem.setMenuCategory(menuCategory);
        menuItem.setBarCode(menuItemDto.getBarCode());
        menuItem.setItemQuantity(menuItemDto.getItemQuantity());
        menuItem.setMenuItemSrcPath(menuItemDto.getMenuItemSrcPath());
        menuItem.setIsAvailable(menuItemDto.getIsAvailable());
        menuItem.setCreatedDate(new Date());

        MenuItem savedMenuItem = menuItemRepository.save(menuItem);
        return new ResponseEntity<>(savedMenuItem, HttpStatus.CREATED);
    }

    public  MenuItem getMenuItemBy(Integer menuItemId) {
        return menuItemRepository.findById(menuItemId).get();
    }

    public List<MenuItem> getAllMenuItem() {
        return menuItemRepository.findAll();
    }


    public void deleteMenuItem(Integer orderId) {
        menuItemRepository.deleteById(orderId);
    }
    public MenuItem updateMenuItem(Integer menuItemId, MenuItemDto menuItemDto) {
        MenuItem menuItem = menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new RuntimeException("Menu Item not found for the given Menu Item Id" + menuItemId));

        menuItem.setMenuItemName(menuItemDto.getMenuItemName());
        menuItem.setIsAvailable(menuItemDto.getIsAvailable());
        menuItem.setMenuItemSrcPath(menuItemDto.getMenuItemSrcPath());
        menuItem.setMenuItemDescription(menuItemDto.getMenuItemDescription());
        menuItem.setBarCode(menuItemDto.getBarCode());
        menuItem.setItemQuantity(menuItemDto.getItemQuantity());
        menuItem.setMenuItemPrice(menuItemDto.getMenuItemPrice());
        Date currentDate = Utility.localDateWithTime();
        menuItem.setUpdatedDate(currentDate);
        MenuItem updatedMenuItem = menuItemRepository.save(menuItem);

        return updatedMenuItem;
    }

    public MenuItem updateMenuItemStatus(Integer menuItemId, MenuItemUpdateStatusDto menuItemUpdateStatusDto) {
        MenuItem menuItem = menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new RuntimeException("Menu Item not found for the given Menu Item Id" + menuItemId));

        // Set only the properties that need to be updated
        menuItem.setIsAvailable(menuItemUpdateStatusDto.getAvailableStatus());

        // Set the updated date
        Date currentDate = Utility.localDateWithTime();
        menuItem.setUpdatedDate(currentDate);

        // Save the updated MenuItem
        MenuItem updatedMenuItem = menuItemRepository.save(menuItem);

        return updatedMenuItem;
    }
}
