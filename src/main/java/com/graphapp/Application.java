package com.graphapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Main application class for the Graph Relational Data Application.
 * This application uses both Neo4j for graph data and SQLite for relational data.
 */
@SpringBootApplication
@EnableNeo4jRepositories(basePackages = "com.graphapp.repository.graph")
@EnableJpaRepositories(basePackages = "com.graphapp.repository.relational")
@EntityScan(basePackages = {"com.graphapp.model.relational", "com.graphapp.model.graph"})
@EnableTransactionManagement
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