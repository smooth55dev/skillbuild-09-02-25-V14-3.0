package com.example.skillbuild;

import com.example.skillbuild.config.PasswordEncoder;
import com.example.skillbuild.controller.AppUserController;
import com.example.skillbuild.domain.AppUser;
import com.example.skillbuild.repository.AppUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppUserControllerTest {

    @InjectMocks
    private AppUserController controller;

    @Mock
    private AppUserRepository repo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private Model model;

    private BCryptPasswordEncoder mockEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Mock the password encoder, but use lenient to prevent UnnecessaryStubbingException
        mockEncoder = mock(BCryptPasswordEncoder.class);
        lenient().when(mockEncoder.encode(anyString())).thenReturn("encodedPassword");
        lenient().when(passwordEncoder.encoder()).thenReturn(mockEncoder);
    }

    @Test
    void testAddAgent_ValidUser() {
        // Setup only when needed
        when(passwordEncoder.encoder()).thenReturn(mockEncoder);
        when(mockEncoder.encode(anyString())).thenReturn("encodedPassword");

        AppUser user = new AppUser();
        user.setPassword("password123");

        when(bindingResult.hasErrors()).thenReturn(false);

        String result = controller.addAgent(user, bindingResult);

        verify(repo, times(1)).save(user);
        assertEquals("redirect:/login-form", result);
    }

    @Test
    void testAddAgent_InvalidUser() {
        // Mock an invalid user with validation errors
        AppUser user = new AppUser();
        when(bindingResult.hasErrors()).thenReturn(true); // Simulate validation failure

        // Call the method
        String result = controller.addAgent(user, bindingResult);

        // Ensure save() is never called
        verify(repo, never()).save(any(AppUser.class));

        // Check if user stays on the form page
        assertEquals("appUsers/form", result);
    }

}
