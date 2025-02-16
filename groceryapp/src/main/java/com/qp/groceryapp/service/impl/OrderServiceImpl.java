package com.qp.groceryapp.service.impl;

import com.qp.groceryapp.dto.OrderItemRequestDto;
import com.qp.groceryapp.dto.OrderItemResponseDto;
import com.qp.groceryapp.dto.OrderRequestDto;
import com.qp.groceryapp.dto.OrderResponseDto;

import com.qp.groceryapp.entity.*;
import com.qp.groceryapp.exception.BadRequestException;
import com.qp.groceryapp.exception.InsufficientStockException;
import com.qp.groceryapp.exception.ResourceNotFoundException;
import com.qp.groceryapp.mapper.OrderItemMapper;
import com.qp.groceryapp.repository.*;
import com.qp.groceryapp.service.IOrderService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
public class OrderServiceImpl implements IOrderService {

    private GroceryItemRepository groceryItemRepository;
    private InventoryRepository inventoryRepository;
    private OrderRepository orderRepository;
    private OrderItemRepository orderItemRepository;
    private UserRepository userRepository;


    /**
     * Places an order by validating stock availability, updating inventory,
     * and saving order details.
     *
     * @param orderRequest DTO containing order details, including items and quantities
     * @return OrderResponseDto containing order summary
     * @throws BadRequestException if the order does not contain any items
     * @throws ResourceNotFoundException if a grocery item is not found
     * @throws InsufficientStockException if stock is insufficient for any item
     */
    public OrderResponseDto placeOrder(OrderRequestDto orderRequest){
        // Validate that the order contains at least one item
        if (orderRequest.getItems() == null || orderRequest.getItems().isEmpty()) {
            throw new BadRequestException("Order must contain at least one item");
        }

        List<OrderItemResponseDto> responseItems = new ArrayList<>();
        List<OrderItemRequestDto> orderItemRequestDtoList = orderRequest.getItems();
        List<OrderItem> orderItemList = new ArrayList<>();
        double totalPrice= 0.0;

        // Fetch authenticated user from Security Context
        String username = getAuthenticatedUsername();
        User user = userRepository.findByName(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Username", username));

        // Create a new order with initial status and price
        Order order = new Order();
        order.setOrderStatus(Order.OrderStatus.PLACED);
        order.setTotalPrice(totalPrice);
        order.setUser(user);
        order = orderRepository.save(order);

        // Process each item in the order
        for(OrderItemRequestDto orderItemRequestDto:orderItemRequestDtoList){
            // Fetch grocery item from database
            GroceryItem groceryItem = groceryItemRepository.findByName(orderItemRequestDto.getGroceryItemName())
                    .orElseThrow(()-> new ResourceNotFoundException("Grocery Item","Name", orderItemRequestDto.getGroceryItemName()));

            // Fetch inventory details of the item
            Inventory inventory = inventoryRepository.findByGroceryItem(groceryItem);

            // Check if enough stock is available
            if(inventory.getStockQuantity()< orderItemRequestDto.getQuantity()){
                throw new InsufficientStockException("Not enough stock for " + groceryItem.getName());
            }

            // Deduct purchased quantity from stock
            inventory.setStockQuantity(inventory.getStockQuantity()-orderItemRequestDto.getQuantity());
            inventoryRepository.save(inventory);

            // Calculate price for the current item
            double price = groceryItem.getPrice()*orderItemRequestDto.getQuantity();
            totalPrice+=price;

            // Create an order item and associate it with the order
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setGroceryItem(groceryItem);
            orderItem.setQuantity(orderItemRequestDto.getQuantity());
            orderItem.setPrice(price);
            orderItemRepository.save(orderItem);
            orderItemList.add(orderItem);
        }

        // Update the order with final total price and list of items
        order.setOrderItems(orderItemList);
        order.setTotalPrice(totalPrice);
        orderRepository.save(order);

        // Prepare order response DTO
        OrderResponseDto orderResponseDto = new OrderResponseDto();
        orderResponseDto.setOrderId(order.getId());
        orderResponseDto.setStatus(order.getOrderStatus().toString());
        orderResponseDto.setTotalPrice(totalPrice);
        orderResponseDto.setItems(orderItemList.stream().map(OrderItemMapper::mapToOrderItemResponseDto).toList());
        return orderResponseDto;
    }


    /**
     * Retrieves the currently authenticated username from the Security Context.
     *
     * @return Authenticated username
     */
    private String getAuthenticatedUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }
}
