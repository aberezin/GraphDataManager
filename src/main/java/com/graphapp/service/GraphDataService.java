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

/**
 * Service class for operations related to graph data.
 */
@Service
public class GraphDataService {

    private final NodeRepository nodeRepository;
    private final RelationshipRepository relationshipRepository;

    @Autowired
    public GraphDataService(NodeRepository nodeRepository, RelationshipRepository relationshipRepository) {
        this.nodeRepository = nodeRepository;
        this.relationshipRepository = relationshipRepository;
    }

    /**
     * Get all nodes.
     * @return List of all nodes
     */
    public List<Node> getAllNodes() {
        return nodeRepository.findAll();
    }

    /**
     * Get a node by ID.
     * @param id ID of the node to find
     * @return Optional containing the node if found
     */
    public Optional<Node> getNodeById(Long id) {
        return nodeRepository.findById(id);
    }

    /**
     * Create a new node.
     * @param node Node to create
     * @return The created node
     */
    @Transactional
    public Node createNode(Node node) {
        validateNode(node);
        return nodeRepository.save(node);
    }

    /**
     * Update an existing node.
     * @param id ID of the node to update
     * @param nodeDetails Updated node details
     * @return The updated node
     * @throws RuntimeException if the node does not exist
     */
    @Transactional
    public Node updateNode(Long id, Node nodeDetails) {
        Node node = nodeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Node not found with id: " + id));

        validateNode(nodeDetails);
        
        node.setLabel(nodeDetails.getLabel());
        node.setType(nodeDetails.getType());
        node.setProperties(nodeDetails.getProperties());
        
        return nodeRepository.save(node);
    }

    /**
     * Delete a node by ID.
     * @param id ID of the node to delete
     * @throws RuntimeException if the node does not exist
     */
    @Transactional
    public void deleteNode(Long id) {
        Node node = nodeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Node not found with id: " + id));
        
        // Find and delete all relationships associated with this node
        List<Relationship> sourceRelationships = relationshipRepository.findBySourceNodeId(id);
        List<Relationship> targetRelationships = relationshipRepository.findByTargetNodeId(id);
        
        relationshipRepository.deleteAll(sourceRelationships);
        relationshipRepository.deleteAll(targetRelationships);
        
        nodeRepository.delete(node);
    }

    /**
     * Search nodes by a query string.
     * @param query Query string to search for
     * @return List of nodes matching the search query
     */
    public List<Node> searchNodes(String query) {
        return nodeRepository.searchNodes(query);
    }

    /**
     * Get all relationships.
     * @return List of all relationships
     */
    public List<Relationship> getAllRelationships() {
        return relationshipRepository.findAll();
    }

    /**
     * Get a relationship by ID.
     * @param id ID of the relationship to find
     * @return Optional containing the relationship if found
     */
    public Optional<Relationship> getRelationshipById(Long id) {
        return relationshipRepository.findById(id);
    }

    /**
     * Create a new relationship.
     * @param relationship Relationship to create
     * @return The created relationship
     * @throws RuntimeException if the source or target node does not exist
     */
    @Transactional
    public Relationship createRelationship(Relationship relationship) {
        validateRelationship(relationship);
        
        // Ensure source and target nodes exist
        Node source = nodeRepository.findById(relationship.getSource().getId())
                .orElseThrow(() -> new RuntimeException("Source node not found"));
        
        Node target = nodeRepository.findById(relationship.getTarget().getId())
                .orElseThrow(() -> new RuntimeException("Target node not found"));
        
        relationship.setSource(source);
        relationship.setTarget(target);
        
        return relationshipRepository.save(relationship);
    }

    /**
     * Update an existing relationship.
     * @param id ID of the relationship to update
     * @param relationshipDetails Updated relationship details
     * @return The updated relationship
     * @throws RuntimeException if the relationship, source node, or target node does not exist
     */
    @Transactional
    public Relationship updateRelationship(Long id, Relationship relationshipDetails) {
        Relationship relationship = relationshipRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Relationship not found with id: " + id));
        
        validateRelationship(relationshipDetails);
        
        // Ensure source and target nodes exist if they are being updated
        if (relationshipDetails.getSource() != null && relationshipDetails.getSource().getId() != null) {
            Node source = nodeRepository.findById(relationshipDetails.getSource().getId())
                    .orElseThrow(() -> new RuntimeException("Source node not found"));
            relationship.setSource(source);
        }
        
        if (relationshipDetails.getTarget() != null && relationshipDetails.getTarget().getId() != null) {
            Node target = nodeRepository.findById(relationshipDetails.getTarget().getId())
                    .orElseThrow(() -> new RuntimeException("Target node not found"));
            relationship.setTarget(target);
        }
        
        relationship.setType(relationshipDetails.getType());
        relationship.setProperties(relationshipDetails.getProperties());
        
        return relationshipRepository.save(relationship);
    }

    /**
     * Delete a relationship by ID.
     * @param id ID of the relationship to delete
     * @throws RuntimeException if the relationship does not exist
     */
    @Transactional
    public void deleteRelationship(Long id) {
        Relationship relationship = relationshipRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Relationship not found with id: " + id));
        
        relationshipRepository.delete(relationship);
    }

    /**
     * Search relationships by a query string.
     * @param query Query string to search for
     * @return List of relationships matching the search query
     */
    public List<Relationship> searchRelationships(String query) {
        return relationshipRepository.searchRelationships(query);
    }

    /**
     * Get data for graph visualization.
     * @return Map containing nodes and relationships for visualization
     */
    public Map<String, Object> getGraphVisualizationData() {
        List<Node> nodes = nodeRepository.findAll();
        List<Relationship> relationships = relationshipRepository.findAll();
        
        Map<String, Object> result = new HashMap<>();
        result.put("nodes", nodes);
        result.put("relationships", relationships);
        
        return result;
    }

    /**
     * Validate a node.
     * @param node Node to validate
     * @throws IllegalArgumentException if validation fails
     */
    private void validateNode(Node node) {
        if (node.getLabel() == null || node.getLabel().trim().isEmpty()) {
            throw new IllegalArgumentException("Node label cannot be empty");
        }
        
        if (node.getType() == null || node.getType().trim().isEmpty()) {
            throw new IllegalArgumentException("Node type cannot be empty");
        }
    }

    /**
     * Validate a relationship.
     * @param relationship Relationship to validate
     * @throws IllegalArgumentException if validation fails
     */
    private void validateRelationship(Relationship relationship) {
        if (relationship.getType() == null || relationship.getType().trim().isEmpty()) {
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