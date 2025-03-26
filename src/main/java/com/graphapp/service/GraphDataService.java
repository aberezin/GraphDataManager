package com.graphapp.service;

import com.graphapp.model.graph.GraphNode;
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
 * Service for managing graph data (nodes and relationships).
 */
@Service
public class GraphDataService {
    
    private final NodeRepository nodeRepository;
    private final RelationshipRepository relationshipRepository;
    
    /**
     * Constructor for GraphDataService.
     * 
     * @param nodeRepository The node repository.
     * @param relationshipRepository The relationship repository.
     */
    @Autowired
    public GraphDataService(NodeRepository nodeRepository, RelationshipRepository relationshipRepository) {
        this.nodeRepository = nodeRepository;
        this.relationshipRepository = relationshipRepository;
    }
    
    /**
     * Get all nodes.
     * 
     * @return The list of nodes.
     */
    public List<GraphNode> getAllNodes() {
        return nodeRepository.findAll();
    }
    
    /**
     * Get a node by ID.
     * 
     * @param id The ID of the node.
     * @return An Optional containing the node if found.
     */
    public Optional<GraphNode> getNodeById(Long id) {
        return nodeRepository.findById(id);
    }
    
    /**
     * Create a new node.
     * 
     * @param node The node to create.
     * @return The created node.
     */
    @Transactional
    public GraphNode createNode(GraphNode node) {
        return nodeRepository.save(node);
    }
    
    /**
     * Update a node.
     * 
     * @param id The ID of the node to update.
     * @param nodeDetails The updated node details.
     * @return The updated node.
     * @throws RuntimeException if the node is not found.
     */
    @Transactional
    public GraphNode updateNode(Long id, GraphNode nodeDetails) {
        return nodeRepository.findById(id)
                .map(existingNode -> {
                    if (nodeDetails.getLabel() != null) {
                        existingNode.setLabel(nodeDetails.getLabel());
                    }
                    if (nodeDetails.getType() != null) {
                        existingNode.setType(nodeDetails.getType());
                    }
                    if (nodeDetails.getLabels() != null) {
                        existingNode.setLabels(nodeDetails.getLabels());
                    }
                    if (nodeDetails.getProperties() != null) {
                        existingNode.setProperties(nodeDetails.getProperties());
                    }
                    return nodeRepository.save(existingNode);
                })
                .orElseThrow(() -> new RuntimeException("Node not found with id: " + id));
    }
    
    /**
     * Delete a node.
     * 
     * @param id The ID of the node to delete.
     * @throws RuntimeException if the node is not found.
     */
    @Transactional
    public void deleteNode(Long id) {
        GraphNode node = nodeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Node not found with id: " + id));
        nodeRepository.delete(node);
    }
    
    /**
     * Find nodes by type.
     * 
     * @param type The type of the nodes.
     * @return The list of nodes.
     */
    public List<GraphNode> findNodesByType(String type) {
        return nodeRepository.findByType(type);
    }
    
    /**
     * Find nodes by label.
     * 
     * @param label The label of the nodes.
     * @return The list of nodes.
     */
    public List<GraphNode> findNodesByLabel(String label) {
        return nodeRepository.findByLabel(label);
    }
    
    /**
     * Search nodes by various criteria.
     * 
     * @param query The search query.
     * @return The list of nodes.
     */
    public List<GraphNode> searchNodes(String query) {
        return nodeRepository.searchNodes(query);
    }
    
    /**
     * Get all relationships.
     * 
     * @return The list of relationships.
     */
    public List<Relationship> getAllRelationships() {
        return relationshipRepository.findAll();
    }
    
    /**
     * Get a relationship by ID.
     * 
     * @param id The ID of the relationship.
     * @return An Optional containing the relationship if found.
     */
    public Optional<Relationship> getRelationshipById(Long id) {
        return relationshipRepository.findById(id);
    }
    
    /**
     * Create a new relationship.
     * 
     * @param relationship The relationship to create.
     * @return The created relationship.
     * @throws RuntimeException if the source or target node is not found.
     */
    @Transactional
    public Relationship createRelationship(Relationship relationship) {
        if (relationship.getSource() == null || relationship.getSource().getId() == null) {
            throw new RuntimeException("Source node is required");
        }
        if (relationship.getTarget() == null || relationship.getTarget().getId() == null) {
            throw new RuntimeException("Target node is required");
        }
        
        GraphNode source = nodeRepository.findById(relationship.getSource().getId())
                .orElseThrow(() -> new RuntimeException("Source node not found with id: " + relationship.getSource().getId()));
        
        GraphNode target = nodeRepository.findById(relationship.getTarget().getId())
                .orElseThrow(() -> new RuntimeException("Target node not found with id: " + relationship.getTarget().getId()));
        
        relationship.setSource(source);
        relationship.setTarget(target);
        
        return relationshipRepository.save(relationship);
    }
    
    /**
     * Update a relationship.
     * 
     * @param id The ID of the relationship to update.
     * @param relationshipDetails The updated relationship details.
     * @return The updated relationship.
     * @throws RuntimeException if the relationship, source node, or target node is not found.
     */
    @Transactional
    public Relationship updateRelationship(Long id, Relationship relationshipDetails) {
        return relationshipRepository.findById(id)
                .map(existingRelationship -> {
                    if (relationshipDetails.getType() != null) {
                        existingRelationship.setType(relationshipDetails.getType());
                    }
                    
                    if (relationshipDetails.getSource() != null && relationshipDetails.getSource().getId() != null) {
                        GraphNode source = nodeRepository.findById(relationshipDetails.getSource().getId())
                                .orElseThrow(() -> new RuntimeException("Source node not found with id: " + relationshipDetails.getSource().getId()));
                        existingRelationship.setSource(source);
                    }
                    
                    if (relationshipDetails.getTarget() != null && relationshipDetails.getTarget().getId() != null) {
                        GraphNode target = nodeRepository.findById(relationshipDetails.getTarget().getId())
                                .orElseThrow(() -> new RuntimeException("Target node not found with id: " + relationshipDetails.getTarget().getId()));
                        existingRelationship.setTarget(target);
                    }
                    
                    if (relationshipDetails.getProperties() != null) {
                        existingRelationship.setProperties(relationshipDetails.getProperties());
                    }
                    
                    return relationshipRepository.save(existingRelationship);
                })
                .orElseThrow(() -> new RuntimeException("Relationship not found with id: " + id));
    }
    
    /**
     * Delete a relationship.
     * 
     * @param id The ID of the relationship to delete.
     * @throws RuntimeException if the relationship is not found.
     */
    @Transactional
    public void deleteRelationship(Long id) {
        Relationship relationship = relationshipRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Relationship not found with id: " + id));
        relationshipRepository.delete(relationship);
    }
    
    /**
     * Find relationships by type.
     * 
     * @param type The type of the relationships.
     * @return The list of relationships.
     */
    public List<Relationship> findRelationshipsByType(String type) {
        return relationshipRepository.findByType(type);
    }
    
    /**
     * Find relationships by node ID.
     * 
     * @param nodeId The ID of the source or target node.
     * @return The list of relationships.
     */
    public List<Relationship> findRelationshipsByNodeId(Long nodeId) {
        return relationshipRepository.findByNodeId(nodeId);
    }
    
    /**
     * Search relationships by type or properties.
     * 
     * @param query The search query.
     * @return The list of relationships.
     */
    public List<Relationship> searchRelationships(String query) {
        return relationshipRepository.searchRelationships(query);
    }
    
    /**
     * Get visualization data for graph rendering.
     * 
     * @return A map containing nodes and relationships.
     */
    public Map<String, Object> getVisualizationData() {
        List<GraphNode> nodes = nodeRepository.findAll();
        List<Relationship> relationships = relationshipRepository.findAllWithNodes();
        
        Map<String, Object> result = new HashMap<>();
        result.put("nodes", nodes);
        result.put("relationships", relationships);
        return result;
    }
    
    /**
     * Search for nodes and relationships based on a query.
     * 
     * @param query The search query.
     * @return A map containing matching nodes and relationships.
     */
    public Map<String, Object> searchGraph(String query) {
        List<GraphNode> nodes = nodeRepository.searchNodes(query);
        List<Relationship> relationships = relationshipRepository.searchRelationships(query);
        
        Map<String, Object> result = new HashMap<>();
        result.put("nodes", nodes);
        result.put("relationships", relationships);
        return result;
    }
}