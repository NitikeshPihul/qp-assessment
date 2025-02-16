package com.qp.groceryapp.dto;

import lombok.Data;

@Data
public class OrderItemResponseDto {
    private String groceryItemName;
    private Integer quantity;
    private Double price;
}
