package com.graphapp.service;

import com.graphapp.model.graph.Node;
import com.graphapp.model.graph.Relationship;
import com.graphapp.repository.graph.NodeRepository;
import com.graphapp.repository.graph.RelationshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class GraphDataService {
    
    private final NodeRepository nodeRepository;
    private final RelationshipRepository relationshipRepository;
    
    @Autowired
    public GraphDataService(NodeRepository nodeRepository, RelationshipRepository relationshipRepository) {
        this.nodeRepository = nodeRepository;
        this.relationshipRepository = relationshipRepository;
    }
    
    // Node operations
    public List<Node> getAllNodes() {
        return nodeRepository.findAll();
    }
    
    public Optional<Node> getNodeById(String id) {
        return nodeRepository.findById(id);
    }
    
    public List<Node> getNodesByType(String type) {
        return nodeRepository.findByType(type);
    }
    
    public List<Node> getNodesByLabel(String label) {
        return nodeRepository.findByLabel(label);
    }
    
    @Transactional
    public Node createNode(Node node) {
        // Validation logic can be added here
        if (node.getLabel() == null || node.getLabel().trim().isEmpty()) {
            throw new IllegalArgumentException("Node label cannot be empty");
        }
        return nodeRepository.save(node);
    }
    
    @Transactional
    public Optional<Node> updateNode(String id, Node nodeDetails) {
        return nodeRepository.findById(id)
                .map(existingNode -> {
                    if (nodeDetails.getLabel() != null) {
                        existingNode.setLabel(nodeDetails.getLabel());
                    }
                    if (nodeDetails.getType() != null) {
                        existingNode.setType(nodeDetails.getType());
                    }
                    if (nodeDetails.getAdditionalLabels() != null) {
                        existingNode.setAdditionalLabels(nodeDetails.getAdditionalLabels());
                    }
                    if (nodeDetails.getProperties() != null) {
                        existingNode.setProperties(nodeDetails.getProperties());
                    }
                    return nodeRepository.save(existingNode);
                });
    }
    
    @Transactional
    public boolean deleteNode(String id) {
        return nodeRepository.findById(id)
                .map(node -> {
                    nodeRepository.delete(node);
                    return true;
                })
                .orElse(false);
    }
    
    // Relationship operations
    public List<Relationship> getAllRelationships() {
        return relationshipRepository.findAll();
    }
    
    public Optional<Relationship> getRelationshipById(String id) {
        return relationshipRepository.findById(id);
    }
    
    public List<Relationship> getRelationshipsByType(String type) {
        return relationshipRepository.findByType(type);
    }
    
    @Transactional
    public Relationship createRelationship(Relationship relationship) {
        // Validation logic can be added here
        if (relationship.getType() == null || relationship.getType().trim().isEmpty()) {
            throw new IllegalArgumentException("Relationship type cannot be empty");
        }
        if (relationship.getSource() == null || relationship.getTarget() == null) {
            throw new IllegalArgumentException("Source and target nodes must be specified");
        }
        
        // Ensure source and target nodes exist
        Node source = relationship.getSource();
        Node target = relationship.getTarget();
        
        if (source.getId() == null) {
            source = nodeRepository.save(source);
            relationship.setSource(source);
        } else if (!nodeRepository.existsById(source.getId())) {
            throw new IllegalArgumentException("Source node not found: " + source.getId());
        }
        
        if (target.getId() == null) {
            target = nodeRepository.save(target);
            relationship.setTarget(target);
        } else if (!nodeRepository.existsById(target.getId())) {
            throw new IllegalArgumentException("Target node not found: " + target.getId());
        }
        
        return relationshipRepository.save(relationship);
    }
    
    @Transactional
    public Optional<Relationship> updateRelationship(String id, Relationship relationshipDetails) {
        return relationshipRepository.findById(id)
                .map(existingRelationship -> {
                    if (relationshipDetails.getType() != null) {
                        existingRelationship.setType(relationshipDetails.getType());
                    }
                    if (relationshipDetails.getProperties() != null) {
                        existingRelationship.setProperties(relationshipDetails.getProperties());
                    }
                    // Source and target nodes are typically not updated
                    return relationshipRepository.save(existingRelationship);
                });
    }
    
    @Transactional
    public boolean deleteRelationship(String id) {
        return relationshipRepository.findById(id)
                .map(relationship -> {
                    relationshipRepository.delete(relationship);
                    return true;
                })
                .orElse(false);
    }
    
    // Graph visualization data
    public Map<String, Object> getGraphVisualizationData() {
        List<Node> nodes = nodeRepository.findAll();
        List<Relationship> relationships = relationshipRepository.findAllRelationshipsWithNodes();
        
        Map<String, Object> graphData = new HashMap<>();
        graphData.put("nodes", nodes);
        graphData.put("relationships", relationships);
        
        return graphData;
    }
    
    // Search operations
    public Map<String, Object> searchGraph(String query) {
        List<Node> nodes = nodeRepository.searchNodes(query);
        List<Relationship> relationships = relationshipRepository.searchRelationships(query);
        
        Map<String, Object> searchResults = new HashMap<>();
        searchResults.put("nodes", nodes);
        searchResults.put("relationships", relationships);
        
        return searchResults;
    }
}