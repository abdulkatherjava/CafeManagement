package com.habibi.CafeManagement.repository;

import com.habibi.CafeManagement.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuItemRepository extends JpaRepository<MenuItem, Integer> {
}
