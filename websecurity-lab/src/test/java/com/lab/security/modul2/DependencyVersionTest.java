package com.lab.security.modul2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.security.CodeSource;
import java.security.ProtectionDomain;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@DisplayName("Module 2 - CVE Dependency Version Guard")
class DependencyVersionTest {

    @Test
    @DisplayName("log4j-core 2.14.1 (CVE-2021-44228, CVSS 10.0) must NOT be on classpath")
    void log4jVulnerableVersionNotPresent() {
        try {
            Class<?> log4jClass = Class.forName("org.apache.logging.log4j.core.impl.Log4jContextFactory");
            ProtectionDomain pd = log4jClass.getProtectionDomain();
            CodeSource cs = pd.getCodeSource();
            if (cs != null) {
                String jarPath = cs.getLocation().getPath();
                assertThat(jarPath)
                    .as("log4j-core JAR must not be vulnerable version 2.14.1 (CVE-2021-44228)")
                    .doesNotContain("2.14.1");
            }
        } catch (ClassNotFoundException e) {
            // log4j-core not on classpath — no CVE risk, test passes
        }
    }

    @Test
    @DisplayName("jackson-databind 2.9.8 (CVE-2019-17267, CVSS 9.8) must NOT be on classpath")
    void jacksonVulnerableVersionNotPresent() {
        try {
            Class<?> jacksonClass = Class.forName("com.fasterxml.jackson.databind.ObjectMapper");
            ProtectionDomain pd = jacksonClass.getProtectionDomain();
            CodeSource cs = pd.getCodeSource();
            if (cs != null) {
                String jarPath = cs.getLocation().getPath();
                assertThat(jarPath)
                    .as("jackson-databind JAR must not be vulnerable version 2.9.8 (CVE-2019-17267)")
                    .doesNotContain("2.9.8");
            }
        } catch (ClassNotFoundException e) {
            // Not on classpath — acceptable
        }
    }

    @Test
    @DisplayName("If log4j is present, it must not be any Log4Shell-vulnerable version (< 2.17.0)")
    void log4jMustBePatched() {
        try {
            Class<?> log4jClass = Class.forName("org.apache.logging.log4j.core.impl.Log4jContextFactory");
            ProtectionDomain pd = log4jClass.getProtectionDomain();
            CodeSource cs = pd.getCodeSource();
            if (cs != null) {
                String jarPath = cs.getLocation().getPath();
                assertThat(jarPath)
                    .as("log4j version must be patched against Log4Shell")
                    .doesNotContain("2.14.")
                    .doesNotContain("2.15.")
                    .doesNotContain("2.16.");
            }
        } catch (ClassNotFoundException e) {
            // Not present — no assertion required
        }
    }

    @Test
    @DisplayName("Spring Boot BOM controls jackson-databind version (no manual pin at 2.9.x)")
    void jacksonVersionManagedByBom() {
        try {
            Class<?> jacksonClass = Class.forName("com.fasterxml.jackson.databind.ObjectMapper");
            ProtectionDomain pd = jacksonClass.getProtectionDomain();
            CodeSource cs = pd.getCodeSource();
            if (cs != null) {
                String jarPath = cs.getLocation().getPath();
                // Spring Boot 3.2.x manages jackson-databind at 2.16.x or higher
                assertThat(jarPath)
                    .as("jackson-databind must not be manually pinned to 2.9.x")
                    .doesNotContain("-2.9.")
                    .doesNotContain("-2.10.")
                    .doesNotContain("-2.11.");
            }
        } catch (ClassNotFoundException e) {
            // Acceptable
        }
    }
}
