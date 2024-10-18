package com.calis100.CalisAPI.controller;


import com.calis100.CalisAPI.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DashboardController {
    private final UserService userService;

    @Autowired
    public DashboardController(UserService userService) {
        this.userService = userService;
    }

    // dashboard display page
    @GetMapping("/dashboard")
    public String showDashboard() {
        return "dashboard";
    }

    /* TODO: tba */

    @GetMapping("/greeting")
    public String greet(@RequestParam("username") String username, Model model) {
        String name = userService.findByUsername(username).toString();
        model.addAttribute("user", "Hello " + name + "!");
        return "dashboard";
    }
}
