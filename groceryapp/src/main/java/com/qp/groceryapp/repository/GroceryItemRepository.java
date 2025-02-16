package com.qp.groceryapp.repository;

import com.qp.groceryapp.entity.GroceryItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroceryItemRepository extends JpaRepository<GroceryItem, Long> {
     Optional<GroceryItem> findByName(String name);
}
