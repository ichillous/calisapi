package com.calis100.CalisAPI.controller;


import com.calis100.CalisAPI.model.Log;
import com.calis100.CalisAPI.model.User;
import com.calis100.CalisAPI.service.LogService;
import com.calis100.CalisAPI.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/log")
public class LogController {
    @Autowired
    private LogService logService;

    @Autowired
    private UserService userService;

    @GetMapping("/log-form")
    public String showLogForm() {
        return "log-form";
    }

    @PostMapping("/log-form")
    public String saveLog(@Valid @ModelAttribute("log") Log log,
                          Authentication authentication,
                          Model model) {
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        return "redirect:/dashboard";
    }


}
