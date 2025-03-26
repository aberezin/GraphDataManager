package com.graphapp.config;

import org.neo4j.driver.Driver;
import org.neo4j.harness.Neo4j;
import org.neo4j.harness.Neo4jBuilders;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.neo4j.core.transaction.Neo4jTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import static org.neo4j.driver.GraphDatabase.driver;

/**
 * Configuration class for Neo4j database in development mode.
 * This configuration uses an embedded Neo4j instance for development and testing.
 */
@Configuration
@Profile("dev")
public class MockNeo4jConfig {

    /**
     * Creates an embedded Neo4j server for development and testing.
     * @return A Neo4j instance
     */
    @Bean(destroyMethod = "close")
    public Neo4j neo4j() {
        return Neo4jBuilders.newInProcessBuilder()
                .withDisabledServer()
                .withFixture(
                        "CREATE (n:Person:Node {name: 'Alice', age: 30})-[:KNOWS]->(m:Person:Node {name: 'Bob', age: 25})," +
                        "(n)-[:WORKS_WITH]->(o:Person:Node {name: 'Charlie', age: 35})," +
                        "(m)-[:LIVES_IN]->(p:City:Node {name: 'New York', population: 8000000})," +
                        "(o)-[:LIVES_IN]->(q:City:Node {name: 'San Francisco', population: 900000})," +
                        "(p)-[:LOCATED_IN]->(r:Country:Node {name: 'USA', code: 'US'})," +
                        "(q)-[:LOCATED_IN]->(r)")
                .build();
    }

    /**
     * Creates a Neo4j driver bean using the embedded Neo4j server.
     * @param neo4j The embedded Neo4j server
     * @return A configured Neo4j Driver
     */
    @Bean
    public Driver driver(Neo4j neo4j) {
        return driver(neo4j.boltURI());
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