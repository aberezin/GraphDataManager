package com.graphapp.config;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.neo4j.config.AbstractNeo4jConfig;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Neo4j configuration for production environment.
 * This class configures the connection to a real Neo4j database.
 */
@Configuration
@EnableNeo4jRepositories(basePackages = "com.graphapp.repository.graph")
@EnableTransactionManagement
@Profile("!dev")
public class Neo4jConfig extends AbstractNeo4jConfig {

    @Value("${spring.neo4j.uri}")
    private String uri;

    @Value("${spring.neo4j.authentication.username}")
    private String username;

    @Value("${spring.neo4j.authentication.password}")
    private String password;

    /**
     * Creates a Neo4j driver bean with the configured connection details.
     * 
     * @return The Neo4j driver.
     */
    @Bean
    @Override
    public Driver driver() {
        return GraphDatabase.driver(uri, AuthTokens.basic(username, password));
    }
}