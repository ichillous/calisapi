package com.calis100.CalisAPI.controller;



import com.calis100.CalisAPI.model.Challenge;
import com.calis100.CalisAPI.model.User;
import com.calis100.CalisAPI.model.enums.Role;
import com.calis100.CalisAPI.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



@Controller
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    // Display the user's profile along with challenge statistics
    @GetMapping("/profile")
    public String showUserProfile(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByUsername(userDetails.getUsername());
        model.addAttribute("user", user);

        int activeChallenges = userService.countActiveChallenges(user);
        int completedChallenges = userService.countCompletedChallenges(user);
        int failedChallenges = userService.countFailedChallenges(user);
        // Add challenge statistics to the model
        model.addAttribute("activeChallenges", activeChallenges);
        model.addAttribute("completedChallenges", completedChallenges);
        model.addAttribute("failedChallenges", failedChallenges);

        return "profile";
    }

    // Handle the change of username
    @PostMapping("/users/update-username")
    public String updateUsername(@RequestParam("newUsername") String newUsername,
                                 Authentication authentication,
                                 RedirectAttributes redirectAttributes) {
        String currentUsername = authentication.getName();
        User user = userService.findByUsername(currentUsername);

        if (userService.userExistsByUsername(newUsername)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Username already exists!");
            return "redirect:/profile";
        }

        user.setUsername(newUsername);
        userService.saveUser(user);

        redirectAttributes.addFlashAttribute("successMessage", "Username updated successfully!");
        return "redirect:/profile";
    }

    // Handle profile update (existing method for other profile details)
    @PostMapping("/edit")
    public String updateUser(@ModelAttribute("user") @Valid User user,
                             BindingResult result, Authentication authentication, RedirectAttributes redirectAttributes) {
        String currentUsername = authentication.getName();
        User currentUser = userService.findByUsername(currentUsername);

        if (result.hasErrors()) {
            return "profile";
        }

        // Update user details
        currentUser.setEmail(user.getEmail());
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            currentUser.setPassword(user.getPassword()); // Ensure password is encoded in service
        }

        userService.saveUser(currentUser);

        redirectAttributes.addFlashAttribute("successMessage", "Profile updated successfully!");
        return "redirect:/profile";
    }

    // Delete the user account
    @PostMapping("/delete")
    public String deleteUser(Authentication authentication) {
        String username = authentication.getName();
        userService.deleteByUsername(username);
        // Invalidate the session or handle logout if necessary
        return "redirect:/register";
    }

}
