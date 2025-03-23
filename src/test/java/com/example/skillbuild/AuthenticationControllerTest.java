package com.example.skillbuild;

import com.example.skillbuild.controller.AuthenticationController;
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

import java.security.Principal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {

    @InjectMocks
    private AuthenticationController controller;

    @Mock
    private AppUserRepository repo;

    @Mock
    private Model model;

    @Mock
    private Principal principal;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoginForm() {
        String viewName = controller.loginForm(model);

        // Verify that "appUser" attribute is added to the model
        verify(model, times(1)).addAttribute(eq("appUser"), any(AppUser.class));

        // Check if the correct view is returned
        assertEquals("security/login", viewName);
    }

    @Test
    void testAccessDenied() {
        String viewName = controller.accessDenied();

        // Check if the correct view is returned
        assertEquals("security/denied", viewName);
    }

    @Test
    void testLoginSuccess_UserExistsAndSpecial() {
        // Mock Principal (User is logged in)
        when(principal.getName()).thenReturn("user@example.com");

        // Mock User Repository
        AppUser user = new AppUser();
        user.setEmail("user@example.com");
        user.setSpecial(true);  // Special user

        when(repo.findByEmail("user@example.com")).thenReturn(Optional.of(user));

        String viewName = controller.loginSuccess(principal);

        // Ensure the user is redirected to the dashboard
        assertEquals("redirect:/dashboard", viewName);
    }

    @Test
    void testLoginSuccess_UserExistsNotSpecial() {
        // Mock Principal (User is logged in)
        when(principal.getName()).thenReturn("user@example.com");

        // Mock User Repository
        AppUser user = new AppUser();
        user.setEmail("user@example.com");
        user.setSpecial(false);  // Not special

        when(repo.findByEmail("user@example.com")).thenReturn(Optional.of(user));

        String viewName = controller.loginSuccess(principal);

        // Ensure the user is redirected to the dashboard
        assertEquals("redirect:/dashboard", viewName);
    }

    @Test
    void testLoginSuccess_UserNotFound() {
        // Mock Principal (User is logged in)
        when(principal.getName()).thenReturn("unknown@example.com");

        // Mock User Repository (No user found)
        when(repo.findByEmail("unknown@example.com")).thenReturn(Optional.empty());

        try {
            controller.loginSuccess(principal);
        } catch (RuntimeException e) {
            assertEquals("User not found", e.getMessage());
        }
    }

    @Test
    void testLoginSuccess_NoPrincipal() {
        // Simulate no principal (user not logged in)
        String viewName = controller.loginSuccess(null);

        // Ensure it returns the success page
        assertEquals("security/success", viewName);
    }
}
