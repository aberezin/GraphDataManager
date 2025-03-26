package com.graphapp.config;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.neo4j.core.transaction.Neo4jTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * Configuration class for Neo4j database.
 * This configuration is used in production environment.
 */
@Configuration
@Profile("!dev")
public class Neo4jConfig {

    @Value("${spring.neo4j.uri:bolt://localhost:7687}")
    private String uri;

    @Value("${spring.neo4j.authentication.username:neo4j}")
    private String username;

    @Value("${spring.neo4j.authentication.password:password}")
    private String password;

    /**
     * Creates a Neo4j driver bean.
     * @return A configured Neo4j Driver
     */
    @Bean
    public Driver driver() {
        return GraphDatabase.driver(uri, AuthTokens.basic(username, password));
    }

    /**
     * Configures the transaction manager for Neo4j.
     * @param driver The Neo4j driver
     * @return A configured PlatformTransactionManager
     */
    @Bean
    public PlatformTransactionManager neo4jTransactionManager(Driver driver) {
        return new Neo4jTransactionManager(driver);
    }
}