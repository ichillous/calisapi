package com.calis100.CalisAPI.controller;

import com.calis100.CalisAPI.model.User;
import com.calis100.CalisAPI.model.enums.Role;
import com.calis100.CalisAPI.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // Registration page - GET
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User()); // Creating an empty User object for the form
        return "register"; // Renders the registration page
    }

    // Registration - POST
    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
        // Validate if email or username already exists
        if (userService.userExistsByEmail(user.getEmail())) {
            result.rejectValue("email", "error.user", "An account with this email already exists.");
        }
        if (userService.userExistsByUsername(user.getUsername())) {
            result.rejectValue("username", "error.user", "An account with this username already exists.");
        }

        // Return to registration form if there are validation errors
        if (result.hasErrors()) {
            return "register"; // Re-render registration form with errors
        }

        // Set default role as USER and save the user
        user.setRole(Role.USER);
        userService.saveUser(user);

        // Redirect to login with success message
        return "redirect:/auth/login?registerSuccess";
    }

    // Login page - GET
    @GetMapping("/login")
    public String showLoginForm(@RequestParam(value = "error", required = false) String error,
                                @RequestParam(value = "logout", required = false) String logout,
                                @RequestParam(value = "registerSuccess", required = false) String registerSuccess,
                                Model model) {
        if (error != null) {
            model.addAttribute("errorMsg", "Invalid username or password.");
        }
        if (logout != null) {
            model.addAttribute("logoutMsg", "You have been logged out.");
        }
        if (registerSuccess != null) {
            model.addAttribute("successMsg", "Registration successful! Please log in.");
        }
        return "login"; // Renders the login page
    }

    // Dashboard redirection after login - GET
    @GetMapping("/dashboard")
    public String showDashboard(Authentication authentication, Model model) {
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        model.addAttribute("user", user); // Add user details to the model for the dashboard view
        return "dashboard"; // Renders the dashboard page
    }

    // Handle custom login page - GET (avoiding mapping conflict with above method)
    @GetMapping("/custom-login")
    public String customLoginPage() {
        return "login"; // Custom login endpoint if you want additional login logic or page variants
    }

    @PostMapping("/login")
    public String loginUser(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "login";
        }
        return "redirect:/dashboard";
        // TODO
    }
}
