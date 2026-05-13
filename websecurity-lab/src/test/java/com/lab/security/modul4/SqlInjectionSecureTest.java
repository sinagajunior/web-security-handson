package com.lab.security.modul4;

import com.lab.security.entity.User;
import com.lab.security.repository.SecureUserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
@DisplayName("Module 4 - Parameterized Query Injection Prevention Tests")
class SqlInjectionSecureTest {

    @Autowired
    private SecureUserRepository secureUserRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("OR injection via method derivation returns empty — payload treated as literal string")
    void orInjectionWithMethodDerivationReturnsEmpty() {
        String payload = "' OR '1'='1";
        List<User> result = secureUserRepository.findByUsernameContainingIgnoreCase(payload);

        assertThat(result)
            .as("Parameterized LIKE query treats injection payload as a literal value — no user has this username")
            .isEmpty();
    }

    @Test
    @DisplayName("OR injection via @Query named parameter returns empty")
    void orInjectionWithNamedParamReturnsEmpty() {
        String payload = "' OR '1'='1";
        List<User> result = secureUserRepository.findByUsernameSecure(payload);

        assertThat(result)
            .as("@Query with @Param never embeds the value into the query string")
            .isEmpty();
    }

    @Test
    @DisplayName("UNION injection via searchByKeyword returns empty — treated as substring search")
    void unionInjectionInKeywordSearchReturnsEmpty() {
        String payload = "' UNION SELECT * FROM USERS --";
        List<User> result = secureUserRepository.searchByKeyword(payload);

        assertThat(result)
            .as("CONCAT-based LIKE parameterized query treats UNION payload as literal substring — no match")
            .isEmpty();
    }

    @Test
    @DisplayName("Normal username query returns only the matching user")
    void normalQueryReturnsCorrectUser() {
        List<User> result = secureUserRepository.findByUsernameContainingIgnoreCase("alice");

        assertThat(result)
            .as("Normal search for 'alice' must return exactly one user")
            .hasSize(1)
            .extracting(User::getUsername)
            .containsExactly("alice");
    }

    @Test
    @DisplayName("Wildcard keyword search is safe and returns expected partial matches")
    void keywordSearchIsSafeAndReturnsPartialMatches() {
        List<User> result = secureUserRepository.searchByKeyword("ali");

        assertThat(result)
            .as("Keyword search for 'ali' should match alice only")
            .hasSize(1)
            .extracting(User::getUsername)
            .containsExactly("alice");
    }

    @Test
    @DisplayName("Secure endpoint rejects OR injection payload with HTTP 400")
    @WithMockUser(username = "alice", roles = {"USER"})
    void secureEndpointRejectsOrInjectionWith400() throws Exception {
        mockMvc.perform(get("/api/secure/search")
                .param("username", "' OR '1'='1"))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Secure endpoint rejects UNION injection payload with HTTP 400")
    @WithMockUser(username = "alice", roles = {"USER"})
    void secureEndpointRejectsUnionInjectionWith400() throws Exception {
        mockMvc.perform(get("/api/secure/search")
                .param("username", "' UNION SELECT * FROM USERS --"))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Secure endpoint rejects single-char username (too short) with HTTP 400")
    @WithMockUser(username = "alice", roles = {"USER"})
    void secureEndpointRejectsTooShortUsername() throws Exception {
        mockMvc.perform(get("/api/secure/search")
                .param("username", "a"))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Secure endpoint accepts valid username and returns HTTP 200")
    @WithMockUser(username = "alice", roles = {"USER"})
    void secureEndpointAcceptsValidInput() throws Exception {
        mockMvc.perform(get("/api/secure/search")
                .param("username", "alice"))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Secure endpoint rejects invalid role string with HTTP 400")
    @WithMockUser(username = "alice", roles = {"USER"})
    void secureEndpointRejectsInvalidRole() throws Exception {
        mockMvc.perform(get("/api/secure/search-role")
                .param("role", "HACKER"))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Secure endpoint accepts valid role ADMIN and returns HTTP 200")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void secureEndpointAcceptsValidRole() throws Exception {
        mockMvc.perform(get("/api/secure/search-role")
                .param("role", "ADMIN"))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Native query with parameter binding is safe against injection")
    void nativeQueryWithParamBindingIsSafe() {
        String injectionUsername = "' OR '1'='1";
        String injectionRole = "' OR '1'='1";
        List<User> result = secureUserRepository.findByUsernameAndRole(injectionUsername, injectionRole);

        assertThat(result)
            .as("Native query with :param binding treats injection as literal — no match")
            .isEmpty();
    }
}
