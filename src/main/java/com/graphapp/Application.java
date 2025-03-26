package com.graphapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

/**
 * Main application class with Spring Boot configuration.
 */
@SpringBootApplication
@ComponentScan({"com.graphapp.config", "com.graphapp.controller", "com.graphapp.service"})
@EntityScan("com.graphapp.model")
public class Application {

    /**
     * Main method to start the application.
     * @param args Command-line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}