package com.qp.groceryapp.service;

import com.qp.groceryapp.dto.GroceryItemDto;
import com.qp.groceryapp.dto.NewGroceryItemDto;

import java.util.List;

public interface IGroceryItemService {
    void addGroceryItem(NewGroceryItemDto groceryItemDto);
    List<GroceryItemDto> findAllGroceryItems();
    boolean deleteGroceryItem(String name);
    boolean updateGroceryItemDetails(GroceryItemDto groceryItemDto);
}
