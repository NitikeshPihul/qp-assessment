package com.qp.groceryapp.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateInventoryRequestDto {

    @NotEmpty(message = "Grocery item name can not be null or empty")
    private String groceryItemName;

    @NotNull(message = "Stock quantity can not be null or empty")
    private Integer stockQuantity;
}
