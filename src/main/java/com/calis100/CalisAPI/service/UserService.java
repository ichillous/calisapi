package com.calis100.CalisAPI.service;


import com.calis100.CalisAPI.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Date;
import java.util.List;


public interface UserService {
    User saveUser(User user);
    User findByEmail(String email);
    User findByUsername(String username);
    boolean userExistsByEmail(String email);
    boolean userExistsByUsername(String username);

    List<User> findAll();
    User findById(Long id);
    void deleteUser(Long id);
    void deleteByUsername(String username);

    // New methods for challenge statistics
    int countCompletedChallenges(User user);
    int countFailedChallenges(User user);
    int countActiveChallenges(User user);
}
