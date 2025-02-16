package com.qp.groceryapp.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
@Data
public class NewGroceryItemDto {
        private Long id;

        @NotEmpty(message = "Grocery item name can not be null or empty")
        private String name;

        @NotEmpty(message = "Grocery item description can not be null or empty")
        private String description;

        @NotNull(message = "Grocery item price can not be null or empty")
        private Double price;

        private NewInventoryItemDto inventory;
}
