package com.qp.groceryapp.controller;

import com.qp.groceryapp.constants.GroceryAppConstants;
import com.qp.groceryapp.dto.ResponseDto;
import com.qp.groceryapp.dto.UserDto;
import com.qp.groceryapp.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * PublicController handles user registration-related operations.
 * This controller is accessible without authentication.
 */
@RestController
@RequestMapping
@AllArgsConstructor
@Tag(name = "Public Controller", description = "Endpoints accessible without authentication (e.g., user registration)")
public class PublicController {

    private final IUserService userService;

    /**
     * Registers a new user in the system.
     *
     * @param userDto The DTO containing user registration details.
     * @return ResponseEntity with a success message and HTTP status 201 (Created).
     */
    @Operation(
            summary = "Register a new user",
            description = "Creates a new user in the system. This endpoint is accessible without authentication."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "400", description = "User already exists")
    })
    @PostMapping("/register")
    public ResponseEntity<ResponseDto> createUser(@RequestBody UserDto userDto) {
        userService.createUser(userDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(GroceryAppConstants.STATUS_201, GroceryAppConstants.USER_MESSAGE_201));
    }
}
