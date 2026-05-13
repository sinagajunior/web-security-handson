package com.lab.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * MODULE 3 SIMULATION — Loaded only when profile "vulnerable" is active.
 * Demonstrates CORS misconfiguration and disabled CSRF.
 * NEVER use this configuration in production.
 */
@Configuration
@Profile("vulnerable")
public class VulnerableSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(vulnerableCorsConfig()))
            // VULNERABLE: CSRF disabled — enables cross-site request forgery attacks
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/h2-console/**").permitAll()
                .requestMatchers("/api/**").permitAll()
                .anyRequest().authenticated()
            )
            .headers(headers -> headers
                .frameOptions(frame -> frame.disable())
            );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource vulnerableCorsConfig() {
        CorsConfiguration config = new CorsConfiguration();
        // VULNERABLE: wildcard allows any domain to make cross-origin requests
        config.addAllowedOrigin("*");
        // VULNERABLE: all HTTP methods permitted from any origin
        config.addAllowedMethod("*");
        // VULNERABLE: all headers accepted from any origin
        config.addAllowedHeader("*");
        // Note: allowCredentials(true) cannot be combined with wildcard origin

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Applied to ALL paths, not just /api/**
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
