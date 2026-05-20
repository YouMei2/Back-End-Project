package com.hehorhii.restful_api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;

import java.util.Arrays;
import java.util.List;

// SecurityConfig configures Spring Security settings for the application.
// This configuration disables CSRF, enables CORS, and permits all requests for testing purposes.
@Configuration
public class SecurityConfig {

    // Configures the security filter chain to disable CSRF and allow all requests
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults()) // Enable CORS support
                .csrf(AbstractHttpConfigurer::disable)    // Disable CSRF (reason for 403 error)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/**").permitAll() // Allow everything for testing
                        .anyRequest().permitAll()
                );
        return http.build();
    }

    // This bean forcibly allows requests from your frontend
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);

        // list of permitted sites
        config.setAllowedOrigins(List.of(
                "https://youmei2.github.io"
        ));

        config.setAllowedHeaders(List.of("*"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}