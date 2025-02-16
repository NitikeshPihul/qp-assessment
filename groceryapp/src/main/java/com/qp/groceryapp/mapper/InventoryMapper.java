package com.qp.groceryapp.mapper;

import com.qp.groceryapp.dto.InventoryDto;
import com.qp.groceryapp.dto.InventoryResponseDto;
import com.qp.groceryapp.entity.Inventory;

public class InventoryMapper {

    public static Inventory mapToInventory(InventoryDto inventoryDto){
        Inventory inventory = new Inventory();
        inventory.setGroceryItem(inventoryDto.getGroceryItem());
        inventory.setStockQuantity(inventoryDto.getStockQuantity());
        return inventory;
    }

    public static InventoryDto mapToInventoryDto(Inventory inventory){
        InventoryDto inventoryDto = new InventoryDto();
        inventoryDto.setGroceryItem(inventory.getGroceryItem());
        inventoryDto.setStockQuantity(inventory.getStockQuantity());
        inventoryDto.setGroceryItemId(inventory.getId());
        return inventoryDto;
    }
    public static InventoryResponseDto mapToInventoryResponseDto(Inventory inventory){
        return new InventoryResponseDto(
                inventory.getId(), inventory.getGroceryItem().getName(),inventory.getStockQuantity());
    }
}
