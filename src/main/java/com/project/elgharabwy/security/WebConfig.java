package com.project.elgharabwy.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
public class WebConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        // Allow your React app's domain during development
            config.setAllowedOrigins(Arrays.asList(
                "https://elgharabwy-clinc.netlify.app",
                 "http://localhost:3000"
            ));
        config.addAllowedHeader("x-client-id"); // Or add specific headers like "x-client-id"
        config.addAllowedMethod("*");
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // RESTful API methods
        config.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization")); // Headers React might use
        config.setAllowCredentials(true); // Allows cookies and credentials to be sent

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); // Apply CORS globally
        return new CorsFilter(source);
    }
}
