package com.qp.groceryapp.dto;

import lombok.Data;

@Data
public class OrderItemRequestDto {
    private String groceryItemName;
    private Integer quantity;
}
