package com.qp.groceryapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class InventoryResponseDto {
    private Long inventoryId;
    private String groceryItemName;
    private Integer stockQuantity;
}
