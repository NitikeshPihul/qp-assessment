package com.qp.groceryapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GroceryItemsResponseDto {
    private String message;
    private List<GroceryItemDto> groceryItemDtoList;
}
