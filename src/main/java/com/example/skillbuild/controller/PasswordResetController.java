package com.example.skillbuild.controller;

import com.example.skillbuild.domain.AppUser;
import com.example.skillbuild.service.PasswordResetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@Controller
public class PasswordResetController {

    private final PasswordResetService passwordResetService;

    public PasswordResetController(PasswordResetService passwordResetService) {
        this.passwordResetService = passwordResetService;
    }

    @GetMapping("/forgotPassword")
    public String showForgotPasswordForm(Model model) {
        model.addAttribute("appUser", new AppUser());
        return "security/forgotPassword"; // Loads the JSP file
    }
    @PostMapping("/forgotPassword")
    public String processForgotPassword(@RequestParam("email") String email, Model model) {
        System.out.println("ðŸ” Processing forgot password request for: " + email);
        model.addAttribute("appUser", new AppUser()); // âœ… Always add appUser to avoid errors

        try {
            String resetToken = passwordResetService.generateResetToken(email);
            model.addAttribute("message", "A password reset link has been sent to your email.");
            System.out.println("âœ… Reset token generated: " + resetToken); // ðŸš€ Debugging: Print to console
        } catch (Exception e) {
            model.addAttribute("error", "Email not found.");
        }
        return "security/forgotPassword"; // Stay on the same page with success/error message
    }

    @PostMapping("/resetPassword")
    public String resetPassword(@RequestParam("token") String token,
                                @RequestParam("newPassword") String newPassword, Model model) {
        passwordResetService.resetPassword(token, newPassword);
        return "security/resetPasswordSuccess"; // Returns a JSP or Thymeleaf page
    }

    @GetMapping("/resetPassword")
    public String showResetPasswordForm(@RequestParam("token") String token, Model model) {
        model.addAttribute("token", token);
        return "security/resetPassword"; // âœ… This loads a JSP/HTML form for password reset
    }

}
