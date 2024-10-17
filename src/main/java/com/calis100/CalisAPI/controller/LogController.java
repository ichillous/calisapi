package com.calis100.CalisAPI.controller;

import com.calis100.CalisAPI.model.Challenge;
import com.calis100.CalisAPI.model.Log;
import com.calis100.CalisAPI.model.User;
import com.calis100.CalisAPI.model.enums.Status;
import com.calis100.CalisAPI.service.ChallengeService;
import com.calis100.CalisAPI.service.LogService;
import com.calis100.CalisAPI.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

    /* TODO: Display Log Page*/
    @GetMapping("/new")
    public String showLogForm(Model model) {
        model.addAttribute("log", new Log());
        return "log-form";
    }

    /* TODO: Display all logs*/
    @GetMapping
    public String getLogs() {
        return "all-logs";
    }

    @PostMapping("/log-form")
    public String submitLog(@Valid @ModelAttribute("log") Log log, BindingResult result, Model model) {
        Challenge challenge = log.getChallenge();
        Challenge activeChallenge = challengeService.getChallenge(challenge);

        if (activeChallenge == null) {
            model.addAttribute("error", "Challenge not found");
            return "redirect:/dashboard";
        }
        logService.saveLog(log);
        return "redirect:/dashboard";
    }
}
