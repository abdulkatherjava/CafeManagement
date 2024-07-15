package com.habibi.CafeManagement.repository;

import com.habibi.CafeManagement.entity.MenuCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuCategoryRepository extends JpaRepository<MenuCategory, Integer> {
}
