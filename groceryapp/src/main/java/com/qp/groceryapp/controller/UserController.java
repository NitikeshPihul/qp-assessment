package com.qp.groceryapp.controller;

import com.qp.groceryapp.constants.GroceryAppConstants;
import com.qp.groceryapp.dto.*;
import com.qp.groceryapp.service.IGroceryItemService;
import com.qp.groceryapp.service.IOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * UserController handles operations related to grocery items and orders for authenticated users.
 */
@RestController
@RequestMapping("/user")
@AllArgsConstructor
@Tag(name = "User Controller", description = "Endpoints for user-related operations (e.g., placing orders, fetching grocery items)")
public class UserController {

    private final IOrderService orderService;
    private final IGroceryItemService groceryItemService;

    /**
     * Places an order for grocery items.
     *
     * @param orderRequest The DTO containing order details.
     * @return ResponseEntity with the order response details and HTTP status 201 (Created).
     */
    @Operation(
            summary = "Place an order",
            description = "Allows users to place an order for grocery items. Requires a valid list of order items."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order placed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid order request"),
            @ApiResponse(responseCode = "400", description = "Insufficient stock for one or more items")
    })
    @PostMapping("/place-order")
    public ResponseEntity<OrderResponseDto> placeOrder(@RequestBody OrderRequestDto orderRequest) {
        OrderResponseDto response = orderService.placeOrder(orderRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Retrieves all available grocery items.
     *
     * @return ResponseEntity with a list of grocery items and HTTP status 200 (OK) or 204 (No Content) if empty.
     */
    @Operation(
            summary = "Get all grocery items",
            description = "Retrieves a list of all available grocery items in the system."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Grocery items retrieved successfully"),
            @ApiResponse(responseCode = "204", description = "No grocery items available")
    })
    @GetMapping("/getAll/grocery-items")
    public ResponseEntity<GroceryItemsResponseDto> getAllGroceryItems() {
        List<GroceryItemDto> groceryItemDtoList = groceryItemService.findAllGroceryItems();
        return ResponseEntity
                .status((groceryItemDtoList.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK))
                .body(new GroceryItemsResponseDto(GroceryAppConstants.GROCERY_ITEM_MESSAGE_200, groceryItemDtoList));
    }
}
