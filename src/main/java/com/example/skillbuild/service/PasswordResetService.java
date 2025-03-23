package com.example.skillbuild.service;

import com.example.skillbuild.domain.AppUser;
import com.example.skillbuild.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import java.util.Optional;
import java.util.UUID;

/**
 * @details The PasswordResetService class manages password reset functionality in the application.
 *
 * Functions & Description:
 * Generates a Password Reset Token:
 *
 * Finds the user by email.
 * Creates a unique reset token (UUID).
 * Saves the token in the database.
 * Sends a password reset email with a reset link.
 * Resets Password Using Token:
 *
 * Finds the user by reset token.
 * Updates the password (hashing it with PasswordEncoder).
 * Clears the reset token after use.
 * Sends Password Reset Email:
 *
 * Uses JavaMailSender to send an email with a reset link.
 * The email contains a URL with the reset token.
 * This service handles secure password reset requests via email verification
 */
@Service
@RequiredArgsConstructor
public class PasswordResetService {

    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final JavaMailSender mailSender;

    public String generateResetToken(String email) {
        email = email.trim().toLowerCase();
        Optional<AppUser> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        AppUser user = userOptional.get();
        user.setResetToken(UUID.randomUUID().toString());
        userRepository.save(user);

        sendResetEmail(user.getEmail(), user.getResetToken());
        return user.getResetToken();
    }

    public void resetPassword(String token, String newPassword) {
        Optional<AppUser> userOptional = userRepository.findByResetToken(token);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("Invalid token");
        }

        AppUser user = userOptional.get();
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetToken(null); // 🔹 Clear token after use
        userRepository.save(user);
    }
    private void sendResetEmail(String email, String resetToken) {
        try {
            String resetUrl = "https://10.9.37.102/resetPassword?token=" + resetToken;
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Password Reset Request");
            message.setText("To reset your password, click the link below:\n" + resetUrl);

            System.out.println("📧 Sending email to: " + email);
            mailSender.send(message);
            System.out.println("Email sent successfully!");

        } catch (Exception e) {
            System.out.println("ERROR: Failed to send email - " + e.getMessage());
            e.printStackTrace();
        }
    }

}

