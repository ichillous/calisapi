package com.calis100.CalisAPI.controller;

import com.calis100.CalisAPI.model.Challenge;
import com.calis100.CalisAPI.model.User;
import com.calis100.CalisAPI.model.enums.Status;
import com.calis100.CalisAPI.service.ChallengeService;
import com.calis100.CalisAPI.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AcceptController {
    private final ChallengeService challengeService;
    private final UserService userService;
    @Autowired
    public AcceptController(ChallengeService challengeService, UserService userService) {
        this.challengeService = challengeService;
        this.userService = userService;
    }

    @GetMapping("/accept-form")
    public String showAcceptForm() {
        return "accept-form";
    }

    @PostMapping("/accept-form")
    public String acceptForm(@ModelAttribute("challenge") Challenge challenge,
                             Authentication authentication,
                             Model model) {
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        challenge.setUser(user);
        challenge.setStatus(Status.Active);
        challengeService.updateChallengeProgress(challenge);
        model.addAttribute("challenge", challenge);
        return "redirect:/dashboard";
    }
}
