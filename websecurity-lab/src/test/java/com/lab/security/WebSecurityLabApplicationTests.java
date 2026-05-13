package com.lab.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@DisplayName("Application Context Smoke Test")
class WebSecurityLabApplicationTests {

    @Test
    @DisplayName("Spring application context loads without errors")
    void contextLoads() {
        // Validates: entity scan, JPA schema creation, data.sql seeding,
        // security config wiring, and all bean dependencies resolve correctly.
    }
}
