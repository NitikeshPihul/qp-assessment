package com.qp.groceryapp.controller;

import com.qp.groceryapp.constants.GroceryAppConstants;
import com.qp.groceryapp.dto.*;
import com.qp.groceryapp.entity.Inventory;
import com.qp.groceryapp.service.IGroceryItemService;
import com.qp.groceryapp.service.IInventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing grocery items and inventory in the system.
 */
@RestController
@RequestMapping("/admin")
@AllArgsConstructor
@Tag(name = "Admin Controller", description = "Admin operations for managing grocery items and inventory")
public class AdminController {

    private final IGroceryItemService groceryItemService;
    private final IInventoryService inventoryService;

    /**
     * Adds a new grocery item.
     *
     * @param groceryItemDto The new grocery item data.
     * @return Response entity with status 201 (Created).
     */
    @Operation(summary = "Add a new grocery item", description = "Creates a new grocery item along with its inventory details")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Grocery item created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    @PostMapping("/create/grocery-item")
    public ResponseEntity<ResponseDto> addGroceryItems(@Valid @RequestBody NewGroceryItemDto groceryItemDto) {
        groceryItemService.addGroceryItem(groceryItemDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(GroceryAppConstants.STATUS_201, GroceryAppConstants.GROCERY_ITEM_MESSAGE_201));
    }

    /**
     * Retrieves all grocery items.
     *
     * @return List of grocery items with HTTP status.
     */
    @Operation(summary = "Get all grocery items", description = "Fetches all available grocery items")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of grocery items retrieved successfully"),
            @ApiResponse(responseCode = "204", description = "No grocery items found")
    })
    @GetMapping("/getAll/grocery-items")
    public ResponseEntity<GroceryItemsResponseDto> getAllGroceryItems() {
        List<GroceryItemDto> groceryItemDtoList = groceryItemService.findAllGroceryItems();
        return ResponseEntity
                .status(groceryItemDtoList.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK)
                .body(new GroceryItemsResponseDto(GroceryAppConstants.GROCERY_ITEM_MESSAGE_200, groceryItemDtoList));
    }

    /**
     * Deletes a grocery item by name.
     *
     * @param name The name of the grocery item to delete.
     * @return Response entity with success or failure message.
     */
    @Operation(summary = "Delete a grocery item", description = "Deletes a grocery item by its name")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Grocery item deleted successfully"),
            @ApiResponse(responseCode = "417", description = "Failed to delete grocery item"),
            @ApiResponse(responseCode = "404", description = "Requested grocery item not found")
    })
    @DeleteMapping("/delete/grocery-item/{name}")
    public ResponseEntity<ResponseDto> deleteGroceryItem(@PathVariable String name) {
        boolean isDeleted = groceryItemService.deleteGroceryItem(name);
        if (isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(GroceryAppConstants.STATUS_200, GroceryAppConstants.GROCERY_ITEM_DELETE_SUCCESS));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(GroceryAppConstants.STATUS_417, GroceryAppConstants.MESSAGE_417_DELETE));
        }
    }

    /**
     * Updates an existing grocery item.
     *
     * @param groceryItemDto The grocery item details to update.
     * @return Response entity with update status.
     */
    @Operation(summary = "Update a grocery item", description = "Updates the details of an existing grocery item")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Grocery item updated successfully"),
            @ApiResponse(responseCode = "417", description = "Failed to update grocery item"),
            @ApiResponse(responseCode = "404", description = "Requested grocery item not found")
    })
    @PutMapping("/update/grocery-item")
    public ResponseEntity<ResponseDto> updateGroceryitem(@RequestBody GroceryItemDto groceryItemDto) {
        boolean isUpdated = groceryItemService.updateGroceryItemDetails(groceryItemDto);
        if (isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(GroceryAppConstants.STATUS_200, GroceryAppConstants.GROCERY_ITEM_UPDATE_SUCCESS));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(GroceryAppConstants.STATUS_417, GroceryAppConstants.MESSAGE_417_UPDATE));
        }
    }

    /**
     * Updates inventory details for a grocery item.
     *
     * @param updateInventoryRequestDto The inventory update request.
     * @return Response entity with update status.
     */
    @Operation(summary = "Update inventory details", description = "Updates stock quantity for a grocery item")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Inventory updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid inventory update request"),
            @ApiResponse(responseCode = "404", description = "Requested grocery item not found")
    })
    @PutMapping("/update-inventory")
    public ResponseEntity<ResponseDto> updateInventory(@Valid @RequestBody UpdateInventoryRequestDto updateInventoryRequestDto) {
        inventoryService.updateInventoryDetails(updateInventoryRequestDto.getGroceryItemName(), updateInventoryRequestDto.getStockQuantity());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(GroceryAppConstants.STATUS_200, GroceryAppConstants.INVENTORY_UPDATE_SUCCESS));
    }

    /**
     * Retrieves all inventory details.
     *
     * @return List of inventory details.
     */
    @Operation(summary = "Get all inventory details", description = "Fetches details of all grocery items in inventory")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of inventory details retrieved successfully"),
            @ApiResponse(responseCode = "204", description = "No inventory details found")
    })
    @GetMapping("/getAll/inventory")
    public ResponseEntity<List<InventoryResponseDto>> getAllInventories() {
        List<InventoryResponseDto> inventoryDtoList = inventoryService.getAllInventoriesDetails();
        return ResponseEntity
                .status(inventoryDtoList.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK)
                .body(inventoryDtoList);
    }
}
