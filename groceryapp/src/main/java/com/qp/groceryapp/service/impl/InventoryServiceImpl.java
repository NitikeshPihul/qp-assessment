package com.qp.groceryapp.service.impl;

import com.qp.groceryapp.dto.InventoryDto;
import com.qp.groceryapp.dto.InventoryResponseDto;
import com.qp.groceryapp.entity.GroceryItem;
import com.qp.groceryapp.entity.Inventory;
import com.qp.groceryapp.exception.ResourceNotFoundException;
import com.qp.groceryapp.mapper.InventoryMapper;
import com.qp.groceryapp.repository.GroceryItemRepository;
import com.qp.groceryapp.repository.InventoryRepository;
import com.qp.groceryapp.service.IInventoryService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.qp.groceryapp.constants.GroceryAppConstants.*;

@AllArgsConstructor
@Service
public class InventoryServiceImpl implements IInventoryService {

    private static final Logger logger = LoggerFactory.getLogger(GroceryItemServiceImpl.class);

    private GroceryItemRepository groceryItemRepository;
    private InventoryRepository inventoryRepository;

    /**
     * Creates a new inventory entry for a given grocery item.
     *
     * @param inventoryDto DTO containing inventory details
     */
    @Override
    public void createInventoryForNewItem(InventoryDto inventoryDto) {
        logger.info(CREATE_NEW_INVENTORY_ITEM);
        // Convert DTO to entity and save inventory record
        Inventory inventory = InventoryMapper.mapToInventory(inventoryDto);
        inventoryRepository.save(inventory);
    }

    /**
     * Deletes an inventory entry associated with a given grocery item.
     *
     * @param groceryItem The grocery item whose inventory needs to be deleted
     */
    @Override
    public void deleteInventoryByGroceryItem(GroceryItem groceryItem) {
        logger.info(DELETE_INVENTORY_ITEM);
        // Delete inventory entry based on the grocery item ID
        inventoryRepository.deleteByGroceryItemId(groceryItem.getId());
    }


    /**
     * Updates the stock quantity of an inventory item based on the grocery item's name.
     *
     * @param name             Name of the grocery item
     * @param newStockQuantity The new stock quantity to be updated
     * @throws ResourceNotFoundException if the grocery item is not found
     */
    @Override
    public void updateInventoryDetails(String name, Integer newStockQuantity) {
        logger.info(UPDATE_INVENTORY_ITEM);
        // Fetch the grocery item by name, throw exception if not found
        GroceryItem storedGroceryItem = groceryItemRepository.findByName(name).orElseThrow(
                ()-> new ResourceNotFoundException("Grocery Item","Name", name)
        );
        // Fetch the inventory entry associated with the grocery item
          Inventory savedInventory = inventoryRepository.findByGroceryItem(storedGroceryItem);
        // Update the stock quantity
          savedInventory.setStockQuantity(newStockQuantity);
        // Save the updated inventory entry
          inventoryRepository.save(savedInventory);

    }

    /**
     * Retrieves all inventory details from the database.
     *
     * @return List of inventory response DTOs containing inventory details
     */
    @Override
    public List<InventoryResponseDto> getAllInventoriesDetails() {
        logger.info(FETCH_INVENTORY_ITEMS);
        // Fetch all inventory records
        List<Inventory> inventoryList = inventoryRepository.findAll();
        return inventoryList.stream().map(InventoryMapper::mapToInventoryResponseDto).toList();

    }
}
