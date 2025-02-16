package com.qp.groceryapp.service;

import com.qp.groceryapp.dto.InventoryDto;
import com.qp.groceryapp.dto.InventoryResponseDto;
import com.qp.groceryapp.entity.GroceryItem;

import java.util.List;

public interface IInventoryService {
    void createInventoryForNewItem(InventoryDto inventoryDto);
    void deleteInventoryByGroceryItem(GroceryItem groceryItem);
    void updateInventoryDetails(String name, Integer newStockQuantity);
    List<InventoryResponseDto> getAllInventoriesDetails();
}
