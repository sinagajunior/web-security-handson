package com.lab.security.modul3;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("vulnerable")
@DisplayName("Module 3 - Vulnerable CORS Configuration Tests")
class CorsVulnerableTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Preflight from evil-site.com returns Access-Control-Allow-Origin: *")
    void preflightFromEvilSiteReturnsWildcardOrigin() throws Exception {
        mockMvc.perform(options("/api/users")
                .header("Origin", "https://evil-site.com")
                .header("Access-Control-Request-Method", "GET")
                .header("Access-Control-Request-Headers", "Content-Type"))
            .andExpect(status().isOk())
            .andExpect(header().string("Access-Control-Allow-Origin", "*"));
    }

    @Test
    @DisplayName("Preflight from any arbitrary origin is permitted (wildcard active)")
    void preflightFromAnyOriginIsAllowed() throws Exception {
        mockMvc.perform(options("/api/users")
                .header("Origin", "https://attacker.example.com")
                .header("Access-Control-Request-Method", "DELETE")
                .header("Access-Control-Request-Headers", "X-Custom-Header"))
            .andExpect(status().isOk())
            .andExpect(header().exists("Access-Control-Allow-Origin"));
    }

    @Test
    @DisplayName("All HTTP methods are permitted when wildcard config is active")
    void preflightAllowsAllHttpMethods() throws Exception {
        mockMvc.perform(options("/api/users")
                .header("Origin", "https://evil-site.com")
                .header("Access-Control-Request-Method", "DELETE")
                .header("Access-Control-Request-Headers", "Content-Type"))
            .andExpect(status().isOk())
            .andExpect(header().string("Access-Control-Allow-Origin", "*"));
    }

    @Test
    @DisplayName("CSRF is disabled — POST without CSRF token must not return 403")
    void csrfDisabledAllowsPostWithoutCsrfToken() throws Exception {
        mockMvc.perform(post("/api/users")
                .header("Origin", "https://evil-site.com")
                .contentType("application/json")
                .content("{}"))
            // A CSRF-protected endpoint returns 403; with CSRF disabled it must NOT
            .andExpect(result ->
                Assertions.assertThat(result.getResponse().getStatus()).isNotEqualTo(403)
            );
    }
}
