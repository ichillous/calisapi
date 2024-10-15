package com.calis100.CalisAPI.controller;

import com.calis100.CalisAPI.model.Challenge;
import com.calis100.CalisAPI.model.Log;
import com.calis100.CalisAPI.model.User;
import com.calis100.CalisAPI.service.ChallengeService;
import com.calis100.CalisAPI.service.LogService;
import com.calis100.CalisAPI.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {
    @Autowired
    private UserService userService;

    @Autowired
    private ChallengeService challengeService;

    @Autowired
    private LogService logService;

    @GetMapping("/dashboard")
    public String showDashboard(Authentication authentication, Model model) {
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        Challenge activeChallenge = challengeService.getActiveChallenge(user);
        List<Log> logs = new ArrayList<>();

        if (activeChallenge != null) {
            logs = logService.getLogsByChallenge(activeChallenge);
        }

        model.addAttribute("user", user);
        model.addAttribute("challenge", activeChallenge);
        model.addAttribute("logs", logs);
        return "dashboard";
    }
}
