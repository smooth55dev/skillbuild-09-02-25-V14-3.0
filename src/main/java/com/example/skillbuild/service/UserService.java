package com.example.skillbuild.service;

import com.example.skillbuild.domain.AppUser;
import com.example.skillbuild.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void updateUserProfile(int userId, String newName, String newEmail, String newPassword) {
        AppUser appUser = appUserRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // ✅ Validate email uniqueness
        Optional<AppUser> existingUser = appUserRepository.findByEmail(newEmail);
        if (existingUser.isPresent() && existingUser.get().getId() != userId) {
            throw new RuntimeException("Email is already in use.");
        }

        appUser.setName(newName);
        appUser.setEmail(newEmail);
        if (!newPassword.isEmpty()) {
            appUser.setPassword(passwordEncoder.encode(newPassword));  // ✅ Encrypt password if changed
        }

        appUserRepository.save(appUser);
    }
}
