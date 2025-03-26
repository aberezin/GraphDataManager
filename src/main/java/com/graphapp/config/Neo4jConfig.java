package com.graphapp.config;

import org.neo4j.driver.Driver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.neo4j.core.DatabaseSelectionProvider;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.data.neo4j.core.transaction.Neo4jTransactionManager;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Configuration for Neo4j graph database connection.
 * Used in production environment.
 */
@Configuration
@EnableNeo4jRepositories(basePackages = "com.graphapp.repository.graph")
@EnableTransactionManagement
@Profile("prod")
public class Neo4jConfig {

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
}