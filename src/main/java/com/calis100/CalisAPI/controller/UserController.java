package com.calis100.CalisAPI.controller;



import com.calis100.CalisAPI.model.User;
import com.calis100.CalisAPI.model.enums.Role;
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

    // Display all users (Admin functionality)
    @GetMapping("/all")
    public String getAllUsers(Model model, Authentication authentication) {
        String username = authentication.getName();
        User currentUser = userService.findByUsername(username);
        if (!currentUser.getRole().equals(Role.ADMIN)) {
            return "redirect:/access-denied";
        }
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


    @GetMapping("/edit")
    public String showEditForm(Authentication authentication, Model model) {
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        model.addAttribute("user", user);
        return "edit-user";
    }

    // Handle profile update
    @PostMapping("/edit")
    public String updateUser(@ModelAttribute("user") @Valid User user,
                             BindingResult result, Authentication authentication, RedirectAttributes redirectAttributes) {
        String currentUsername = authentication.getName();
        User currentUser = userService.findByUsername(currentUsername);


        if (result.hasErrors()) {
            return "edit-user";
        }

        // Update user details
        currentUser.setUsername(user.getUsername());
        currentUser.setEmail(user.getEmail());
        // Handle password update if necessary
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            currentUser.setPassword(user.getPassword()); // Ensure password is encoded in service
        }

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
        return "redirect:/auth/register?accountDeleted";
    }

}
