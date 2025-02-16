package com.qp.groceryapp.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class GroceryItemDto {
    private Long id;
    private String name;
    private String description;
    private Double price;

}
