package com.graphapp.config;

import org.mockito.Mockito;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.neo4j.driver.Result;
import org.neo4j.driver.Record;
import org.neo4j.driver.Value;
import org.neo4j.driver.internal.InternalNode;
import org.neo4j.driver.internal.InternalRelationship;
import org.neo4j.driver.types.Node;
import org.neo4j.driver.types.Relationship;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@EnableNeo4jRepositories(basePackages = "com.graphapp.repository.graph")
@Profile("dev") // This config is active only with dev profile
public class MockNeo4jConfig {

    private Map<Long, Node> mockNodes = new HashMap<>();
    private Map<Long, Relationship> mockRelationships = new HashMap<>();
    private long nodeIdCounter = 0;
    private long relationshipIdCounter = 0;

    @Bean
    @Primary
    public Driver mockNeo4jDriver() {
        Driver mockDriver = Mockito.mock(Driver.class);
        Session mockSession = Mockito.mock(Session.class);
        Result mockResult = Mockito.mock(Result.class);
        
        // Add some initial demo data
        initializeMockData();
        
        // Mock session creation
        Mockito.when(mockDriver.session()).thenReturn(mockSession);
        
        // Mock query execution
        Mockito.when(mockSession.run(Mockito.anyString(), Mockito.anyMap())).thenReturn(mockResult);
        
        // Mock result list
        List<Record> mockRecords = createMockRecords();
        Mockito.when(mockResult.list()).thenReturn(mockRecords);
        
        return mockDriver;
    }
    
    private void initializeMockData() {
        // Create mock nodes
        Map<String, Object> props1 = new HashMap<>();
        props1.put("name", "John Doe");
        props1.put("age", 30);
        createMockNode("Person", "Employee", props1);
        
        Map<String, Object> props2 = new HashMap<>();
        props2.put("name", "Project X");
        props2.put("priority", "High");
        createMockNode("Project", "Active", props2);
        
        Map<String, Object> props3 = new HashMap<>();
        props3.put("name", "Sarah Smith");
        props3.put("role", "Manager");
        createMockNode("Person", "Manager", props3);
        
        // Create mock relationships
        createMockRelationship(0L, 1L, "WORKS_ON", new HashMap<>());
        createMockRelationship(2L, 1L, "MANAGES", new HashMap<>());
    }
    
    private Node createMockNode(String type, String label, Map<String, Object> properties) {
        Long id = nodeIdCounter++;
        InternalNode node = Mockito.mock(InternalNode.class);
        
        Mockito.when(node.id()).thenReturn(id);
        Mockito.when(node.get("type")).thenReturn(Mockito.mock(Value.class));
        Mockito.when(node.get("type").asString()).thenReturn(type);
        Mockito.when(node.get("label")).thenReturn(Mockito.mock(Value.class));
        Mockito.when(node.get("label").asString()).thenReturn(label);
        
        for (Map.Entry<String, Object> entry : properties.entrySet()) {
            Mockito.when(node.get(entry.getKey())).thenReturn(Mockito.mock(Value.class));
            if (entry.getValue() instanceof String) {
                Mockito.when(node.get(entry.getKey()).asString()).thenReturn((String) entry.getValue());
            } else if (entry.getValue() instanceof Integer) {
                Mockito.when(node.get(entry.getKey()).asInt()).thenReturn((Integer) entry.getValue());
            }
        }
        
        mockNodes.put(id, node);
        return node;
    }
    
    private Relationship createMockRelationship(Long sourceId, Long targetId, String type, Map<String, Object> properties) {
        Long id = relationshipIdCounter++;
        InternalRelationship relationship = Mockito.mock(InternalRelationship.class);
        
        Mockito.when(relationship.id()).thenReturn(id);
        Mockito.when(relationship.startNodeId()).thenReturn(sourceId);
        Mockito.when(relationship.endNodeId()).thenReturn(targetId);
        Mockito.when(relationship.type()).thenReturn(type);
        
        for (Map.Entry<String, Object> entry : properties.entrySet()) {
            Mockito.when(relationship.get(entry.getKey())).thenReturn(Mockito.mock(Value.class));
            if (entry.getValue() instanceof String) {
                Mockito.when(relationship.get(entry.getKey()).asString()).thenReturn((String) entry.getValue());
            } else if (entry.getValue() instanceof Integer) {
                Mockito.when(relationship.get(entry.getKey()).asInt()).thenReturn((Integer) entry.getValue());
            }
        }
        
        mockRelationships.put(id, relationship);
        return relationship;
    }
    
    private List<Record> createMockRecords() {
        List<Record> records = new ArrayList<>();
        
        // Create mock records based on nodes and relationships
        for (Node node : mockNodes.values()) {
            Record mockRecord = Mockito.mock(Record.class);
            Mockito.when(mockRecord.get("n")).thenReturn(Mockito.mock(Value.class));
            Mockito.when(mockRecord.get("n").asNode()).thenReturn(node);
            records.add(mockRecord);
        }
        
        return records;
    }
}