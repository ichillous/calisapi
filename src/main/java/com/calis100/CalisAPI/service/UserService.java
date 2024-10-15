package com.calis100.CalisAPI.service;

import com.calis100.CalisAPI.model.User;

import java.util.List;


public interface UserService {
    // Save a new user
    User saveUser(User user);

    // Find a user by email
    User findByEmail(String email);

    // Find a user by username
    User findByUsername(String username);

    // Check if a user exists by email
    boolean userExistsByEmail(String email);

    // Check if a user exists by username
    boolean userExistsByUsername(String username);

    List<User> findAll();

    User findById(Long id);

    void deleteUser(Long id);

    void deleteByUsername(String username);
}
