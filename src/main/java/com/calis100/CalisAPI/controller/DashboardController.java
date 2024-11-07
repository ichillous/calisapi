package com.calis100.CalisAPI.controller;

import com.calis100.CalisAPI.model.Challenge;
import com.calis100.CalisAPI.model.Log;
import com.calis100.CalisAPI.model.User;
import com.calis100.CalisAPI.model.enums.EditMode;
import com.calis100.CalisAPI.service.ChallengeService;
import com.calis100.CalisAPI.service.LogService;
import com.calis100.CalisAPI.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {
    private final UserService userService;
    private final ChallengeService challengeService;
    private final LogService logService;

    @Autowired
    public DashboardController(UserService userService, ChallengeService challengeService, LogService logService) {
        this.userService = userService;
        this.challengeService = challengeService;
        this.logService = logService;
    }

    @GetMapping
    public String dashboard(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByUsername(userDetails.getUsername());
        Challenge challenge = challengeService.getActiveChallenge(user);
        model.addAttribute("challenge", challenge);
        model.addAttribute("logs", logService.findAllByChallenge(challenge));
        model.addAttribute("log", new Log());

        return "dashboard";
    }

    /* LOG form page */
    @GetMapping("/log")
    @Transactional
    public String showAddForm(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByUsername(userDetails.getUsername());
        List<Log> list = logService.findAllByChallenge(challengeService.getActiveChallenge(user));
        model.addAttribute("logs", list);
        model.addAttribute("log", new Log()); // Add this line to add empty log object to the model
        return "dashboard";
    }

    @GetMapping("/log/{id}")
    public String editLogModel(@PathVariable("id") Long id, @ModelAttribute("log") Log log,
                               BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "redirect:/dashboard";
        }
        Log existingLog = logService.findById(id);
        if (existingLog == null) {
            return "redirect:/dashboard";
        }
        model.addAttribute("editMode", EditMode.UPDATE);
        logService.updateLog(id);
        model.addAttribute("log", log);
        return "dashboard";
    }

    @PostMapping("/log/save")
    public String saveLog(@Valid @ModelAttribute("log") Log log, BindingResult result, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        if (result.hasErrors()) {
            // Ensure that the log object is present in the model along with logs and challenge
            model.addAttribute("log", log);
            return "redirect:/dashboard";
        }
        User user = userService.findByUsername(userDetails.getUsername());
        Challenge challenge = challengeService.getActiveChallenge(user);
        model.addAttribute("challenge", challenge);
        model.addAttribute("logs", logService.findAllByChallenge(challenge));

        log.setChallenge(challenge);
        logService.create(log);
        return "redirect:/dashboard";
    }

    @PostMapping("/log/delete/{id}")
    public String deleteLog(@PathVariable Long id) {
        logService.deleteById(id);
        return "redirect:/dashboard";
    }

}