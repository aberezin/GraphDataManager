package com.graphapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

/**
 * Main application class.
 */
@SpringBootApplication
@EntityScan(basePackages = {"com.graphapp.model.relational"})
@EnableJpaRepositories(basePackages = {"com.graphapp.repository.relational"})
@EnableNeo4jRepositories(basePackages = {"com.graphapp.repository.graph"})
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