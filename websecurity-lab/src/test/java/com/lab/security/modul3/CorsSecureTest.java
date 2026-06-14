package com.lab.security.modul3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("Module 3 - Secure CORS Configuration Tests")
class CorsSecureTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Preflight from evil-site.com must NOT receive Access-Control-Allow-Origin header")
    void preflightFromEvilSiteIsBlocked() throws Exception {
        mockMvc.perform(options("/api/secure/users")
                .header("Origin", "https://evil-site.com")
                .header("Access-Control-Request-Method", "GET")
                .header("Access-Control-Request-Headers", "Content-Type"))
            .andExpect(header().doesNotExist("Access-Control-Allow-Origin"));
    }

    @Test
    @DisplayName("Preflight from allowed origin localhost:3000 returns correct specific origin header")
    @WithMockUser(username = "alice", roles = {"USER"})
    void preflightFromAllowedOriginIsGranted() throws Exception {
        mockMvc.perform(options("/api/secure/users")
                .header("Origin", "http://localhost:3000")
                .header("Access-Control-Request-Method", "GET")
                .header("Access-Control-Request-Headers", "Content-Type"))
            .andExpect(status().isOk())
            .andExpect(header().string("Access-Control-Allow-Origin", "http://localhost:3000"));
    }

    @Test
    @DisplayName("Response to allowed origin must include Access-Control-Allow-Credentials: true")
    @WithMockUser(username = "alice", roles = {"USER"})
    void allowedOriginReceivesCredentialsHeader() throws Exception {
        mockMvc.perform(get("/api/secure/users")
                .header("Origin", "http://localhost:3000"))
            .andExpect(header().string("Access-Control-Allow-Origin", "http://localhost:3000"))
            .andExpect(header().string("Access-Control-Allow-Credentials", "true"));
    }

    @Test
    @DisplayName("Response to allowed origin must NOT return wildcard — must return specific origin")
    @WithMockUser(username = "alice", roles = {"USER"})
    void allowedOriginGetsSpecificNotWildcard() throws Exception {
        mockMvc.perform(get("/api/secure/users")
                .header("Origin", "http://localhost:3000"))
            .andExpect(header().string("Access-Control-Allow-Origin", "http://localhost:3000"))
            .andExpect(header().string("Vary", containsString("Origin")));
    }

    @Test
    @DisplayName("Preflight from localhost:4200 (second allowed origin) is also granted")
    @WithMockUser(username = "alice", roles = {"USER"})
    void preflightFromSecondAllowedOriginIsGranted() throws Exception {
        mockMvc.perform(options("/api/secure/users")
                .header("Origin", "http://localhost:4200")
                .header("Access-Control-Request-Method", "GET")
                .header("Access-Control-Request-Headers", "Content-Type"))
            .andExpect(status().isOk())
            .andExpect(header().string("Access-Control-Allow-Origin", "http://localhost:4200"));
    }

//    @Test
//    @DisplayName("Unauthenticated request to secure endpoint returns 401 or redirects to login")
//    void unauthenticatedRequestIsRejected() throws Exception {
//        mockMvc.perform(get("/api/secure/users"))
//            .andExpect(status().is(anyOf(is(401), is(302))));
//    }

    @Test
    @DisplayName("Preflight for non-whitelisted HTTP method is rejected")
    @WithMockUser(username = "alice", roles = {"USER"})
    void preflightForNonWhitelistedMethodIsRejected() throws Exception {
        // TRACE is not in the allowed methods list
        mockMvc.perform(options("/api/secure/users")
                .header("Origin", "http://localhost:3000")
                .header("Access-Control-Request-Method", "TRACE")
                .header("Access-Control-Request-Headers", "Content-Type"))
            // When an unknown method is requested in preflight, the CORS processor
            // does not include Access-Control-Allow-Methods for TRACE
            .andExpect(result -> {
                String allowMethods = result.getResponse().getHeader("Access-Control-Allow-Methods");
                if (allowMethods != null) {
                    org.assertj.core.api.Assertions.assertThat(allowMethods)
                        .doesNotContain("TRACE");
                }
            });
    }
}
