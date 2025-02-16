package com.qp.groceryapp.service.impl;

import com.qp.groceryapp.constants.GroceryAppConstants;
import com.qp.groceryapp.dto.GroceryItemDto;
import com.qp.groceryapp.dto.InventoryDto;
import com.qp.groceryapp.dto.NewGroceryItemDto;
import com.qp.groceryapp.entity.GroceryItem;
import com.qp.groceryapp.entity.Inventory;
import com.qp.groceryapp.exception.GroceryItemAlreadyExistsException;
import com.qp.groceryapp.exception.ResourceNotFoundException;
import com.qp.groceryapp.mapper.GroceryItemMapper;
import com.qp.groceryapp.repository.GroceryItemRepository;
import com.qp.groceryapp.repository.InventoryRepository;
import com.qp.groceryapp.service.IGroceryItemService;
import com.qp.groceryapp.service.IInventoryService;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.qp.groceryapp.constants.GroceryAppConstants.*;

@AllArgsConstructor
@Service
public class GroceryItemServiceImpl implements IGroceryItemService {

    private GroceryItemRepository groceryItemRepository;
    private InventoryRepository inventoryRepository;
    private IInventoryService InventoryServiceImpl;

    private static final Logger logger = LoggerFactory.getLogger(GroceryItemServiceImpl.class);


    /**
     * Adds a new grocery item to the database. If the item already exists, throws an exception.
     * Also creates a corresponding inventory record.
     *
     * @param groceryItemDto DTO containing grocery item details
     * @throws GroceryItemAlreadyExistsException if the grocery item already exists
     */
    @Override
    public void addGroceryItem(NewGroceryItemDto groceryItemDto) {
        // Check if the grocery item already exists in the database
        Optional<GroceryItem> storedGroceryItem = groceryItemRepository.findByName(groceryItemDto.getName());
        logger.info(CHECK_ITEM_ALREADY_EXIST);
        if(storedGroceryItem.isPresent()){
            throw new GroceryItemAlreadyExistsException(GROCERY_ITEM_ALREADY_EXISTS);
        }

        logger.info(CREATE_NEW_GROCERY_ITEM);
        // Convert DTO to entity and save the new grocery item
        GroceryItem groceryItem = GroceryItemMapper.mapToGroceryItem(groceryItemDto);
        GroceryItem savedGroceryItem = groceryItemRepository.save(groceryItem);

        // Create an inventory entry for the new grocery item
        logger.info(CREATE_INVENTORY_ITEM);
        InventoryDto inventoryDto = new InventoryDto();
        inventoryDto.setGroceryItem(savedGroceryItem);
        inventoryDto.setStockQuantity(groceryItemDto.getInventory()!=null?
                groceryItemDto.getInventory().getStockQuantity()!=null?
                        groceryItemDto.getInventory().getStockQuantity():
                        GroceryAppConstants.ZERO:GroceryAppConstants.ZERO);
        // Call the inventory service to create the inventory record
        InventoryServiceImpl.createInventoryForNewItem(inventoryDto);
    }

    /**
     * Retrieves a list of all grocery items in the system.
     *
     * @return List of grocery item DTOs
     */
    @Override
    public List<GroceryItemDto> findAllGroceryItems() {
        logger.info(FETCH_ITEMS);
        // Fetch all grocery items from the database
        List<GroceryItem> storedGroceryItemList = groceryItemRepository.findAll();
        return storedGroceryItemList.stream().map(GroceryItemMapper::mapToGroceryItemDto).toList();
    }

    /**
     * Deletes a grocery item based on its name.
     *
     * @param name Name of the grocery item to delete
     * @return true if deletion is successful
     * @throws ResourceNotFoundException if the grocery item is not found
     */
    @Override
    @Transactional
    public boolean deleteGroceryItem(String name) {
        logger.info(DELETE_GROCERY_ITEM);
        // Fetch the grocery item; throw an exception if not found
        GroceryItem storedGroceryItem = groceryItemRepository.findByName(name).orElseThrow(
                ()-> new ResourceNotFoundException("Grocery Item","Name", name)
        );
        // Delete the grocery item by its ID
        groceryItemRepository.deleteById(storedGroceryItem.getId());
        return true;
    }

    /**
     * Updates the details of an existing grocery item.
     *
     * @param groceryItemDto DTO containing updated details of the grocery item
     * @return true if the update is successful
     * @throws ResourceNotFoundException if the grocery item is not found
     */
    @Override
    public boolean updateGroceryItemDetails(GroceryItemDto groceryItemDto) {
        // Fetch the grocery item by ID; throw an exception if not found
        logger.info(UPDATE_GROCERY_ITEM);
        GroceryItem storedGroceryItem = groceryItemRepository.findById(groceryItemDto.getId())
                .orElseThrow(
                ()-> new ResourceNotFoundException("Grocery Item","Name", groceryItemDto.getName())
        );

        // Update grocery item details if values are provided in the DTO
        if(groceryItemDto.getName()!=null)
            storedGroceryItem.setName(groceryItemDto.getName());
        if(groceryItemDto.getDescription()!=null)
            storedGroceryItem.setDescription(groceryItemDto.getDescription());
        if(groceryItemDto.getPrice()!=null)
            storedGroceryItem.setPrice(groceryItemDto.getPrice());

        // Save the updated grocery item entity
        groceryItemRepository.save(storedGroceryItem);
        return true;
    }


}
