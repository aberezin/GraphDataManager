package com.graphapp.config;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.data.neo4j.config.AbstractReactiveNeo4jConfig;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableNeo4jRepositories(basePackages = "com.graphapp.repository.graph")
@EnableTransactionManagement
@Profile("!dev") // This config is active when dev profile is not active
public class Neo4jConfig {

    private final Environment env;

    public Neo4jConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public Driver driver() {
        String uri = env.getProperty("spring.neo4j.uri");
        String username = env.getProperty("spring.neo4j.authentication.username");
        String password = env.getProperty("spring.neo4j.authentication.password");

        return GraphDatabase.driver(uri, AuthTokens.basic(username, password));
    }
}