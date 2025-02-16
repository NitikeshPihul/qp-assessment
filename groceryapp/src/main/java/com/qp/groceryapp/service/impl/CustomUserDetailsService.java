package com.qp.groceryapp.service.impl;

import com.qp.groceryapp.entity.User;
import com.qp.groceryapp.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.List;

import static com.qp.groceryapp.constants.GroceryAppConstants.FETCH_USER_FROM_DB;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {


    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);
    private UserRepository userRepository;

    /**
     * Loads user details based on the provided username.
     *
     * @param username the username of the user to be loaded
     * @return UserDetails containing user's authentication and authorization information
     * @throws UsernameNotFoundException if the user is not found in the database
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info(FETCH_USER_FROM_DB);
        // Fetch user from the database; throw exception if not found
        User user = userRepository.findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Return UserDetails instance with username, password, and assigned role(s)
        return new org.springframework.security.core.userdetails.User(
                user.getName(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority(user.getRole().name())) // Assign role
        );
    }
}
