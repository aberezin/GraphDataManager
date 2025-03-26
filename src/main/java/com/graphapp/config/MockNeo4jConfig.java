package com.graphapp.config;

import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.neo4j.config.AbstractNeo4jConfig;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Configuration class for Neo4j database in development environment.
 * Uses an embedded in-memory Neo4j database for development and testing.
 */
@Configuration
@EnableNeo4jRepositories(basePackages = "com.graphapp.repository.graph")
@EnableTransactionManagement
@Profile("dev")
public class MockNeo4jConfig extends AbstractNeo4jConfig {

    /**
     * Create a Neo4j driver bean for development.
     * Uses bolt protocol with in-memory database.
     * @return Neo4j driver instance
     */
    @Bean
    @Override
    public Driver driver() {
        return GraphDatabase.driver("bolt://localhost:7687");
    }
}