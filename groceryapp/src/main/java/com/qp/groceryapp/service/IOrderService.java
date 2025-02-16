package com.qp.groceryapp.service;

import com.qp.groceryapp.dto.OrderRequestDto;
import com.qp.groceryapp.dto.OrderResponseDto;

public interface IOrderService {
    OrderResponseDto placeOrder(OrderRequestDto orderRequest);
}
