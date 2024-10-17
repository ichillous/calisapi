package com.calis100.CalisAPI.controller;

import com.calis100.CalisAPI.model.Challenge;
import com.calis100.CalisAPI.model.Log;
import com.calis100.CalisAPI.model.enums.Status;
import com.calis100.CalisAPI.service.ChallengeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ChallengeController {
    private final ChallengeService challengeService;

    @Autowired
    public ChallengeController(ChallengeService challengeService) {
        this.challengeService = challengeService;
    }


    /* TODO: Display Challenge progress in the dashboard page */
    @GetMapping
    public String getChallenges() {
        return "view-challenges";
    }

    /* TODO: Accept a new challenge */
    @PostMapping("/accept")
    public String acceptChallenge(@Valid @ModelAttribute("challenge") Challenge challenge, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "dashboard";
        }

        if (challenge.getStatus() == Status.Active) {
            model.addAttribute("message", "You have already accepted a challenge");
            return "dashboard";
        }

        if (challenge.getStatus() == Status.None) {
            Log log = new Log();
            log.setChallenge(challenge);
            model.addAttribute("log", log);
            return "/log-form";
        }
        challenge.setStatus(Status.Active);
        challengeService.saveChallenge(challenge);
        return "redirect:/dashboard";
    }
}
