package com.graphapp.config;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.harness.Neo4j;
import org.neo4j.harness.Neo4jBuilders;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.neo4j.config.AbstractNeo4jConfig;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PreDestroy;

/**
 * Configuration for embedded Neo4j database (Development).
 * This creates a real in-memory Neo4j instance for development.
 */
@Configuration
@EnableNeo4jRepositories(basePackages = "com.graphapp.repository.graph")
@EnableTransactionManagement
@Profile("dev")
public class MockNeo4jConfig extends AbstractNeo4jConfig {
    
    private Neo4j embeddedServer;

    /**
     * Initialize the embedded Neo4j server.
     * This will create an in-memory Neo4j database that listens on a random port.
     * 
     * @return The Neo4j test server instance.
     */
    @Bean
    public Neo4j embeddedDatabaseServer() {
        // Start in-memory embedded Neo4j server with default random ports
        embeddedServer = Neo4jBuilders
                .newInProcessBuilder()
                .withDisabledServer() // Don't need HTTP server for our use case
                .build();
                
        System.out.println("Started embedded Neo4j server at: " + embeddedServer.boltURI());
        return embeddedServer;
    }
    
    /**
     * Configure the Neo4j driver for development with embedded database.
     * 
     * @return The Neo4j driver connected to the embedded server.
     */
    @Bean
    @Override
    public Driver driver() {
        // Get the embedded database Neo4j server
        Neo4j server = embeddedDatabaseServer();
        
        // Connect to the embedded Neo4j server using its Bolt URI
        return GraphDatabase.driver(server.boltURI(), AuthTokens.none());
    }
    
    /**
     * Clean up resources when the application is shutting down.
     */
    @PreDestroy
    public void stopNeo4j() {
        if (embeddedServer != null) {
            embeddedServer.close();
            System.out.println("Embedded Neo4j server has been shut down");
        }
    }
}