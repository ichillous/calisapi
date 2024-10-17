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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class DashboardController {
    private final UserService userService;

    @Autowired
    public DashboardController(UserService userService) {
        this.userService = userService;
    }

    /* TODO: Display Dashboard*/
    @GetMapping("/dashboard")
    public String showDashboard() {
        return "dashboard";
    }

    /* TODO: Display */

    @GetMapping("/greeting")
    public String greet(@RequestParam("username") String username, Model model) {
        String name = userService.findByUsername(username).toString();
        model.addAttribute("user", "Hello " + name + "!");
        return "dashboard";
    }
}
