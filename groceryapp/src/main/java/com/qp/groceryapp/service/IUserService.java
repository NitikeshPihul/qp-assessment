package com.qp.groceryapp.service;

import com.qp.groceryapp.dto.UserDto;

import java.util.List;

public interface IUserService {
    void createUser(UserDto userDto);
    List<UserDto> getAllUser();
    void deleteUser(Long id);

}
