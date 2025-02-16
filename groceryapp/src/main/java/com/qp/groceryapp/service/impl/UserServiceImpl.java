package com.qp.groceryapp.service.impl;

import com.qp.groceryapp.dto.UserDto;
import com.qp.groceryapp.entity.User;
import com.qp.groceryapp.exception.UserAlreadyExistException;
import com.qp.groceryapp.mapper.UserMapper;
import com.qp.groceryapp.repository.UserRepository;
import com.qp.groceryapp.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.qp.groceryapp.constants.GroceryAppConstants.USER_ALREADY_EXISTS;

@Service
@AllArgsConstructor
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Creates a new user in the system after checking for existing users.
     *
     * @param userDto Data Transfer Object containing user details.
     * @throws UserAlreadyExistException if a user with the given email already exists.
     */
    @Override
    public void createUser(UserDto userDto) {
        // Check if a user with the given email already exists
        Optional<User> storedUser = userRepository.findByEmail(userDto.getEmail());
        if (storedUser.isPresent()) {
            throw new UserAlreadyExistException(USER_ALREADY_EXISTS + userDto.getEmail());
        }

        // Convert DTO to entity and encode the password before saving
        User user = UserMapper.mapToUser(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword())); // Encrypt password
        userRepository.save(user); // Save user to the database
    }

    /**
     * Retrieves all users from the system.
     *
     * @return List of UserDto containing user details.
     */
    @Override
    public List<UserDto> getAllUser() {
        List<User> userList = userRepository.findAll(); // Fetch all users
        return userList.stream().map(UserMapper::mapToUserDto).toList(); // Convert to DTOs and return
    }

    /**
     * Deletes a user from the system based on the provided user ID.
     *
     * @param id The ID of the user to be deleted.
     */
    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id); // Delete user by ID
    }
}