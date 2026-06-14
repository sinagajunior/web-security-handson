package com.lab.security.repository;

import com.lab.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface SecureUserRepository extends JpaRepository<User, Long> {

    // Method derivation — Spring generates a parameterized query automatically
    Optional<User> findByUsername(String username);

    // Safe LIKE search via method derivation
    List<User> findByUsernameContainingIgnoreCase(String username);

    Optional<User> findByEmail(String email);

    List<User> findByRole(String role);

    // Named JPQL parameter — input is bound as a value, never concatenated into query string
    @Query("SELECT u FROM User u WHERE u.username = :username")
    List<User> findByUsernameSecure(@Param("username") String username);

    // Safe LIKE using CONCAT inside JPQL — parameter binding prevents injection
    @Query("SELECT u FROM User u WHERE LOWER(u.username) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<User> searchByKeyword(@Param("keyword") String keyword);

    // Native query remains safe because values are passed as bound parameters
    @Query(value = "SELECT * FROM users WHERE username = :username AND role = :role",
           nativeQuery = true)
    List<User> findByUsernameAndRole(@Param("username") String username,
                                     @Param("role") String role);

    // Dynamic optional-parameter search — both params are nullable
    @Query("SELECT u FROM User u WHERE " +
           "(:username IS NULL OR LOWER(u.username) LIKE LOWER(CONCAT('%', :username, '%'))) AND " +
           "(:role IS NULL OR u.role = :role)")
    List<User> searchSecure(@Param("username") String username,
                             @Param("role") String role);
}
