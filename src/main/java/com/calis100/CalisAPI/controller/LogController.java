package com.calis100.CalisAPI.controller;

import com.calis100.CalisAPI.model.Challenge;
import com.calis100.CalisAPI.model.Log;
import com.calis100.CalisAPI.model.User;
import com.calis100.CalisAPI.model.enums.Status;
import com.calis100.CalisAPI.service.ChallengeService;
import com.calis100.CalisAPI.service.LogService;
import com.calis100.CalisAPI.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/logs")
public class LogController {
    @Autowired
    private LogService logService;

    @Autowired
    private ChallengeService challengeService;

    @Autowired
    private UserService userService;

    @Autowired
    public LogController(LogService logService, ChallengeService challengeService, UserService userService) {
        this.logService = logService;
        this.challengeService = challengeService;
        this.userService = userService;
    }

    // Show log form
    @GetMapping("/new")
    public String showLogForm(Model model) {
        model.addAttribute("log", new Log());
        return "log-form";
    }

    // Handle log submission
    @PostMapping
    public String submitLog(@ModelAttribute("log") Log log, Authentication authentication, RedirectAttributes redirectAttributes) {
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        Challenge challenge = challengeService.getActiveChallenge(user);

        if (challenge == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "No active challenge found.");
            return "redirect:/dashboard";
        }

        log.setChallenge(challenge);
        log.setLogDate(new Date());
        logService.saveLog(log);

        // Update challenge day
        challenge.setCurrentDay(challenge.getCurrentDay() + 1);

        // Check if challenge is completed
        if (challenge.getCurrentDay() > 100) {
            challenge.setStatus(Status.COMPLETED);
        }

        challengeService.saveChallenge(challenge);

        redirectAttributes.addFlashAttribute("successMessage", "Log submitted successfully!");
        return "redirect:/dashboard";
    }

    // Get all logs for the active challenge
    @GetMapping
    public List<Log> getLogs(Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        Challenge challenge = challengeService.getActiveChallenge(user);

        if (challenge == null) {
            throw new RuntimeException("No active challenge found.");
        }

        return logService.getLogsByChallenge(challenge);
    }
}
