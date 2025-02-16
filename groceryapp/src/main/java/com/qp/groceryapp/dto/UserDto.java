package com.qp.groceryapp.dto;

import com.qp.groceryapp.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class UserDto {
    private String name;
    private String email;
    private String password;
    private User.Role role;
}
