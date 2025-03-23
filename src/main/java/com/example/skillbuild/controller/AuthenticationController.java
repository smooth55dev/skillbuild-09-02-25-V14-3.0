package com.example.skillbuild.controller;

import com.example.skillbuild.domain.AppUser;
import com.example.skillbuild.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

/**
 * @details The AuthenticationController file is similar to AuthController, but it specifically focuses on handling authentication requests in a Spring Boot application.
 *
 * Functions & Description:
 * Manages user authentication, typically using JWT or sessions.
 * Handles login requests (POST /authenticate or similar).
 * Validates user credentials and issues an authentication token (JWT) or creates a session.
 * May include logout functionality (POST /logout).
 * This class acts as the entry point for authentication API requests in the application.
 */
@Controller
public class AuthenticationController {

    @Autowired
    AppUserRepository repo;

    @RequestMapping("/login-form")
    public String loginForm(Model model) {
        model.addAttribute("appUser", new AppUser());
        return "security/login";
    }
 //   @RequestMapping("/myLogin")
 //   public void loginProcess() {
 //       System.out.println("Processing");
 //   }

    @RequestMapping("/denied")
    public String accessDenied() {
        return "security/denied";
    }
    @RequestMapping("/success-login")
    public String loginSuccess(Principal principal) {
        if (principal != null) {
            AppUser user = repo.findByEmail(principal.getName())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            if (user.isSpecial()) {
                return "redirect:/dashboard";
            } else {
                return "redirect:/dashboard";
            }
        }
        return "security/success";
    }


}
