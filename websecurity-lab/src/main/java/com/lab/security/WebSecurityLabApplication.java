package com.lab.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class WebSecurityLabApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebSecurityLabApplication.class, args);
    }
}
