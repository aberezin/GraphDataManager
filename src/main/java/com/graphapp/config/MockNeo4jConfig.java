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

/**
 * Neo4j configuration for development environment.
 * This class creates an embedded Neo4j server for testing and development.
 */
@Configuration
@EnableNeo4jRepositories(basePackages = "com.graphapp.repository.graph")
@EnableTransactionManagement
@Profile("dev")
public class MockNeo4jConfig extends AbstractNeo4jConfig {

    /**
     * Creates an embedded Neo4j server for testing and development.
     * 
     * @return The embedded Neo4j server.
     */
    @Bean
    public Neo4j embeddedDatabaseServer() {
        return Neo4jBuilders.newInProcessBuilder()
                .withDisabledServer()
                .build();
    }

    /**
     * Creates a Neo4j driver bean that connects to the embedded server.
     * 
     * @return The Neo4j driver.
     */
    @Bean
    @Override
    public Driver driver() {
        var embeddedDatabaseServer = embeddedDatabaseServer();
        return GraphDatabase.driver(embeddedDatabaseServer.boltURI(), 
                AuthTokens.none());
    }
}