package com.calis100.CalisAPI.service.impl;


import com.calis100.CalisAPI.model.User;
import com.calis100.CalisAPI.model.enums.Role;
import com.calis100.CalisAPI.repository.UserRepository;
import com.calis100.CalisAPI.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User saveUser(User user) {
        // Encode the password before saving
        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        // Set default role if not set
        if (user.getRole() == null) {
            user.setRole(Role.USER);
        }
        return userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public boolean userExistsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean userExistsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void deleteByUsername(String username) {
        userRepository.findByUsername(username).ifPresent(userRepository::delete);
    }
}
