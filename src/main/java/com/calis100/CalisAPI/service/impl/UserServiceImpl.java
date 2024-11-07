package com.calis100.CalisAPI.service.impl;



import com.calis100.CalisAPI.model.Challenge;
import com.calis100.CalisAPI.model.User;
import com.calis100.CalisAPI.model.enums.Role;
import com.calis100.CalisAPI.model.enums.Status;
import com.calis100.CalisAPI.repository.ChallengeRepository;
import com.calis100.CalisAPI.repository.UserRepository;
import com.calis100.CalisAPI.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ChallengeRepository challengeRepository;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, ChallengeRepository challengeRepository) {
        this.userRepository = userRepository;
        this.challengeRepository = challengeRepository;
    }

    @Override
    public User saveUser(User user) {
        user.setRole(Role.USER);
        user.setLastLogin(new Date());
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
    // Calculate completed challenges
    @Override
    public int countCompletedChallenges(User user) {
        int count = 0;
        // challenge prop needed.
        List<Challenge> list = user.getChallenges();
        for (Challenge challenge : list) {
            if (challenge.getStatus() == Status.Completed)
                count++;
        }
        return count;
    }

    // Calculate failed challenges
    @Override
    public int countFailedChallenges(User user) {
        int count = 0;
        // challenge prop needed.
        List<Challenge> list = user.getChallenges();
        for (Challenge challenge : list) {
            if (challenge.getStatus() == Status.Failed)
                count++;
        }
        return count;
    }

    // Calculate active challenges
    @Override
    public int countActiveChallenges(User user) {
        int count = 0;
        // challenge prop needed.
        List<Challenge> challengeList = user.getChallenges();
        if (!challengeList.isEmpty()) {
            for (Challenge c: challengeList) {
                if(c.getStatus() == Status.Active) {
                    count += 1;
                }
            }
        }
        return count;
    }
}
