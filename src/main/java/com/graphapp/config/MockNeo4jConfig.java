package com.graphapp.config;

import org.mockito.Mockito;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.neo4j.driver.Result;
import org.neo4j.driver.Record;
import org.neo4j.driver.Transaction;
import org.neo4j.driver.Value;
import org.neo4j.driver.AccessMode;
import org.neo4j.driver.Query;
import org.neo4j.driver.SessionConfig;
import org.neo4j.driver.exceptions.Neo4jException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.neo4j.config.AbstractNeo4jConfig;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

/**
 * Mock configuration for Neo4j database (Development).
 * This creates a mocked version of Neo4j driver for lightweight development.
 */
@Configuration
@EnableNeo4jRepositories(basePackages = "com.graphapp.repository.graph")
@EnableTransactionManagement
@Profile({"default", "dev"})
public class MockNeo4jConfig extends AbstractNeo4jConfig {
    
    /**
     * Create a mocked Neo4j driver.
     * This is a lightweight alternative to a real embedded Neo4j server.
     * 
     * @return A mocked Neo4j driver.
     */
    @Bean
    @Override
    public Driver driver() {
        Driver mockDriver = Mockito.mock(Driver.class);
        Session mockSession = Mockito.mock(Session.class);
        Transaction mockTransaction = Mockito.mock(Transaction.class);
        Result mockResult = Mockito.mock(Result.class);
        
        // Configure the mock session
        Mockito.when(mockDriver.session()).thenReturn(mockSession);
        Mockito.when(mockDriver.session(Mockito.any(SessionConfig.class))).thenReturn(mockSession);
        
        // Configure the mock transaction
        Mockito.when(mockSession.beginTransaction()).thenReturn(mockTransaction);
        
        // Configure the mock result
        Mockito.when(mockTransaction.run(Mockito.anyString())).thenReturn(mockResult);
        Mockito.when(mockTransaction.run(Mockito.anyString(), Mockito.anyMap())).thenReturn(mockResult);
        Mockito.when(mockTransaction.run(Mockito.any(Query.class))).thenReturn(mockResult);
        
        Mockito.when(mockSession.run(Mockito.anyString())).thenReturn(mockResult);
        Mockito.when(mockSession.run(Mockito.anyString(), Mockito.anyMap())).thenReturn(mockResult);
        Mockito.when(mockSession.run(Mockito.any(Query.class))).thenReturn(mockResult);
        
        // Configure basic result behavior (empty results)
        Mockito.when(mockResult.hasNext()).thenReturn(false);
        Mockito.when(mockResult.list()).thenReturn(Collections.emptyList());
        
        // Configure basic completion stages for async operations
        CompletionStage<Transaction> txStage = CompletableFuture.completedFuture(mockTransaction);
        CompletionStage<Session> sessionStage = CompletableFuture.completedFuture(mockSession);
        CompletionStage<Void> voidStage = CompletableFuture.completedFuture(null);
        
        Mockito.when(mockSession.closeAsync()).thenReturn(voidStage);
        Mockito.when(mockTransaction.commitAsync()).thenReturn(voidStage);
        Mockito.when(mockTransaction.rollbackAsync()).thenReturn(voidStage);
        
        System.out.println("Created mock Neo4j driver for development");
        return mockDriver;
    }
}