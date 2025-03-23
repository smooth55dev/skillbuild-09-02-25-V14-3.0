package com.example.skillbuild;

import com.example.skillbuild.domain.AppUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

class AppUserTest {

    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        // Initialize Password Encoder
        passwordEncoder = new BCryptPasswordEncoder();
    }

    @Test
    void testAppUser_SettersAndGetters() {
        // Create a new user
        AppUser user = new AppUser();
        user.setId(1);
        user.setName("Test User");
        user.setEmail("testuser@example.com");
        user.setUniversity("Spring Boot University");
        user.setPassword("plaintextPassword");
        user.setSpecial(true);

        // Validate getters
        assertEquals(1, user.getId());
        assertEquals("Test User", user.getName());
        assertEquals("testuser@example.com", user.getEmail());
        assertEquals("Spring Boot University", user.getUniversity());
        assertEquals("plaintextPassword", user.getPassword());
        assertTrue(user.isSpecial());
    }

    @Test
    void testPasswordEncoding() {
        AppUser user = new AppUser();
        String rawPassword = "securePassword123";
        String encodedPassword = passwordEncoder.encode(rawPassword);

        // Set encoded password
        user.setPassword(encodedPassword);

        // Verify password is correctly encoded
        assertNotEquals(rawPassword, user.getPassword());
        assertTrue(passwordEncoder.matches(rawPassword, user.getPassword()));
    }
}
