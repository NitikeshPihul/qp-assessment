package com.qp.groceryapp.mapper;

import com.qp.groceryapp.dto.UserDto;
import com.qp.groceryapp.entity.User;
import com.qp.groceryapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserMapper {




        public static UserDto mapToUserDto(User user){
        UserDto userDto = new UserDto();
        userDto.setEmail(userDto.getEmail());
        userDto.setPassword(userDto.getPassword());
        userDto.setRole(user.getRole());
        userDto.setName(user.getName());
        return userDto;
    }

    public static User mapToUser(UserDto userDto){
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setName(userDto.getName());
        user.setRole(userDto.getRole());
        user.setPassword(userDto.getPassword());
        return user;
    }
}
