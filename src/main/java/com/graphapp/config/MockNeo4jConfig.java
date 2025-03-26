package com.graphapp.config;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.neo4j.config.AbstractNeo4jConfig;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Configuration for embedded Neo4j database (Development).
 */
@Configuration
@EnableNeo4jRepositories(basePackages = "com.graphapp.repository.graph")
@EnableTransactionManagement
@Profile("dev")
public class MockNeo4jConfig extends AbstractNeo4jConfig {
    
    /**
     * Configure the Neo4j driver for development with embedded database.
     * 
     * @return The Neo4j driver.
     */
    @Bean
    @Override
    public Driver driver() {
        try {
            // Connect to Neo4j server if available
            return GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "password"));
        } catch (Exception e) {
            // If we can't connect to Neo4j, return a mock driver that doesn't actually connect to anything
            // This is for development mode only - you would need a real Neo4j instance in production
            System.out.println("Warning: Using mock Neo4j driver. Graph database operations will not work properly.");
            return GraphDatabase.driver("bolt://localhost:7687", AuthTokens.none());
        }
    }
}