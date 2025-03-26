package com.graphapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

/**
 * Main application class that bootstraps the Spring Boot application.
 * This application uses both Neo4j and SQLite databases.
 */
@SpringBootApplication
@EnableNeo4jRepositories(basePackages = "com.graphapp.repository.graph")
@EnableJpaRepositories(basePackages = "com.graphapp.repository.relational")
@EntityScan(basePackages = {"com.graphapp.model.relational", "com.graphapp.model.graph"})
public class Application {

    /**
     * Main method that starts the Spring Boot application.
     * @param args Command line arguments passed to the application
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}