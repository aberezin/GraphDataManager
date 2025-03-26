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
 * Service class for handling graph data operations
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
     * Get all nodes
     */
    public List<Node> getAllNodes() {
        return nodeRepository.findAll();
    }

    /**
     * Get node by id
     */
    public Optional<Node> getNodeById(Long id) {
        return nodeRepository.findById(id);
    }

    /**
     * Create a new node
     */
    @Transactional
    public Node createNode(Node node) {
        if (node.getId() != null) {
            throw new IllegalArgumentException("New node cannot have an ID");
        }
        return nodeRepository.save(node);
    }

    /**
     * Update an existing node
     */
    @Transactional
    public Node updateNode(Long id, Node nodeDetails) {
        return nodeRepository.findById(id)
            .map(existingNode -> {
                if (nodeDetails.getLabel() != null) {
                    existingNode.setLabel(nodeDetails.getLabel());
                }
                if (nodeDetails.getType() != null) {
                    existingNode.setType(nodeDetails.getType());
                }
                if (nodeDetails.getProperties() != null) {
                    // Merge properties instead of replacing
                    Map<String, Object> updatedProperties = new HashMap<>();
                    if (existingNode.getProperties() != null) {
                        updatedProperties.putAll(existingNode.getProperties());
                    }
                    updatedProperties.putAll(nodeDetails.getProperties());
                    existingNode.setProperties(updatedProperties);
                }
                return nodeRepository.save(existingNode);
            })
            .orElseThrow(() -> new RuntimeException("Node not found with id: " + id));
    }

    /**
     * Delete a node
     */
    @Transactional
    public void deleteNode(Long id) {
        // First delete all relationships connected to this node
        List<Relationship> relationships = relationshipRepository.findByNodeId(id);
        for (Relationship relationship : relationships) {
            relationshipRepository.deleteById(relationship.getId());
        }
        
        // Then delete the node
        nodeRepository.deleteById(id);
    }

    /**
     * Get nodes by type
     */
    public List<Node> getNodesByType(String type) {
        return nodeRepository.findByType(type);
    }

    /**
     * Get nodes by label
     */
    public List<Node> getNodesByLabel(String label) {
        return nodeRepository.findByLabel(label);
    }

    /**
     * Search nodes by text
     */
    public List<Node> searchNodes(String text) {
        return nodeRepository.searchNodes(text);
    }

    /**
     * Get all relationships
     */
    public List<Relationship> getAllRelationships() {
        return relationshipRepository.findAll();
    }

    /**
     * Get relationship by id
     */
    public Optional<Relationship> getRelationshipById(Long id) {
        return relationshipRepository.findById(id);
    }

    /**
     * Create a new relationship
     */
    @Transactional
    public Relationship createRelationship(Relationship relationship) {
        if (relationship.getId() != null) {
            throw new IllegalArgumentException("New relationship cannot have an ID");
        }
        
        // Validate source and target nodes exist
        Node source = relationship.getSource();
        Node target = relationship.getTarget();
        
        if (source == null || source.getId() == null) {
            throw new IllegalArgumentException("Relationship must have a valid source node");
        }
        
        if (target == null || target.getId() == null) {
            throw new IllegalArgumentException("Relationship must have a valid target node");
        }
        
        if (!nodeRepository.existsById(source.getId())) {
            throw new RuntimeException("Source node not found with id: " + source.getId());
        }
        
        if (!nodeRepository.existsById(target.getId())) {
            throw new RuntimeException("Target node not found with id: " + target.getId());
        }
        
        return relationshipRepository.save(relationship);
    }

    /**
     * Update an existing relationship
     */
    @Transactional
    public Relationship updateRelationship(Long id, Relationship relationshipDetails) {
        return relationshipRepository.findById(id)
            .map(existingRelationship -> {
                if (relationshipDetails.getType() != null) {
                    existingRelationship.setType(relationshipDetails.getType());
                }
                
                // Check if source node is being updated
                if (relationshipDetails.getSource() != null && relationshipDetails.getSource().getId() != null) {
                    Long sourceId = relationshipDetails.getSource().getId();
                    Node sourceNode = nodeRepository.findById(sourceId)
                        .orElseThrow(() -> new RuntimeException("Source node not found with id: " + sourceId));
                    existingRelationship.setSource(sourceNode);
                }
                
                // Check if target node is being updated
                if (relationshipDetails.getTarget() != null && relationshipDetails.getTarget().getId() != null) {
                    Long targetId = relationshipDetails.getTarget().getId();
                    Node targetNode = nodeRepository.findById(targetId)
                        .orElseThrow(() -> new RuntimeException("Target node not found with id: " + targetId));
                    existingRelationship.setTarget(targetNode);
                }
                
                if (relationshipDetails.getProperties() != null) {
                    // Merge properties instead of replacing
                    Map<String, Object> updatedProperties = new HashMap<>();
                    if (existingRelationship.getProperties() != null) {
                        updatedProperties.putAll(existingRelationship.getProperties());
                    }
                    updatedProperties.putAll(relationshipDetails.getProperties());
                    existingRelationship.setProperties(updatedProperties);
                }
                
                return relationshipRepository.save(existingRelationship);
            })
            .orElseThrow(() -> new RuntimeException("Relationship not found with id: " + id));
    }

    /**
     * Delete a relationship
     */
    @Transactional
    public void deleteRelationship(Long id) {
        relationshipRepository.deleteById(id);
    }

    /**
     * Get relationships by type
     */
    public List<Relationship> getRelationshipsByType(String type) {
        return relationshipRepository.findByType(type);
    }

    /**
     * Get relationships by source node id
     */
    public List<Relationship> getRelationshipsBySourceNodeId(Long nodeId) {
        return relationshipRepository.findBySourceNodeId(nodeId);
    }

    /**
     * Get relationships by target node id
     */
    public List<Relationship> getRelationshipsByTargetNodeId(Long nodeId) {
        return relationshipRepository.findByTargetNodeId(nodeId);
    }

    /**
     * Get relationships connected to a node (either as source or target)
     */
    public List<Relationship> getRelationshipsByNodeId(Long nodeId) {
        return relationshipRepository.findByNodeId(nodeId);
    }

    /**
     * Search relationships by text
     */
    public List<Relationship> searchRelationships(String text) {
        return relationshipRepository.searchRelationships(text);
    }

    /**
     * Get data for graph visualization (nodes and relationships)
     */
    public Map<String, Object> getGraphVisualizationData() {
        List<Node> nodes = nodeRepository.findAll();
        List<Relationship> relationships = relationshipRepository.findAllWithNodes();
        
        Map<String, Object> result = new HashMap<>();
        result.put("nodes", nodes);
        result.put("relationships", relationships);
        
        return result;
    }

    /**
     * Search in the graph (nodes and relationships)
     */
    public Map<String, Object> searchGraph(String query) {
        List<Node> nodes = nodeRepository.searchNodes(query);
        List<Relationship> relationships = relationshipRepository.searchRelationships(query);
        
        // Also include relationships that connect the found nodes
        for (Node node : nodes) {
            List<Relationship> nodeRelationships = relationshipRepository.findByNodeId(node.getId());
            for (Relationship relationship : nodeRelationships) {
                if (!relationships.contains(relationship)) {
                    relationships.add(relationship);
                }
            }
        }
        
        Map<String, Object> result = new HashMap<>();
        result.put("nodes", nodes);
        result.put("relationships", relationships);
        
        return result;
    }
}