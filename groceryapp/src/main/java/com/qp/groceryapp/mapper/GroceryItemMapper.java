package com.qp.groceryapp.mapper;

import com.qp.groceryapp.dto.GroceryItemDto;
import com.qp.groceryapp.dto.NewGroceryItemDto;
import com.qp.groceryapp.entity.GroceryItem;
import com.qp.groceryapp.entity.Inventory;

public class GroceryItemMapper {

    public static GroceryItemDto mapToGroceryItemDto(GroceryItem groceryItem){
        GroceryItemDto groceryItemDto = new GroceryItemDto();
        groceryItemDto.setId(groceryItem.getId());
        groceryItemDto.setDescription(groceryItem.getDescription());
        groceryItemDto.setName(groceryItem.getName());
        groceryItemDto.setPrice(groceryItem.getPrice());
        return groceryItemDto;
    }

    public static GroceryItem mapToGroceryItem(NewGroceryItemDto groceryItemDto){
        GroceryItem groceryItem = new GroceryItem();
        groceryItem.setDescription(groceryItemDto.getDescription());
        groceryItem.setName(groceryItemDto.getName());
        groceryItem.setPrice(groceryItemDto.getPrice());
        return groceryItem;
    }
}
