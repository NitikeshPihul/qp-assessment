package com.qp.groceryapp.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderResponseDto {
    private Long orderId;
    private String status;
    private List<OrderItemResponseDto> items;
    private Double totalPrice;
}
