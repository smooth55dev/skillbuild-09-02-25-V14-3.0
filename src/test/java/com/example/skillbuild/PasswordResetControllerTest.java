package com.example.skillbuild;

import com.example.skillbuild.controller.PasswordResetController;
import com.example.skillbuild.service.PasswordResetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class PasswordResetControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PasswordResetService passwordResetService;

    @Mock
    private Model model;

    @InjectMocks
    private PasswordResetController controller;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    /**
     * Test that the forgot password form page loads correctly.
     */
    @Test
    void testShowForgotPasswordForm() throws Exception {
        mockMvc.perform(get("/forgotPassword"))
                .andExpect(status().isOk())  // Expect HTTP 200 OK
                .andExpect(view().name("security/forgotPassword")) // Expect correct view name
                .andExpect(model().attributeExists("appUser")); // Expect "appUser" model attribute
    }

    /**
     * Test that a reset token is generated for a valid email.
     */
    @Test
    void testProcessForgotPassword_Success() throws Exception {
        String testEmail = "test@example.com";
        when(passwordResetService.generateResetToken(testEmail)).thenReturn("mock-token-123");

        mockMvc.perform(post("/forgotPassword")
                        .param("email", testEmail))
                .andExpect(status().isOk())
                .andExpect(view().name("security/forgotPassword"))
                .andExpect(model().attributeExists("message")); // Expect success message in model

        verify(passwordResetService, times(1)).generateResetToken(testEmail);
    }

    /**
     * Test that an error message is shown if the email is not found.
     */
    @Test
    void testProcessForgotPassword_Failure() throws Exception {
        String testEmail = "invalid@example.com";
        when(passwordResetService.generateResetToken(testEmail)).thenThrow(new RuntimeException("Email not found."));

        mockMvc.perform(post("/forgotPassword")
                        .param("email", testEmail))
                .andExpect(status().isOk())
                .andExpect(view().name("security/forgotPassword"))
                .andExpect(model().attributeExists("error")); // Expect error message in model

        verify(passwordResetService, times(1)).generateResetToken(testEmail);
    }

    /**
     * Test that a password reset request is processed successfully.
     */
    @Test
    void testResetPassword_Success() throws Exception {
        String token = "valid-token";
        String newPassword = "Secure123!";

        mockMvc.perform(post("/resetPassword")
                        .param("token", token)
                        .param("newPassword", newPassword))
                .andExpect(status().isOk())
                .andExpect(view().name("security/resetPasswordSuccess")); // Expect reset success page

        verify(passwordResetService, times(1)).resetPassword(token, newPassword);
    }

    /**
     * Test that the reset password form page loads correctly with a valid token.
     */
    @Test
    void testShowResetPasswordForm() throws Exception {
        String testToken = "mock-reset-token";

        mockMvc.perform(get("/resetPassword")
                        .param("token", testToken))
                .andExpect(status().isOk())
                .andExpect(view().name("security/resetPassword")) // Expect correct view
                .andExpect(model().attributeExists("token")); // Expect token to be in model
    }
}
