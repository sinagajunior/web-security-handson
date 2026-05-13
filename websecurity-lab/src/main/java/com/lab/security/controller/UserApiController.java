package com.lab.security.controller;

import com.lab.security.entity.User;
import com.lab.security.repository.SecureUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * MODULE 3 DEMO — Shows bad practice of per-controller @CrossOrigin with wildcard.
 * When the "vulnerable" profile is active, this combined with VulnerableSecurityConfig
 * allows any origin to access user data.
 * In the secure profile, the centralized CorsConfigurationSource in SecureSecurityConfig
 * takes precedence for the /api/** path pattern.
 */
@RestController
@RequestMapping("/api/users")
// VULNERABLE annotation — demonstrated bad practice for Module 3
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class UserApiController {

    private final SecureUserRepository userRepository;

    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/search")
    public List<User> searchByRole(@RequestParam String role) {
        return userRepository.findByRole(role);
    }
}
