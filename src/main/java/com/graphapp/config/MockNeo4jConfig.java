package com.graphapp.config;

import org.neo4j.driver.Driver;
import org.neo4j.driver.SessionConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.data.neo4j.core.transaction.Neo4jTransactionManager;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Mock Neo4j configuration for development purposes
 * This avoids the need for a real Neo4j database in development
 */
@Configuration
@Profile("dev")
public class MockNeo4jConfig {

    private Map<String, Object> inMemoryDb = new HashMap<>();
    private long nodeIdCounter = 0;
    private long relationshipIdCounter = 0;

    @Bean
    @Primary
    public Driver mockNeo4jDriver() {
        return new MockDriver(inMemoryDb);
    }

    @Bean
    @Primary
    public Neo4jClient mockNeo4jClient(Driver driver) {
        return Neo4jClient.create(driver);
    }

    @Bean
    @Primary
    public Neo4jTemplate mockNeo4jTemplate(Neo4jClient client) {
        return new Neo4jTemplate(client);
    }

    @Bean
    @Primary
    public Neo4jTransactionManager mockNeo4jTransactionManager(Driver driver) {
        return new Neo4jTransactionManager(driver);
    }

    // Mock implementation of Neo4j Driver for development
    private static class MockDriver implements Driver {
        private final Map<String, Object> database;

        public MockDriver(Map<String, Object> database) {
            this.database = database;
            // Initialize with some test data
            initializeTestData();
        }

        private void initializeTestData() {
            // Initialize nodes
            Map<String, Object> nodes = new HashMap<>();
            
            // Create some sample nodes
            Map<String, Object> node1 = new HashMap<>();
            node1.put("id", 1L);
            node1.put("label", "Person");
            node1.put("type", "Person");
            Map<String, Object> node1Props = new HashMap<>();
            node1Props.put("name", "John Doe");
            node1Props.put("age", 30);
            node1.put("properties", node1Props);
            nodes.put("1", node1);
            
            Map<String, Object> node2 = new HashMap<>();
            node2.put("id", 2L);
            node2.put("label", "Person");
            node2.put("type", "Person");
            Map<String, Object> node2Props = new HashMap<>();
            node2Props.put("name", "Jane Smith");
            node2Props.put("age", 28);
            node2.put("properties", node2Props);
            nodes.put("2", node2);
            
            Map<String, Object> node3 = new HashMap<>();
            node3.put("id", 3L);
            node3.put("label", "Project");
            node3.put("type", "Project");
            Map<String, Object> node3Props = new HashMap<>();
            node3Props.put("name", "Graph Database Project");
            node3Props.put("description", "A project using Neo4j");
            node3.put("properties", node3Props);
            nodes.put("3", node3);
            
            // Initialize relationships
            Map<String, Object> relationships = new HashMap<>();
            
            // Create some sample relationships
            Map<String, Object> rel1 = new HashMap<>();
            rel1.put("id", 1L);
            rel1.put("type", "WORKS_ON");
            rel1.put("source", node1);
            rel1.put("target", node3);
            Map<String, Object> rel1Props = new HashMap<>();
            rel1Props.put("role", "Developer");
            rel1Props.put("since", "2023-01-01");
            rel1.put("properties", rel1Props);
            relationships.put("1", rel1);
            
            Map<String, Object> rel2 = new HashMap<>();
            rel2.put("id", 2L);
            rel2.put("type", "WORKS_ON");
            rel2.put("source", node2);
            rel2.put("target", node3);
            Map<String, Object> rel2Props = new HashMap<>();
            rel2Props.put("role", "Designer");
            rel2Props.put("since", "2023-02-15");
            rel2.put("properties", rel2Props);
            relationships.put("2", rel2);
            
            Map<String, Object> rel3 = new HashMap<>();
            rel3.put("id", 3L);
            rel3.put("type", "KNOWS");
            rel3.put("source", node1);
            rel3.put("target", node2);
            Map<String, Object> rel3Props = new HashMap<>();
            rel3Props.put("since", "2022-10-10");
            rel3.put("properties", rel3Props);
            relationships.put("3", rel3);
            
            // Store in the database map
            database.put("nodes", nodes);
            database.put("relationships", relationships);
            database.put("nodeIdCounter", 3L);
            database.put("relationshipIdCounter", 3L);
        }

        @Override
        public org.neo4j.driver.Session session() {
            throw new UnsupportedOperationException("Mock driver does not implement this method");
        }

        @Override
        public org.neo4j.driver.Session session(SessionConfig sessionConfig) {
            throw new UnsupportedOperationException("Mock driver does not implement this method");
        }

        @Override
        public org.neo4j.driver.RxSession rxSession() {
            throw new UnsupportedOperationException("Mock driver does not implement this method");
        }

        @Override
        public org.neo4j.driver.RxSession rxSession(SessionConfig sessionConfig) {
            throw new UnsupportedOperationException("Mock driver does not implement this method");
        }

        @Override
        public org.neo4j.driver.AsyncSession asyncSession() {
            throw new UnsupportedOperationException("Mock driver does not implement this method");
        }

        @Override
        public org.neo4j.driver.AsyncSession asyncSession(SessionConfig sessionConfig) {
            throw new UnsupportedOperationException("Mock driver does not implement this method");
        }

        @Override
        public void close() {
            // No resources to release
        }

        @Override
        public boolean isEncrypted() {
            return false;
        }

        @Override
        public boolean isMetricsEnabled() {
            return false;
        }

        @Override
        public org.neo4j.driver.Metrics metrics() {
            return null;
        }

        @Override
        public org.neo4j.driver.ConnectionPoolMetrics connectionPoolMetrics() {
            return null;
        }

        @Override
        public org.neo4j.driver.reactive.RxSession rxSession(org.neo4j.driver.SessionConfig sessionConfig) {
            return null;
        }

        @Override
        public org.neo4j.driver.reactive.RxSession rxSession() {
            return null;
        }

        @Override
        public org.neo4j.driver.reactivestreams.ReactiveSession reactiveSession() {
            return null;
        }

        @Override
        public org.neo4j.driver.reactivestreams.ReactiveSession reactiveSession(org.neo4j.driver.SessionConfig sessionConfig) {
            return null;
        }

        @Override
        public boolean verifyConnectivity() {
            return true;
        }

        @Override
        public org.neo4j.driver.ExecutableQuery executableQuery(String query) {
            return null;
        }
    }
}