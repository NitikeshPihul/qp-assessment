package com.qp.groceryapp.dto;

import com.qp.groceryapp.entity.GroceryItem;
import lombok.Data;

@Data
public class InventoryDto {
    private Long groceryItemId;
    private Integer stockQuantity;
    private GroceryItem groceryItem;
}
