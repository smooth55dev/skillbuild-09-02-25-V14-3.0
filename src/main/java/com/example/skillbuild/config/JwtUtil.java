package com.example.skillbuild.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

/// Ensure the secret key is at least 32 characters long (or use Base64 encoding)
/**
 * Generates JWT Tokens – When a user logs in, it creates a token that contains user information and an expiration time.
 * Validates JWT Tokens – When a request comes in, it checks if the token is valid (not expired, properly signed).
 * Extracts Information from JWT – It retrieves the username or other details stored inside the token.
 * Tokens Are Used for Authentication:

 * When making requests, the client includes the JWT in the Authorization header.
 * The backend extracts the token and verifies it using JwtUtil.validateToken().
 * If valid, the user is authenticated.
 * Session vs. JWT:

 * Even though your app uses sessions, JWT allows API authentication for non-browser clients (like mobile apps).
 * If the session expires, the refresh token can be used to get a new access token without logging in again.
 * token is being generated in authService
 */
@Component
public class JwtUtil {

    private static final String SECRET_KEY = "a_very_long_secret_key_that_is_32_chars_minimum";
    private static final long ACCESS_EXPIRATION_TIME = 1000L * 60 * 15; // 15 minutes (Access Token)
    private static final long REFRESH_EXPIRATION_TIME = 1000L * 60 * 60 * 24; // 24 hours (Refresh Token)

    private Key getSigningKey() {
        byte[] keyBytes = Base64.getEncoder().encode(SECRET_KEY.getBytes());
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * @details The function generateToken(UUID userId) is designed to create a unique token associated with a specific user,
     * identified by their UUID. This token is typically used for authentication or session management purposes,
     * allowing the application to verify the user's identity in subsequent interactions.
     * @param userId
     * @return
     */
    // Generate Access Token
    public String generateToken(UUID userId) {
        return Jwts.builder()
                .setSubject(userId.toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_EXPIRATION_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * @detils Regenerating a refresh token each time it's used, a process known as refresh token rotation,
     * enhances security. With rotation, every time an application exchanges a refresh token for a
     * new access token, a new refresh token is also issued, and the old one is invalidated.
     * This approach reduces the risk of a compromised refresh token being reused maliciously,
     * as any attempt to use an old refresh token can be detected and addressed promptly.
     * @param userId
     * @return
     */
    // Generate Refresh Token
    public String generateRefreshToken(UUID userId) {
        return Jwts.builder()
                .setSubject(userId.toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_EXPIRATION_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Validate Token
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // Extract User ID from Token
    public UUID extractUserId(String token) {
        return UUID.fromString(Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject());
    }
}
