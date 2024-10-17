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

    @GetMapping("/new")
    public String showLogForm(Model model, Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        Challenge activeChallenge = challengeService.getActiveChallenge(user);

        if (activeChallenge == null) {
            return "redirect:/dashboard?error=noActiveChallenge";
        }

        model.addAttribute("log", new Log());
        model.addAttribute("challenge", activeChallenge);
        return "log-form";
    }

    @GetMapping
    public String getLogs(Model model, Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        List<Log> logs = logService.getLogsByUser(user);
        model.addAttribute("logs", logs);
        return "all-logs";
    }

    @PostMapping("/submit")
    public String submitLog(@Valid @ModelAttribute("log") Log log, BindingResult result, Authentication authentication) {
        if (result.hasErrors()) {
            return "log-form";
        }

        String username = authentication.getName();
        User user = userService.findByUsername(username);
        Challenge activeChallenge = challengeService.getActiveChallenge(user);

        if (activeChallenge == null) {
            return "redirect:/dashboard?error=noActiveChallenge";
        }

        log.setChallenge(activeChallenge);
        logService.saveLog(log);
        challengeService.updateChallengeProgress(activeChallenge);

        return "redirect:/dashboard?success=logSubmitted";
    }
}
