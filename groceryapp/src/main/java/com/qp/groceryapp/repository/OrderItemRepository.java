package com.qp.groceryapp.repository;

import com.qp.groceryapp.entity.OrderItem;
import com.qp.groceryapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
