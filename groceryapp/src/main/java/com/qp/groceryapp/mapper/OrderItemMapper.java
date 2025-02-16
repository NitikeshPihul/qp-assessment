package com.qp.groceryapp.mapper;

import com.qp.groceryapp.dto.OrderItemResponseDto;
import com.qp.groceryapp.entity.OrderItem;

public class OrderItemMapper {

    public static OrderItemResponseDto mapToOrderItemResponseDto(OrderItem orderItem){
        OrderItemResponseDto orderItemResponseDto = new OrderItemResponseDto();
        orderItemResponseDto.setGroceryItemName(orderItem.getGroceryItem().getName());
        orderItemResponseDto.setQuantity(orderItem.getQuantity());
        orderItemResponseDto.setPrice(orderItem.getPrice());
        return  orderItemResponseDto;
    }
}
