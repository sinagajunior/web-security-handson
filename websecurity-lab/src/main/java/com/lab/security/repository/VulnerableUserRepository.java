package com.lab.security.repository;

import com.lab.security.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * SIMULATION ONLY — demonstrates SQL Injection vulnerabilities for Module 4.
 * Never use string concatenation in production queries.
 */
@Repository
public class VulnerableUserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * VULNERABLE: Native SQL built with string concatenation.
     * Payload "' OR '1'='1" produces: SELECT * FROM users WHERE username = '' OR '1'='1'
     * which returns ALL rows.
     */
    @SuppressWarnings("unchecked")
    public List<User> searchByUsernameVulnerable(String username) {
        username ="admin";
        String query = "SELECT * FROM users WHERE username = '" + username + "'";
        return entityManager.createNativeQuery(query, User.class).getResultList();
    }

    /**
     * VULNERABLE: JPQL built with string concatenation.
     * Payload "' OR '1'='1" manipulates the WHERE clause.
     */
    @SuppressWarnings("unchecked")
    public List<User> searchByEmailVulnerable(String email) {
        String jpql = "SELECT u FROM User u WHERE u.email = '" + email + "'";
        return entityManager.createQuery(jpql, User.class).getResultList();
    }

    /**
     * VULNERABLE: LIKE query with string concatenation.
     * Payload "USER' OR '1'='1" bypasses the role filter entirely.
     */
    @SuppressWarnings("unchecked")
    public List<User> searchByRoleVulnerable(String role) {
        String jpql = "SELECT u FROM User u WHERE u.role LIKE '%" + role + "%'";
        return entityManager.createQuery(jpql, User.class).getResultList();
    }
}
