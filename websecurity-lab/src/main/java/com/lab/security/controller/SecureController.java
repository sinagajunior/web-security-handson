package com.lab.security.controller;

import com.lab.security.entity.User;
import com.lab.security.service.SecureUserService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * MODULE 4 FIX — Secure endpoints that validate input at controller + service layers
 * before delegating to parameterized queries.
 * No @CrossOrigin — CORS is managed centrally in SecureSecurityConfig.
 */
@RestController
@RequestMapping("/api/secure")
@Validated
@RequiredArgsConstructor
public class SecureController {

    private final SecureUserService userService;

    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUser(
        @RequestParam
        @NotBlank
        @Size(min = 2, max = 50)
        String username
    ) {
        return ResponseEntity.ok(userService.searchUsers(username));
    }

    @GetMapping("/search-role")
    public ResponseEntity<List<User>> searchByRole(
        @RequestParam @NotBlank String role
    ) {
        return ResponseEntity.ok(userService.searchByRole(role));
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }
}
