package com.graphapp.service;

import com.graphapp.model.graph.Node;
import com.graphapp.model.graph.Relationship;
import com.graphapp.repository.graph.NodeRepository;
import com.graphapp.repository.graph.RelationshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class GraphDataService {

    private final NodeRepository nodeRepository;
    private final RelationshipRepository relationshipRepository;

    @Autowired
    public GraphDataService(NodeRepository nodeRepository, RelationshipRepository relationshipRepository) {
        this.nodeRepository = nodeRepository;
        this.relationshipRepository = relationshipRepository;
    }

    // Node methods
    public List<Node> getAllNodes() {
        return nodeRepository.findAll();
    }

    public Optional<Node> getNodeById(Long id) {
        return nodeRepository.findById(id);
    }

    @Transactional
    public Node saveNode(Node node) {
        return nodeRepository.save(node);
    }

    @Transactional
    public void deleteNode(Long id) {
        nodeRepository.deleteById(id);
    }

    // Relationship methods
    public List<Relationship> getAllRelationships() {
        return relationshipRepository.findAll();
    }

    public Optional<Relationship> getRelationshipById(Long id) {
        return relationshipRepository.findById(id);
    }

    @Transactional
    public Relationship saveRelationship(Relationship relationship) {
        // Ensure the source and target nodes exist
        if (relationship.getSourceNode() != null && relationship.getSourceNode().getId() != null) {
            nodeRepository.findById(relationship.getSourceNode().getId())
                    .ifPresent(relationship::setSourceNode);
        }

        if (relationship.getTargetNode() != null && relationship.getTargetNode().getId() != null) {
            nodeRepository.findById(relationship.getTargetNode().getId())
                    .ifPresent(relationship::setTargetNode);
        }

        return relationshipRepository.save(relationship);
    }

    @Transactional
    public void deleteRelationship(Long id) {
        relationshipRepository.deleteById(id);
    }

    // Visualization data
    public Map<String, Object> getGraphVisualizationData() {
        Map<String, Object> visualizationData = new HashMap<>();
        
        // Get all nodes and prepare them for visualization
        List<Node> nodes = nodeRepository.findLimited();
        List<Map<String, Object>> nodeDataList = new ArrayList<>();
        
        for (Node node : nodes) {
            Map<String, Object> nodeData = new HashMap<>();
            nodeData.put("id", node.getId());
            nodeData.put("label", node.getLabel());
            nodeData.put("type", node.getType());
            nodeData.put("properties", node.getProperties());
            nodeDataList.add(nodeData);
        }
        
        // Get all relationships and prepare them for visualization
        List<Relationship> relationships = relationshipRepository.findLimited();
        List<Map<String, Object>> relationshipDataList = new ArrayList<>();
        
        for (Relationship relationship : relationships) {
            Map<String, Object> relationshipData = new HashMap<>();
            relationshipData.put("id", relationship.getId());
            relationshipData.put("type", relationship.getType());
            relationshipData.put("source", relationship.getSourceNode() != null ? relationship.getSourceNode().getId() : null);
            relationshipData.put("target", relationship.getTargetNode() != null ? relationship.getTargetNode().getId() : null);
            relationshipData.put("properties", relationship.getProperties());
            relationshipDataList.add(relationshipData);
        }
        
        visualizationData.put("nodes", nodeDataList);
        visualizationData.put("relationships", relationshipDataList);
        
        return visualizationData;
    }

    // Search functionality
    public Map<String, Object> searchGraph(String query) {
        Map<String, Object> searchResults = new HashMap<>();
        
        List<Node> nodes = nodeRepository.searchNodes(query);
        List<Relationship> relationships = relationshipRepository.searchRelationships(query);
        
        searchResults.put("nodes", nodes);
        searchResults.put("relationships", relationships);
        
        return searchResults;
    }
}
