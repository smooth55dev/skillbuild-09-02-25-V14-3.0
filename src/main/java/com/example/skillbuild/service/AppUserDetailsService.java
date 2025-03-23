package com.example.skillbuild.service;

import com.example.skillbuild.domain.AppUser;
import com.example.skillbuild.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @details The AppUserDetailsService class is a Spring Security service that loads user details for authentication.
 *
 * Functions & Description:
 * @Service → Marks this class as a Spring-managed service.
 * Implements UserDetailsService, which is used by Spring Security to load user details.
 * Overrides loadUserByUsername(String username) to:
 * Retrieve user details from the database.
 * Convert them into a UserDetails object for authentication.
 * Used for authentication & authorization, typically with JWT or session-based login.
 * This service provides user authentication data for Spring Security.
 */
@Service
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    private AppUserRepository repo;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser appUser = repo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String role = appUser.isSpecial() ? "ROLE_SPECIAL_AGENT" : "ROLE_ORDINARY_AGENT";

        return User.builder()
                .username(appUser.getEmail())
                .password(appUser.getPassword())
                .authorities(role)
                .build();
    }

}
