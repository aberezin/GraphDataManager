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
        validateNode(node);
        return nodeRepository.save(node);
    }
    
    @Transactional
    public Node updateNode(String id, Node nodeDetails) {
        validateNode(nodeDetails);
        
        return nodeRepository.findById(id)
                .map(existingNode -> {
                    if (nodeDetails.getType() != null) {
                        existingNode.setType(nodeDetails.getType());
                    }
                    if (nodeDetails.getLabel() != null) {
                        existingNode.setLabel(nodeDetails.getLabel());
                    }
                    if (nodeDetails.getAdditionalLabels() != null) {
                        existingNode.setAdditionalLabels(nodeDetails.getAdditionalLabels());
                    }
                    if (nodeDetails.getProperties() != null) {
                        existingNode.setProperties(nodeDetails.getProperties());
                    }
                    
                    return nodeRepository.save(existingNode);
                })
                .orElseThrow(() -> new IllegalArgumentException("Node not found with id: " + id));
    }
    
    @Transactional
    public void deleteNode(String id) {
        Node node = nodeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Node not found with id: " + id));
        
        // Delete relationships involving this node
        List<Relationship> relationships = relationshipRepository.findAll();
        relationships.stream()
                .filter(relationship -> 
                        (relationship.getSource() != null && relationship.getSource().getId().equals(id)) ||
                        (relationship.getTarget() != null && relationship.getTarget().getId().equals(id)))
                .forEach(relationshipRepository::delete);
        
        nodeRepository.delete(node);
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
        validateRelationship(relationship);
        
        // Make sure source and target nodes exist
        Node source = nodeRepository.findById(relationship.getSource().getId())
                .orElseThrow(() -> new IllegalArgumentException("Source node not found with id: " + relationship.getSource().getId()));
        
        Node target = nodeRepository.findById(relationship.getTarget().getId())
                .orElseThrow(() -> new IllegalArgumentException("Target node not found with id: " + relationship.getTarget().getId()));
        
        relationship.setSource(source);
        relationship.setTarget(target);
        
        return relationshipRepository.save(relationship);
    }
    
    @Transactional
    public Relationship updateRelationship(String id, Relationship relationshipDetails) {
        return relationshipRepository.findById(id)
                .map(existingRelationship -> {
                    if (relationshipDetails.getType() != null) {
                        existingRelationship.setType(relationshipDetails.getType());
                    }
                    
                    if (relationshipDetails.getProperties() != null) {
                        existingRelationship.setProperties(relationshipDetails.getProperties());
                    }
                    
                    if (relationshipDetails.getSource() != null && relationshipDetails.getSource().getId() != null) {
                        Node source = nodeRepository.findById(relationshipDetails.getSource().getId())
                                .orElseThrow(() -> new IllegalArgumentException("Source node not found with id: " + relationshipDetails.getSource().getId()));
                        existingRelationship.setSource(source);
                    }
                    
                    if (relationshipDetails.getTarget() != null && relationshipDetails.getTarget().getId() != null) {
                        Node target = nodeRepository.findById(relationshipDetails.getTarget().getId())
                                .orElseThrow(() -> new IllegalArgumentException("Target node not found with id: " + relationshipDetails.getTarget().getId()));
                        existingRelationship.setTarget(target);
                    }
                    
                    return relationshipRepository.save(existingRelationship);
                })
                .orElseThrow(() -> new IllegalArgumentException("Relationship not found with id: " + id));
    }
    
    @Transactional
    public void deleteRelationship(String id) {
        Relationship relationship = relationshipRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Relationship not found with id: " + id));
        
        relationshipRepository.delete(relationship);
    }
    
    // Search operations
    public List<Node> searchNodes(String query) {
        return nodeRepository.searchNodes(query);
    }
    
    public List<Relationship> searchRelationships(String query) {
        return relationshipRepository.searchRelationships(query);
    }
    
    public Map<String, Object> getGraphVisualizationData() {
        List<Node> nodes = nodeRepository.findAll();
        List<Relationship> relationships = relationshipRepository.findAllRelationshipsWithNodes();
        
        Map<String, Object> result = new HashMap<>();
        result.put("nodes", nodes);
        result.put("relationships", relationships);
        
        return result;
    }
    
    // Validation methods
    private void validateNode(Node node) {
        if (node.getType() == null || node.getType().isEmpty()) {
            throw new IllegalArgumentException("Node type cannot be empty");
        }
    }
    
    private void validateRelationship(Relationship relationship) {
        if (relationship.getType() == null || relationship.getType().isEmpty()) {
            throw new IllegalArgumentException("Relationship type cannot be empty");
        }
        
        if (relationship.getSource() == null || relationship.getSource().getId() == null) {
            throw new IllegalArgumentException("Source node must be specified");
        }
        
        if (relationship.getTarget() == null || relationship.getTarget().getId() == null) {
            throw new IllegalArgumentException("Target node must be specified");
        }
    }
}