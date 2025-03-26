package com.graphapp.config;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Session;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.neo4j.core.DatabaseSelectionProvider;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.data.neo4j.core.transaction.Neo4jTransactionManager;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;
import java.util.Arrays;

/**
 * Mock configuration for Neo4j when working in development environment.
 * Uses an embedded Neo4j database or a test instance.
 */
@Configuration
@EnableNeo4jRepositories(basePackages = "com.graphapp.repository.graph")
@EnableTransactionManagement
@Profile("dev")
public class MockNeo4jConfig {
    
    private static final String URI = "bolt://localhost:7687";
    private static final String USERNAME = "neo4j";
    private static final String PASSWORD = "password";
    
    /**
     * Create a Neo4j driver for the embedded database.
     * 
     * @return The Neo4j driver.
     */
    @Bean
    public Driver neo4jDriver() {
        return GraphDatabase.driver(URI, AuthTokens.basic(USERNAME, PASSWORD));
    }
    
    /**
     * Create a Neo4j transaction manager.
     * 
     * @param driver The Neo4j driver.
     * @param databaseSelectionProvider The database selection provider.
     * @return The Neo4j transaction manager.
     */
    @Bean
    public Neo4jTransactionManager transactionManager(Driver driver,
                                                      DatabaseSelectionProvider databaseSelectionProvider) {
        return new Neo4jTransactionManager(driver, databaseSelectionProvider);
    }
    
    /**
     * Create a Neo4j client.
     * 
     * @param driver The Neo4j driver.
     * @return The Neo4j client.
     */
    @Bean
    public Neo4jClient neo4jClient(Driver driver) {
        return Neo4jClient.create(driver);
    }
    
    /**
     * Create a Neo4j template.
     * 
     * @param driver The Neo4j driver.
     * @param databaseSelectionProvider The database selection provider.
     * @return The Neo4j template.
     */
    @Bean
    public Neo4jTemplate neo4jTemplate(Driver driver, 
                                       DatabaseSelectionProvider databaseSelectionProvider) {
        return new Neo4jTemplate(driver, databaseSelectionProvider);
    }
    
    /**
     * Initialize the Neo4j database with sample data.
     */
    @PostConstruct
    public void initDatabase() {
        try (Driver driver = neo4jDriver()) {
            try (Session session = driver.session()) {
                // Clear existing data
                session.run("MATCH (n) DETACH DELETE n");
                
                // Create node labels
                String[] nodeQueries = {
                    "CREATE (n:Person {id: 1, label: 'John Doe', properties: {age: 30, occupation: 'Developer'}})",
                    "CREATE (n:Person {id: 2, label: 'Jane Smith', properties: {age: 28, occupation: 'Designer'}})",
                    "CREATE (n:Project {id: 3, label: 'Graph Database App', properties: {status: 'Active'}})",
                    "CREATE (n:Technology {id: 4, label: 'Neo4j', properties: {version: '4.2'}})",
                    "CREATE (n:Technology {id: 5, label: 'Spring Boot', properties: {version: '2.6'}})"
                };
                
                // Create relationships
                String[] relationshipQueries = {
                    "MATCH (a:Person {id: 1}), (b:Project {id: 3}) CREATE (a)-[r:WORKS_ON {id: 1, properties: {role: 'Lead'}}]->(b)",
                    "MATCH (a:Person {id: 2}), (b:Project {id: 3}) CREATE (a)-[r:WORKS_ON {id: 2, properties: {role: 'Contributor'}}]->(b)",
                    "MATCH (a:Project {id: 3}), (b:Technology {id: 4}) CREATE (a)-[r:USES {id: 3, properties: {since: '2023'}}]->(b)",
                    "MATCH (a:Project {id: 3}), (b:Technology {id: 5}) CREATE (a)-[r:USES {id: 4, properties: {since: '2023'}}]->(b)",
                    "MATCH (a:Person {id: 1}), (b:Person {id: 2}) CREATE (a)-[r:KNOWS {id: 5, properties: {since: '2020'}}]->(b)"
                };
                
                // Execute node creation queries
                Arrays.stream(nodeQueries).forEach(session::run);
                
                // Execute relationship creation queries
                Arrays.stream(relationshipQueries).forEach(session::run);
            }
        } catch (Exception e) {
            // In development mode, we may not have a Neo4j instance available
            // So just log the error instead of throwing an exception
            System.err.println("Warning: Failed to initialize Neo4j mock database: " + e.getMessage());
            System.err.println("This is normal if you don't have Neo4j running locally in development mode.");
        }
    }
}