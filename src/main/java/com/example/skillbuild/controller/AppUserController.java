package com.example.skillbuild.controller;

import com.example.skillbuild.config.PasswordEncoder;
import com.example.skillbuild.domain.AppUser;
import com.example.skillbuild.repository.AppUserRepository;
import com.example.skillbuild.service.BadgeService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @details @Controller → Marks this class as a Spring MVC Controller (used for handling HTTP requests and returning views).
 * Handles user-related operations like:
 * Registering users
 * Logging in users
 * Displaying user-related pages
 * Maps HTTP requests (GET, POST) to specific methods inside the class.
 * This class acts as the entry point for user interactions in the web application.
 */
@Controller
public class AppUserController {


    @Autowired
    private AppUserRepository repo;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private BadgeService badgeService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(new AppUserValidator(repo));
    }

    @RequestMapping({"/appUsers", "/appUser"})
    public String agents(Model model) {
        model.addAttribute("agents", repo.findAll());
        return "appUsers/dashboard";
    }


    @RequestMapping("/addAppUser")
    public String addUserForm(Model model) {
        model.addAttribute("user", new AppUser());
        return "appUsers/form";  //
    }
    @RequestMapping(value = "/addAppUser", method = RequestMethod.POST)
    public String addAgent(@Valid @ModelAttribute("user") AppUser user, BindingResult result) {
        if (result.hasErrors()) {
            return "appUsers/form";
        }
        user.setSpecial(false);
        // Encrypt password before saving
        user.setPassword(passwordEncoder.encoder().encode(user.getPassword()));

        repo.save(user);
        return "redirect:/login-form";
    }

    @GetMapping("/editProfile")
    public String editProfile(Model model) {
        String email = getLoggedInUserEmail();

        Optional<AppUser> userOptional = repo.findByEmail(email);
        if (userOptional.isPresent()) {
            AppUser user = userOptional.get();
            model.addAttribute("user", user);
            model.addAttribute("badges", badgeService.getUserBadges(user));
            return "appUsers/profile";
        } else {
            return "redirect:/dashboard";
        }
    }


    //Post profile updates
    @Transactional
    @PostMapping("/editProfile")
    public String editProfile(@Valid @ModelAttribute("user") AppUser user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            System.out.println("Validation errors: " + result.getAllErrors());
            return "appUsers/profile";
        }

        // Debugging logs
        System.out.println("Received user update request for ID: " + user.getId());

        Optional<AppUser> existingUserOptional = repo.findById(user.getId()); // Ensure this fetches by ID
        if (existingUserOptional.isPresent()) {
            AppUser existingUser = existingUserOptional.get();

            System.out.println("Found user: " + existingUser.getEmail()); // Debug log

            // Update fields
            existingUser.setName(user.getName());
            existingUser.setEmail(user.getEmail());

            // Only update password if it's not empty or null
            if (user.getPassword() != null && !user.getPassword().trim().isEmpty()) {
                existingUser.setPassword(passwordEncoder.encoder().encode(user.getPassword()));
                System.out.println("Password updated.");
            } else {
                existingUser.setPassword(existingUser.getPassword()); // Keep old password
                System.out.println("Password not changed.");
            }

            // Save to DB
            //debug
            System.out.println("Saving: " + existingUser.getEmail());
            repo.save(existingUser);
            System.out.println("User updated successfully: " + existingUser.getId());

            return "redirect:/dashboard";
        } else {
            System.out.println("User not found in the database for ID: " + user.getId());
        }

        model.addAttribute("error", "User not found");
        return "appUsers/profile";
    }

    private String getLoggedInUserEmail() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        }
        return null;
    }
}
