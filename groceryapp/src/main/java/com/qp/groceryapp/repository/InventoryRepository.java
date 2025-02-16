package com.qp.groceryapp.repository;

import com.qp.groceryapp.entity.GroceryItem;
import com.qp.groceryapp.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
     void deleteByGroceryItemId(Long groceryItemId);
     Inventory findByGroceryItem(GroceryItem groceryItem);
}
