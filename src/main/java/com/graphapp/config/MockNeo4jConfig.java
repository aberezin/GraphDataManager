package com.graphapp.config;

import com.graphapp.model.graph.Node;
import com.graphapp.model.graph.Relationship;
import com.graphapp.repository.graph.NodeRepository;
import com.graphapp.repository.graph.RelationshipRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

import java.util.*;
import java.util.stream.Collectors;

@Configuration
@EnableNeo4jRepositories(basePackages = "com.graphapp.repository.graph")
@Profile("dev")
public class MockNeo4jConfig {
    
    // Mock implementation of NodeRepository for development
    @Bean
    @Primary
    public NodeRepository nodeRepository() {
        return new MockNodeRepository();
    }
    
    // Mock implementation of RelationshipRepository for development
    @Bean
    @Primary
    public RelationshipRepository relationshipRepository() {
        return new MockRelationshipRepository();
    }
    
    // In-memory implementation of NodeRepository
    private static class MockNodeRepository implements NodeRepository {
        private final Map<String, Node> nodes = new HashMap<>();
        private int idCounter = 1;
        
        public MockNodeRepository() {
            // Initialize with some sample nodes
            Node person1 = new Node("Person");
            person1.setLabel("John Smith");
            Map<String, Object> props1 = new HashMap<>();
            props1.put("age", 32);
            props1.put("occupation", "Developer");
            person1.setProperties(props1);
            
            Node person2 = new Node("Person");
            person2.setLabel("Jane Doe");
            Map<String, Object> props2 = new HashMap<>();
            props2.put("age", 28);
            props2.put("occupation", "Designer");
            person2.setProperties(props2);
            
            Node company = new Node("Organization");
            company.setLabel("ACME Corp");
            Map<String, Object> props3 = new HashMap<>();
            props3.put("founded", 1985);
            props3.put("industry", "Technology");
            company.setProperties(props3);
            
            Node project = new Node("Project");
            project.setLabel("Graph Database App");
            Map<String, Object> props4 = new HashMap<>();
            props4.put("startDate", "2023-01-15");
            props4.put("priority", "High");
            project.setProperties(props4);
            
            save(person1);
            save(person2);
            save(company);
            save(project);
        }
        
        @Override
        public List<Node> findAll() {
            return new ArrayList<>(nodes.values());
        }
        
        @Override
        public Optional<Node> findById(String id) {
            return Optional.ofNullable(nodes.get(id));
        }
        
        @Override
        public Node save(Node node) {
            if (node.getId() == null) {
                node.setId(String.valueOf(idCounter++));
            }
            nodes.put(node.getId(), node);
            return node;
        }
        
        @Override
        public void delete(Node node) {
            if (node.getId() != null) {
                nodes.remove(node.getId());
            }
        }
        
        @Override
        public boolean existsById(String id) {
            return nodes.containsKey(id);
        }
        
        @Override
        public List<Node> findByType(String type) {
            return nodes.values().stream()
                    .filter(node -> type.equals(node.getType()))
                    .collect(Collectors.toList());
        }
        
        @Override
        public List<Node> findByLabel(String label) {
            return nodes.values().stream()
                    .filter(node -> label.equals(node.getLabel()))
                    .collect(Collectors.toList());
        }
        
        @Override
        public List<Node> searchNodes(String query) {
            String lowerQuery = query.toLowerCase();
            return nodes.values().stream()
                    .filter(node -> (node.getLabel() != null && node.getLabel().toLowerCase().contains(lowerQuery)) ||
                            (node.getType() != null && node.getType().toLowerCase().contains(lowerQuery)) ||
                            (node.getProperties() != null && node.getProperties().values().stream()
                                    .anyMatch(value -> value != null && value.toString().toLowerCase().contains(lowerQuery))))
                    .collect(Collectors.toList());
        }
    }
    
    // In-memory implementation of RelationshipRepository
    private static class MockRelationshipRepository implements RelationshipRepository {
        private final Map<String, Relationship> relationships = new HashMap<>();
        private int idCounter = 1;
        
        public MockRelationshipRepository() {
            // Sample relationships will be created after nodes are loaded
        }
        
        @Override
        public List<Relationship> findAll() {
            return new ArrayList<>(relationships.values());
        }
        
        @Override
        public Optional<Relationship> findById(String id) {
            return Optional.ofNullable(relationships.get(id));
        }
        
        @Override
        public Relationship save(Relationship relationship) {
            if (relationship.getId() == null) {
                relationship.setId(String.valueOf(idCounter++));
            }
            relationships.put(relationship.getId(), relationship);
            return relationship;
        }
        
        @Override
        public void delete(Relationship relationship) {
            if (relationship.getId() != null) {
                relationships.remove(relationship.getId());
            }
        }
        
        @Override
        public List<Relationship> findByType(String type) {
            return relationships.values().stream()
                    .filter(rel -> type.equals(rel.getType()))
                    .collect(Collectors.toList());
        }
        
        @Override
        public List<Relationship> findAllRelationshipsWithNodes() {
            return new ArrayList<>(relationships.values());
        }
        
        @Override
        public List<Relationship> searchRelationships(String query) {
            String lowerQuery = query.toLowerCase();
            return relationships.values().stream()
                    .filter(rel -> (rel.getType() != null && rel.getType().toLowerCase().contains(lowerQuery)) ||
                            (rel.getProperties() != null && rel.getProperties().values().stream()
                                    .anyMatch(value -> value != null && value.toString().toLowerCase().contains(lowerQuery))) ||
                            (rel.getSource() != null && rel.getSource().getLabel() != null && 
                                    rel.getSource().getLabel().toLowerCase().contains(lowerQuery)) ||
                            (rel.getTarget() != null && rel.getTarget().getLabel() != null && 
                                    rel.getTarget().getLabel().toLowerCase().contains(lowerQuery)))
                    .collect(Collectors.toList());
        }
    }
}