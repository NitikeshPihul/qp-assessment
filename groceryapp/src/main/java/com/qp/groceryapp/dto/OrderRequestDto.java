package com.qp.groceryapp.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequestDto {
    private List<OrderItemRequestDto> items;
}
