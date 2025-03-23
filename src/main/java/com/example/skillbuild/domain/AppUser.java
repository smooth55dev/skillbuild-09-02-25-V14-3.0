package com.example.skillbuild.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @details Defines user attributes such as:
 * username
 * email
 * password
 * roles (e.g., ROLE_USER, ROLE_ADMIN)
 * Maps to a database table if using JPA (@Entity).
 * Used for authentication and authorization in the application.
 * May include methods for password encryption, role management, or profile updates.
 * This class is the core model for user data in the application.
 */
@Entity
@Getter
@Setter
public class AppUser {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String email;
    private String university;
    private String password;
    private boolean special = false;

    // added for reset password
    private String resetToken; // Token for password reset
    private LocalDateTime resetTokenExpiry; // Expiry time for the
    private String refreshToken;

    public boolean isSpecial() {
        return special;
    }

    // reset tokens added here as well
    @Override
    public String toString() {
        return "AppUser{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", university='" + university + '\'' +
                ", password='" + password + '\'' +
                ", special=" + special +
                ", resetToken='" + resetToken + '\'' +
                ", resetTokenExpiry=" + resetTokenExpiry +

                '}';
    }
}
