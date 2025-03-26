package com.graphapp.config;

import org.neo4j.driver.Driver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.data.neo4j.core.transaction.Neo4jTransactionManager;

import static org.mockito.Mockito.mock;

/**
 * Mock Neo4j configuration for development and testing purposes
 * This class is active only in the "dev" profile
 */
@Configuration
@Profile("dev")
public class MockNeo4jConfig {

    @Bean
    @Primary
    public Driver mockDriver() {
        return mock(Driver.class);
    }

    @Bean
    @Primary
    public Neo4jClient mockNeo4jClient() {
        return mock(Neo4jClient.class);
    }

    @Bean
    @Primary
    public Neo4jTemplate mockNeo4jTemplate() {
        return mock(Neo4jTemplate.class);
    }

    @Bean
    @Primary
    public Neo4jTransactionManager mockTransactionManager() {
        return mock(Neo4jTransactionManager.class);
    }
}