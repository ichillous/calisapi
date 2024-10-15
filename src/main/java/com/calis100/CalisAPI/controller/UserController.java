package com.calis100.CalisAPI.controller;


import com.calis100.CalisAPI.dto.UserDTO;
import com.calis100.CalisAPI.model.User;
import com.calis100.CalisAPI.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Display the registration form
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", model);
        return "register"; // Returns the register.html template
    }

    // Handle user registration
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") @Valid UserDTO userDTO,
                               BindingResult result, Model model) {
        // Validate if email or username already exists
        if (userService.findByEmail(userDTO.getEmail()) != null) {
            result.rejectValue("email", "error.user", "An account with this email already exists.");
        }
        if (userService.findByUsername(userDTO.getUsername()) != null) {
            result.rejectValue("username", "error.user", "An account with this username already exists.");
        }

        if (result.hasErrors()) {
            model.addAttribute("user", userDTO);
            return "register";
        }

        // Map UserDTO to User entity
        User user = convertToEntity(userDTO);

        userService.saveUser(user);
        return "redirect:/login?registerSuccess";
    }

    // Display the login page
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    // Display all users (Admin functionality)
    @GetMapping("/all")
    public String getAllUsers(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "all-users"; // Returns the all-users.html template
    }

    // Display the user's profile
    @GetMapping("/profile")
    public String showUserProfile(Authentication authentication, Model model) {
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        model.addAttribute("user", user);
        return "profile";
    }

    // Show edit form
    @GetMapping("/edit")
    public String showEditForm(Authentication authentication, Model model) {
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        model.addAttribute("userDTO", user);
        return "edit-user";
    }

    // Handle profile update
    @PostMapping("/edit")
    public String updateUser(@ModelAttribute("userDTO") @Valid UserDTO userDTO,
                             BindingResult result, Authentication authentication, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "edit-user";
        }

        String currentUsername = authentication.getName();
        User user = userService.findByUsername(currentUsername);

        // Update user details
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());

        userService.saveUser(user);

        redirectAttributes.addFlashAttribute("successMessage", "Profile updated successfully!");
        return "redirect:/users/profile";
    }
    // Delete the user account
    @PostMapping("/delete")
    public String deleteUser(Authentication authentication) {
        String username = authentication.getName();
        userService.deleteByUsername(username);
        // Invalidate the session or handle logout if necessary
        return "redirect:/register?accountDeleted";
    }


    // Helper method to convert UserDTO to User entity
    private User convertToEntity(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPasswordHash(userDTO.getPassword()); // Password encoding is handled in the service
        // Set other fields as necessary
        return user;
    }
}
