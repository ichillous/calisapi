package com.calis100.CalisAPI.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {
    @GetMapping("/home")
    public String home() {
        return "home";
    }
    @PostMapping("/home")
    public String postHome() {
        return "home";
    }
}
