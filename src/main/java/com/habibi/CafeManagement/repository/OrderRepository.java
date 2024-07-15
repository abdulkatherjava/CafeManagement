package com.habibi.CafeManagement.repository;

import com.habibi.CafeManagement.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
