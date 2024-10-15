package com.calis100.CalisAPI.controller;

import com.calis100.CalisAPI.model.Challenge;
import com.calis100.CalisAPI.model.User;
import com.calis100.CalisAPI.model.enums.Status;
import com.calis100.CalisAPI.service.ChallengeService;
import com.calis100.CalisAPI.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/challenges")
public class ChallengeController {
    private final ChallengeService challengeService;
    private final UserService userService;

    @Autowired
    public ChallengeController(ChallengeService challengeService, UserService userService) {
        this.challengeService = challengeService;
        this.userService = userService;
    }

    // Accept a new challenge
    @PostMapping("/accept")
    public Challenge acceptChallenge(Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findByUsername(username);

        // Check if user already has an active challenge
        Challenge existingChallenge = challengeService.getActiveChallenge(user);
        if (existingChallenge != null) {
            // Handle accordingly, e.g., throw an exception or return a message
            throw new RuntimeException("You already have an active challenge.");
        }

        // Create new challenge
        Challenge challenge = new Challenge();
        challenge.setUser(user);
        challenge.setStartDate(new Date());
        challenge.setCurrentDay(1);
        challenge.setStatus(Status.ACTIVE);

        return challengeService.saveChallenge(challenge);
    }

    // Get active challenge
    @GetMapping("/active")
    public Challenge getActiveChallenge(Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findByUsername(username);

        return challengeService.getActiveChallenge(user);
    }

    // Get all challenges for a user
    @GetMapping
    public List<Challenge> getChallenges(Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findByUsername(username);

        return challengeService.getChallengesByUser(user);
    }
}
