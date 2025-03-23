package com.example.skillbuild.repository;

import com.example.skillbuild.domain.AppUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * @details The AppUserRepository interface is a Spring Data JPA repository for managing AppUser entities in the database.
 *
 * Functions & Description:
 * Extends CrudRepository<AppUser, Integer>, which provides built-in CRUD operations (save, findById, delete, etc.).
 * Custom Query Methods:
 * findByEmail(String email): Retrieves a user by their email.
 * findByResetToken(String resetToken): Finds a user by their password reset token.
 * Uses Optional<AppUser> to handle cases where no user is found.
 * This repository simplifies database interactions for user management.
 */
public interface AppUserRepository extends CrudRepository<AppUser, Integer> {
    Optional<AppUser> findByEmail(String email);

    Optional<AppUser> findByResetToken(String resetToken);
}