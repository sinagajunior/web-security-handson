package com.lab.security.service;

import com.lab.security.entity.User;
import com.lab.security.repository.SecureUserRepository;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
@RequiredArgsConstructor
public class SecureUserService {

    private final SecureUserRepository userRepository;

    /**
     * Searches users by username with strict input validation.
     * @Pattern blocks SQL metacharacters before they reach the database layer.
     */
    public List<User> searchUsers(
        @NotBlank(message = "Username must not be blank")
        @Size(min = 2, max = 50, message = "Username must be between 2-50 characters")
        @Pattern(
            regexp = "^[a-zA-Z0-9_\\-\\.]+$",
            message = "Username may only contain letters, digits, underscore, dash, and dot"
        )
        String username
    ) {
        return userRepository.findByUsernameContainingIgnoreCase(username);
    }

    /**
     * Searches users by role with whitelist validation.
     * Only ADMIN, USER, or MODERATOR are accepted — any SQL payload is rejected.
     */
    public List<User> searchByRole(
        @NotBlank(message = "Role must not be blank")
        @Pattern(
            regexp = "^(ADMIN|USER|MODERATOR)$",
            message = "Role must be ADMIN, USER, or MODERATOR"
        )
        String role
    ) {
        return userRepository.findByRole(role);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
}
