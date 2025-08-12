package com.habibi.cafemanagement.repository;

import com.habibi.cafemanagement.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByCategoryName(String name);
    List<Category> findByCategoryNameContainingIgnoreCase(String namePart);
    Page<Category> findByCategoryNameContainingIgnoreCase(String namePart, Pageable pageable);
}
