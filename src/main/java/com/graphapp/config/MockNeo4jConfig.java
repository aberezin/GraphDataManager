package com.graphapp.config;

import org.neo4j.driver.Driver;
import org.neo4j.driver.internal.InternalDriver;
import org.neo4j.driver.internal.logging.DevNullLogging;
import org.neo4j.harness.Neo4j;
import org.neo4j.harness.Neo4jBuilders;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.neo4j.config.AbstractNeo4jConfig;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Neo4j configuration for development environment
 * Uses embedded Neo4j for testing and development
 */
@Configuration
@EnableNeo4jRepositories(basePackages = "com.graphapp.repository.graph")
@EnableTransactionManagement
@Profile("dev")
public class MockNeo4jConfig extends AbstractNeo4jConfig {

    /**
     * Embedded Neo4j instance for development
     */
    @Bean(destroyMethod = "close")
    public Neo4j embeddedDatabaseServer() {
        return Neo4jBuilders.newInProcessBuilder()
                .withDisabledServer() // No need for HTTP server in embedded mode
                .build();
    }

    /**
     * Driver for the embedded database
     */
    @Bean
    @Override
    public Driver driver() {
        return embeddedDatabaseServer().driver();
    }
}