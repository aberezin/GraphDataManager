package com.graphapp.config;

import com.graphapp.model.graph.Node;
import com.graphapp.model.graph.Relationship;
import com.graphapp.repository.graph.NodeRepository;
import com.graphapp.repository.graph.RelationshipRepository;
import org.neo4j.driver.Driver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.neo4j.core.DatabaseSelectionProvider;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.data.neo4j.core.transaction.Neo4jTransactionManager;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Configuration
@EnableNeo4jRepositories(basePackages = "com.graphapp.repository.graph")
@EnableTransactionManagement
@Profile("dev")
public class MockNeo4jConfig {

    @Bean
    @Primary
    public NodeRepository mockNodeRepository() {
        return new MockNodeRepository();
    }

    @Bean
    @Primary
    public RelationshipRepository mockRelationshipRepository() {
        return new MockRelationshipRepository();
    }

    // Mock implementation of NodeRepository
    public static class MockNodeRepository implements NodeRepository {
        private final Map<String, Node> nodes = new HashMap<>();
        private int idCounter = 1;

        public MockNodeRepository() {
            // Initialize with some sample data
            createSampleNodes();
        }

        private void createSampleNodes() {
            // Create sample nodes for development testing
            Node person1 = new Node("John Doe", "Person");
            person1.addProperty("age", 30);
            person1.addProperty("occupation", "Software Engineer");
            save(person1);

            Node person2 = new Node("Jane Smith", "Person");
            person2.addProperty("age", 28);
            person2.addProperty("occupation", "Data Scientist");
            save(person2);

            Node company1 = new Node("Acme Corp", "Company");
            company1.addProperty("industry", "Technology");
            company1.addProperty("founded", 2010);
            save(company1);

            Node project1 = new Node("Graph Database Project", "Project");
            project1.addProperty("status", "In Progress");
            project1.addProperty("priority", "High");
            save(project1);
        }

        @Override
        public List<Node> findByType(String type) {
            List<Node> result = new ArrayList<>();
            for (Node node : nodes.values()) {
                if (node.getType() != null && node.getType().equals(type)) {
                    result.add(node);
                }
            }
            return result;
        }

        @Override
        public List<Node> findByLabel(String label) {
            List<Node> result = new ArrayList<>();
            for (Node node : nodes.values()) {
                if (node.getLabel() != null && node.getLabel().equals(label)) {
                    result.add(node);
                }
            }
            return result;
        }

        @Override
        public List<Node> searchNodes(String searchTerm) {
            List<Node> result = new ArrayList<>();
            for (Node node : nodes.values()) {
                if ((node.getLabel() != null && node.getLabel().contains(searchTerm)) ||
                    (node.getType() != null && node.getType().contains(searchTerm))) {
                    result.add(node);
                }
            }
            return result;
        }

        @Override
        public List<Node> findNodesWithRelationships() {
            // In a mock implementation, we'll just return all nodes
            return findAll();
        }

        @Override
        public <S extends Node> S save(S entity) {
            if (entity.getId() == null) {
                entity.setId(String.valueOf(idCounter++));
            }
            nodes.put(entity.getId(), entity);
            return entity;
        }

        @Override
        public <S extends Node> List<S> saveAll(Iterable<S> entities) {
            List<S> result = new ArrayList<>();
            for (S entity : entities) {
                result.add(save(entity));
            }
            return result;
        }

        @Override
        public Optional<Node> findById(String id) {
            return Optional.ofNullable(nodes.get(id));
        }

        @Override
        public boolean existsById(String id) {
            return nodes.containsKey(id);
        }

        @Override
        public List<Node> findAll() {
            return new ArrayList<>(nodes.values());
        }

        @Override
        public Iterable<Node> findAllById(Iterable<String> ids) {
            List<Node> result = new ArrayList<>();
            for (String id : ids) {
                Node node = nodes.get(id);
                if (node != null) {
                    result.add(node);
                }
            }
            return result;
        }

        @Override
        public long count() {
            return nodes.size();
        }

        @Override
        public void deleteById(String id) {
            nodes.remove(id);
        }

        @Override
        public void delete(Node entity) {
            if (entity.getId() != null) {
                nodes.remove(entity.getId());
            }
        }

        @Override
        public void deleteAllById(Iterable<? extends String> ids) {
            for (String id : ids) {
                nodes.remove(id);
            }
        }

        @Override
        public void deleteAll(Iterable<? extends Node> entities) {
            for (Node entity : entities) {
                delete(entity);
            }
        }

        @Override
        public void deleteAll() {
            nodes.clear();
        }
    }

    // Mock implementation of RelationshipRepository
    public static class MockRelationshipRepository implements RelationshipRepository {
        private final Map<String, Relationship> relationships = new HashMap<>();
        private int idCounter = 1;

        public MockRelationshipRepository() {
            // We'll add sample relationships after nodes are created
        }

        @Override
        public List<Relationship> findByType(String type) {
            List<Relationship> result = new ArrayList<>();
            for (Relationship relationship : relationships.values()) {
                if (relationship.getType() != null && relationship.getType().equals(type)) {
                    result.add(relationship);
                }
            }
            return result;
        }

        @Override
        public List<Relationship> searchRelationships(String searchTerm) {
            List<Relationship> result = new ArrayList<>();
            for (Relationship relationship : relationships.values()) {
                if (relationship.getType() != null && relationship.getType().contains(searchTerm)) {
                    result.add(relationship);
                }
            }
            return result;
        }

        @Override
        public List<Relationship> findAllRelationshipsWithNodes() {
            return findAll();
        }

        @Override
        public List<Relationship> findRelationshipsBetweenNodes(String sourceId, String targetId) {
            List<Relationship> result = new ArrayList<>();
            for (Relationship relationship : relationships.values()) {
                if (relationship.getSource() != null && relationship.getTarget() != null &&
                    relationship.getSource().getId().equals(sourceId) &&
                    relationship.getTarget().getId().equals(targetId)) {
                    result.add(relationship);
                }
            }
            return result;
        }

        @Override
        public <S extends Relationship> S save(S entity) {
            if (entity.getId() == null) {
                entity.setId(String.valueOf(idCounter++));
            }
            relationships.put(entity.getId(), entity);
            return entity;
        }

        @Override
        public <S extends Relationship> List<S> saveAll(Iterable<S> entities) {
            List<S> result = new ArrayList<>();
            for (S entity : entities) {
                result.add(save(entity));
            }
            return result;
        }

        @Override
        public Optional<Relationship> findById(String id) {
            return Optional.ofNullable(relationships.get(id));
        }

        @Override
        public boolean existsById(String id) {
            return relationships.containsKey(id);
        }

        @Override
        public List<Relationship> findAll() {
            return new ArrayList<>(relationships.values());
        }

        @Override
        public Iterable<Relationship> findAllById(Iterable<String> ids) {
            List<Relationship> result = new ArrayList<>();
            for (String id : ids) {
                Relationship relationship = relationships.get(id);
                if (relationship != null) {
                    result.add(relationship);
                }
            }
            return result;
        }

        @Override
        public long count() {
            return relationships.size();
        }

        @Override
        public void deleteById(String id) {
            relationships.remove(id);
        }

        @Override
        public void delete(Relationship entity) {
            if (entity.getId() != null) {
                relationships.remove(entity.getId());
            }
        }

        @Override
        public void deleteAllById(Iterable<? extends String> ids) {
            for (String id : ids) {
                relationships.remove(id);
            }
        }

        @Override
        public void deleteAll(Iterable<? extends Relationship> entities) {
            for (Relationship entity : entities) {
                delete(entity);
            }
        }

        @Override
        public void deleteAll() {
            relationships.clear();
        }
    }
}