package com.hehorhii.restful_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// Main application class for the RESTful API.
// This is the entry point for the Spring Boot application, enabling scheduling and providing a BCrypt password encoder bean.
@EnableScheduling
@SpringBootApplication
public class ResTfulApiApplication {
    // Main method to run the Spring Boot application
    static void main(String[] args) {
        SpringApplication.run(ResTfulApiApplication.class, args);
    }
    // Bean for password encoding using BCrypt
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
