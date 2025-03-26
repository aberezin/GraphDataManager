package com.graphapp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.graphapp.model.graph.Node;
import com.graphapp.model.graph.Relationship;
import com.graphapp.repository.graph.NodeRepository;
import com.graphapp.repository.graph.RelationshipRepository;

/**
 * Service for managing graph data (nodes and relationships).
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
    
    // Node operations
    
    /**
     * Get all nodes.
     * 
     * @return A list of all nodes.
     */
    public List<Node> getAllNodes() {
        return nodeRepository.findAll();
    }
    
    /**
     * Get a node by ID.
     * 
     * @param id The ID of the node.
     * @return The node, or null if not found.
     */
    public Node getNodeById(String id) {
        return nodeRepository.findById(id).orElse(null);
    }
    
    /**
     * Create a new node.
     * 
     * @param node The node to create.
     * @return The created node.
     */
    @Transactional
    public Node createNode(Node node) {
        if (node.getLabel() == null || node.getLabel().trim().isEmpty()) {
            throw new IllegalArgumentException("Node label cannot be empty");
        }
        return nodeRepository.save(node);
    }
    
    /**
     * Update an existing node.
     * 
     * @param id The ID of the node to update.
     * @param nodeDetails The updated node details.
     * @return The updated node, or null if not found.
     */
    @Transactional
    public Node updateNode(String id, Node nodeDetails) {
        Optional<Node> optionalNode = nodeRepository.findById(id);
        if (optionalNode.isPresent()) {
            Node node = optionalNode.get();
            
            if (nodeDetails.getLabel() != null) {
                node.setLabel(nodeDetails.getLabel());
            }
            
            if (nodeDetails.getType() != null) {
                node.setType(nodeDetails.getType());
            }
            
            if (nodeDetails.getProperties() != null) {
                node.setProperties(nodeDetails.getProperties());
            }
            
            return nodeRepository.save(node);
        }
        return null;
    }
    
    /**
     * Delete a node by ID.
     * 
     * @param id The ID of the node to delete.
     * @return True if the node was deleted, false otherwise.
     */
    @Transactional
    public boolean deleteNode(String id) {
        if (nodeRepository.existsById(id)) {
            nodeRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    /**
     * Search for nodes by label or type.
     * 
     * @param searchText The text to search for.
     * @return A list of nodes matching the search criteria.
     */
    public List<Node> searchNodes(String searchText) {
        return nodeRepository.findByLabelContainingIgnoreCaseOrTypeContainingIgnoreCase(searchText, searchText);
    }
    
    /**
     * Find nodes by type.
     * 
     * @param type The type of nodes to find.
     * @return A list of nodes with the specified type.
     */
    public List<Node> findNodesByType(String type) {
        return nodeRepository.findByType(type);
    }
    
    // Relationship operations
    
    /**
     * Get all relationships.
     * 
     * @return A list of all relationships.
     */
    public List<Relationship> getAllRelationships() {
        return relationshipRepository.findAll();
    }
    
    /**
     * Get a relationship by ID.
     * 
     * @param id The ID of the relationship.
     * @return The relationship, or null if not found.
     */
    public Relationship getRelationshipById(String id) {
        return relationshipRepository.findById(id).orElse(null);
    }
    
    /**
     * Create a new relationship.
     * 
     * @param relationship The relationship to create.
     * @return The created relationship.
     */
    @Transactional
    public Relationship createRelationship(Relationship relationship) {
        if (relationship.getType() == null || relationship.getType().trim().isEmpty()) {
            throw new IllegalArgumentException("Relationship type cannot be empty");
        }
        
        if (relationship.getSource() == null || relationship.getSource().getId() == null) {
            throw new IllegalArgumentException("Source node cannot be null");
        }
        
        if (relationship.getTarget() == null || relationship.getTarget().getId() == null) {
            throw new IllegalArgumentException("Target node cannot be null");
        }
        
        // Ensure source and target nodes exist
        Node source = nodeRepository.findById(relationship.getSource().getId())
                .orElseThrow(() -> new IllegalArgumentException("Source node not found"));
        
        Node target = nodeRepository.findById(relationship.getTarget().getId())
                .orElseThrow(() -> new IllegalArgumentException("Target node not found"));
        
        relationship.setSource(source);
        relationship.setTarget(target);
        
        return relationshipRepository.save(relationship);
    }
    
    /**
     * Update an existing relationship.
     * 
     * @param id The ID of the relationship to update.
     * @param relationshipDetails The updated relationship details.
     * @return The updated relationship, or null if not found.
     */
    @Transactional
    public Relationship updateRelationship(String id, Relationship relationshipDetails) {
        Optional<Relationship> optionalRelationship = relationshipRepository.findById(id);
        if (optionalRelationship.isPresent()) {
            Relationship relationship = optionalRelationship.get();
            
            if (relationshipDetails.getType() != null) {
                relationship.setType(relationshipDetails.getType());
            }
            
            if (relationshipDetails.getProperties() != null) {
                relationship.setProperties(relationshipDetails.getProperties());
            }
            
            return relationshipRepository.save(relationship);
        }
        return null;
    }
    
    /**
     * Delete a relationship by ID.
     * 
     * @param id The ID of the relationship to delete.
     * @return True if the relationship was deleted, false otherwise.
     */
    @Transactional
    public boolean deleteRelationship(String id) {
        if (relationshipRepository.existsById(id)) {
            relationshipRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    /**
     * Search for relationships by type.
     * 
     * @param searchText The text to search for.
     * @return A list of relationships matching the search criteria.
     */
    public List<Relationship> searchRelationships(String searchText) {
        return relationshipRepository.findByTypeContainingIgnoreCase(searchText);
    }
    
    /**
     * Find relationships by type.
     * 
     * @param type The type of relationships to find.
     * @return A list of relationships with the specified type.
     */
    public List<Relationship> findRelationshipsByType(String type) {
        return relationshipRepository.findByType(type);
    }
    
    /**
     * Get data for graph visualization.
     * 
     * @return A map containing nodes and relationships for visualization.
     */
    public Map<String, Object> getGraphVisualizationData() {
        List<Node> nodes = nodeRepository.findAll();
        List<Relationship> relationships = relationshipRepository.findAll();
        
        Map<String, Object> visualizationData = new HashMap<>();
        visualizationData.put("nodes", nodes);
        visualizationData.put("relationships", relationships);
        
        return visualizationData;
    }
    
    /**
     * Search for nodes and relationships.
     * 
     * @param query The search query.
     * @return A map containing nodes and relationships matching the search criteria.
     */
    public Map<String, Object> searchGraph(String query) {
        List<Node> nodes = searchNodes(query);
        List<Relationship> relationships = searchRelationships(query);
        
        Map<String, Object> searchResults = new HashMap<>();
        searchResults.put("nodes", nodes);
        searchResults.put("relationships", relationships);
        
        return searchResults;
    }
}