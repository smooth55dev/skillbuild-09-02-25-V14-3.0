package com.example.skillbuild.controller;

import com.example.skillbuild.domain.AppUser;
import com.example.skillbuild.repository.AppUserRepository;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import java.util.regex.Pattern;

/**
 * @details implements Validator → This class follows Spring’s Validator interface to validate objects before processing.
 * Used for form validation, such as:
 * Checking if the username or email is already taken.
 * Ensuring the password meets security requirements.
 * Validating input fields before saving a user.
 * Prevents invalid data from being saved to the database by throwing validation errors.
 * It ensures that user data is properly formatted and secure before being processed.
 */
public class AppUserValidator implements Validator {

    private AppUserRepository repo;
    // Email regex pattern for validation
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    public AppUserValidator(AppUserRepository repo) {
        this.repo = repo;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return AppUser.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AppUser user = (AppUser) target;

        if (repo.findById(user.getId()).isPresent()) {
            errors.rejectValue("id", "", "ID already in use.");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "", "Your agent needs a name!");
        // Ensure university is Leicester
        if (!"Leicester".equals(user.getUniversity())) {
            errors.rejectValue("university", "", "Only for Leicester University Students");
        }
        // Validate that password is at least 6 characters long
        if (user.getPassword() == null || user.getPassword().length() < 6) {
            errors.rejectValue("password", "", "Password must be at least 6 characters long.");
        }
        // Ensure email is valid
        if (user.getEmail() == null || !EMAIL_PATTERN.matcher(user.getEmail()).matches()) {
            errors.rejectValue("email", "", "Invalid email format.");

        }
    }
}
