package com.lab.security.controller;

import com.lab.security.entity.User;
import com.lab.security.repository.VulnerableUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * MODULE 4 DEMO — Endpoints that pass user input directly to vulnerable SQL queries.
 * No validation, no parameterization. Demonstrates SQL injection surface.
 */
@RestController
@RequestMapping("/api/vulnerable")
@RequiredArgsConstructor
public class VulnerableController {

    private final VulnerableUserRepository vulnerableRepo;

    // VULNERABLE: input goes straight to string-concatenated native SQL
    @GetMapping("/search")
    public List<User> searchUser(@RequestParam String username) {
        return vulnerableRepo.searchByUsernameVulnerable(username);
    }

    // VULNERABLE: input goes straight to string-concatenated JPQL
    @GetMapping("/search-email")
    public List<User> searchByEmail(@RequestParam String email) {
        return vulnerableRepo.searchByEmailVulnerable(email);
    }

    // VULNERABLE: input goes straight to string-concatenated LIKE query
    @GetMapping("/search-role")
    public List<User> searchByRole(@RequestParam String role) {
        return vulnerableRepo.searchByRoleVulnerable(role);
    }
}
