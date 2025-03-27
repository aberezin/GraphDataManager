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
 * Configuration for Neo4j database.
 * This config connects to a standalone Neo4j server (either external or locally run).
 */
@Configuration
@EnableNeo4jRepositories(basePackages = "com.graphapp.repository.graph")
@EnableTransactionManagement
@Profile({"prod", "dev", "default"})
public class Neo4jConfig extends AbstractNeo4jConfig {
    
    @Value("${spring.neo4j.uri:bolt://localhost:7687}")
    private String uri;
    
    @Value("${spring.neo4j.authentication.username:neo4j}")
    private String username;
    
    @Value("${spring.neo4j.authentication.password:password}")
    private String password;
    
    /**
     * Configure the Neo4j driver.
     * For development, this connects to the local standalone Neo4j server.
     * For production, it uses the configured connection parameters.
     * 
     * @return The Neo4j driver.
     */
    @Bean
    @Override
    public Driver driver() {
        // Use no authentication for local development server
        if (uri.contains("localhost")) {
            return GraphDatabase.driver(uri, AuthTokens.none());
        }
        
        // Use username/password authentication for external servers
        return GraphDatabase.driver(uri, AuthTokens.basic(username, password));
    }
}