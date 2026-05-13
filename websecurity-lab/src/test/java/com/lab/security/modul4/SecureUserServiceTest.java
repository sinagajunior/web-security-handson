package com.lab.security.modul4;

import com.lab.security.service.SecureUserService;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Uses @SpringBootTest (not just Mockito) because @Validated on the service
 * requires the Spring AOP proxy to be active for method-level constraint
 * enforcement to fire. A raw "new SecureUserService()" would bypass all
 * @Pattern / @Size / @NotBlank annotations.
 */
@SpringBootTest
@ActiveProfiles("test")
@Transactional
@DisplayName("Module 4 - SecureUserService Input Validation Tests")
class SecureUserServiceTest {

    @Autowired
    private SecureUserService secureUserService;

    // ─── Username searchUsers() validation ───────────────────────────────────

    @Test
    @DisplayName("SQL OR injection characters rejected by @Pattern constraint")
    void orInjectionPayloadRejectedByPattern() {
        assertThatThrownBy(() -> secureUserService.searchUsers("' OR '1'='1"))
            .isInstanceOf(ConstraintViolationException.class)
            .hasMessageContaining("Username may only contain");
    }

    @Test
    @DisplayName("UNION keyword injection rejected by @Pattern constraint")
    void unionInjectionPayloadRejectedByPattern() {
        assertThatThrownBy(() -> secureUserService.searchUsers("' UNION SELECT--"))
            .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    @DisplayName("Semicolon and comment marker injection rejected by @Pattern")
    void semicolonInjectionRejectedByPattern() {
        assertThatThrownBy(() -> secureUserService.searchUsers("alice'; DROP TABLE users--"))
            .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    @DisplayName("Blank username rejected by @NotBlank constraint")
    void blankUsernameRejectedByNotBlank() {
        assertThatThrownBy(() -> secureUserService.searchUsers(""))
            .isInstanceOf(ConstraintViolationException.class)
            .hasMessageContaining("must not be blank");
    }

    @Test
    @DisplayName("Username with 1 character rejected by @Size(min=2) constraint")
    void singleCharUsernameRejectedBySize() {
        assertThatThrownBy(() -> secureUserService.searchUsers("a"))
            .isInstanceOf(ConstraintViolationException.class)
            .hasMessageContaining("2-50 characters");
    }

    @Test
    @DisplayName("Username exceeding 50 characters rejected by @Size(max=50) constraint")
    void tooLongUsernameRejectedBySize() {
        String tooLong = "a".repeat(51);
        assertThatThrownBy(() -> secureUserService.searchUsers(tooLong))
            .isInstanceOf(ConstraintViolationException.class)
            .hasMessageContaining("2-50 characters");
    }

    @Test
    @DisplayName("Valid alphanumeric username passes all constraints")
    void validAlphanumericUsernamePassesValidation() {
        assertThatCode(() -> secureUserService.searchUsers("alice"))
            .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Username with underscore, dash, and dot passes @Pattern constraint")
    void usernameWithAllowedSpecialCharsPassesValidation() {
        assertThatCode(() -> secureUserService.searchUsers("user_name-01.test"))
            .doesNotThrowAnyException();
    }

    // ─── Role searchByRole() validation ──────────────────────────────────────

    @Test
    @DisplayName("Arbitrary role string 'HACKER' rejected by @Pattern whitelist")
    void invalidRoleHackerRejectedByPattern() {
        assertThatThrownBy(() -> secureUserService.searchByRole("HACKER"))
            .isInstanceOf(ConstraintViolationException.class)
            .hasMessageContaining("Role must be ADMIN, USER, or MODERATOR");
    }

    @Test
    @DisplayName("SQL injection in role field rejected by @Pattern whitelist")
    void sqlInjectionInRoleRejectedByPattern() {
        assertThatThrownBy(() -> secureUserService.searchByRole("ADMIN' OR '1'='1"))
            .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    @DisplayName("Blank role rejected by @NotBlank constraint")
    void blankRoleRejectedByNotBlank() {
        assertThatThrownBy(() -> secureUserService.searchByRole(""))
            .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    @DisplayName("Valid role ADMIN passes @Pattern whitelist constraint")
    void validRoleAdminPassesValidation() {
        assertThatCode(() -> secureUserService.searchByRole("ADMIN"))
            .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Valid role USER passes @Pattern whitelist constraint")
    void validRoleUserPassesValidation() {
        assertThatCode(() -> secureUserService.searchByRole("USER"))
            .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Valid role MODERATOR passes @Pattern whitelist constraint")
    void validRoleModeratorPassesValidation() {
        assertThatCode(() -> secureUserService.searchByRole("MODERATOR"))
            .doesNotThrowAnyException();
    }
}
