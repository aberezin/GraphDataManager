package com.graphapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Main application class for the Graph App.
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.graphapp"})
public class Application {
    
    /**
     * Main method to start the application.
     * 
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}