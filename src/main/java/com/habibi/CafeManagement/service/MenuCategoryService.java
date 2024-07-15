package com.habibi.CafeManagement.service;

import com.habibi.CafeManagement.dto.MenuCategoryDto;
import com.habibi.CafeManagement.entity.MenuCategory;
import com.habibi.CafeManagement.repository.MenuCategoryRepository;
import com.habibi.CafeManagement.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MenuCategoryService {
    @Autowired
    MenuCategoryRepository menuCategoryRepository;

    public ResponseEntity<?> saveMenuCategory(MenuCategoryDto menuCategoryDto) {
        if (menuCategoryDto.getMenuCategory().isBlank()) {
            throw new RuntimeException("Category is empty");
        }
        MenuCategory menuCategory = new MenuCategory();
        menuCategory.setMenuCategory(menuCategoryDto.getMenuCategory().toUpperCase());
        menuCategory.setCreatedDate(new Date());

        MenuCategory savedMenuItem = menuCategoryRepository.save(menuCategory);
        return new ResponseEntity<>(savedMenuItem, HttpStatus.CREATED);
    }

    public  MenuCategory getMenuCategoryById(Integer menuCategoryId) {
        return menuCategoryRepository.findById(menuCategoryId).get();
    }

    public List<MenuCategory> getAllMenuCategory() {
        return menuCategoryRepository.findAll();
    }

    public void deleteMenucategory(Integer menucategoryId) {
        menuCategoryRepository.deleteById(menucategoryId);
    }

    public MenuCategory updateMenuCategory(Integer menuCategoryId, MenuCategoryDto menuCategoryDto) {
        MenuCategory menuCategory = menuCategoryRepository.findById(menuCategoryId)
                .orElseThrow(() -> new RuntimeException("Menu Category id not found for " + menuCategoryId));

        menuCategory.setMenuCategory(menuCategoryDto.getMenuCategory().toUpperCase());
        Date currentDate = Utility.localDateWithTime();
        menuCategory.setUpdatedDate(currentDate);
        MenuCategory updatedMenuCategory = menuCategoryRepository.save(menuCategory);

        return updatedMenuCategory;
    }
}
