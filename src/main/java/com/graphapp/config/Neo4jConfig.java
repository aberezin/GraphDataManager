package com.graphapp.config;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.data.neo4j.core.transaction.Neo4jTransactionManager;

/**
 * Configuration for Neo4j graph database
 */
@Configuration
@EnableNeo4jRepositories(basePackages = "com.graphapp.repository.graph")
public class Neo4jConfig {

    @Value("${spring.neo4j.uri:bolt://localhost:7687}")
    private String uri;
    
    @Value("${spring.neo4j.authentication.username:neo4j}")
    private String username;
    
    @Value("${spring.neo4j.authentication.password:password}")
    private String password;

    @Bean
    public Driver driver() {
        return GraphDatabase.driver(uri, AuthTokens.basic(username, password));
    }

    @Bean
    public Neo4jClient neo4jClient(Driver driver) {
        return Neo4jClient.create(driver);
    }

    @Bean
    public Neo4jTemplate neo4jTemplate(Neo4jClient neo4jClient) {
        return new Neo4jTemplate(neo4jClient);
    }

    @Bean
    public Neo4jTransactionManager transactionManager(Driver driver) {
        return new Neo4jTransactionManager(driver);
    }
}
