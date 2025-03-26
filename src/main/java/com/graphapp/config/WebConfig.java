package com.graphapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web configuration for the application.
 */
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    
    /**
     * Configure CORS mapping to allow cross-origin requests.
     * 
     * @param registry The CORS registry.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:3000", "http://localhost:5000")
                .allowedOriginPatterns(
                    "http://*.replit.app", "http://*.replit.dev", 
                    "https://*.replit.app", "https://*.replit.dev",
                    "http://*.repl.co", "https://*.repl.co"
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
    
    /**
     * Configure CORS filter to allow requests from Replit domains.
     * This provides a more flexible way to handle cross-origin requests.
     * 
     * @return The CORS filter.
     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        
        // Allow credentials
        config.setAllowCredentials(true);
        
        // Allow all headers
        config.addAllowedHeader("*");
        
        // Allow all methods
        config.addAllowedMethod("*");
        
        // Allow localhost origins
        config.addAllowedOrigin("http://localhost:3000");
        config.addAllowedOrigin("http://localhost:5000");
        
        // Allow all Replit domains
        config.addAllowedOriginPattern("https://*.replit.app");
        config.addAllowedOriginPattern("https://*.replit.dev");
        config.addAllowedOriginPattern("http://*.replit.app");
        config.addAllowedOriginPattern("http://*.replit.dev");
        config.addAllowedOriginPattern("https://*.repl.co");
        config.addAllowedOriginPattern("http://*.repl.co");
        
        source.registerCorsConfiguration("/api/**", config);
        return new CorsFilter(source);
    }
}