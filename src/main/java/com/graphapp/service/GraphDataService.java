package com.graphapp.service;

import com.graphapp.model.graph.Node;
import com.graphapp.model.graph.Relationship;
import com.graphapp.repository.graph.NodeRepository;
import com.graphapp.repository.graph.RelationshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    @Transactional(readOnly = true)
    public List<Node> getAllNodes() {
        return nodeRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public Optional<Node> getNodeById(Long id) {
        return nodeRepository.findById(id);
    }
    
    @Transactional
    public Node createNode(Node node) {
        return nodeRepository.save(node);
    }
    
    @Transactional
    public Node updateNode(Long id, Node nodeDetails) {
        Optional<Node> optionalNode = nodeRepository.findById(id);
        if (optionalNode.isPresent()) {
            Node existingNode = optionalNode.get();
            existingNode.setLabel(nodeDetails.getLabel());
            existingNode.setType(nodeDetails.getType());
            existingNode.setProperties(nodeDetails.getProperties());
            return nodeRepository.save(existingNode);
        } else {
            throw new RuntimeException("Node not found with id: " + id);
        }
    }
    
    @Transactional
    public void deleteNode(Long id) {
        nodeRepository.deleteById(id);
    }
    
    @Transactional(readOnly = true)
    public List<Node> searchNodes(String searchTerm) {
        return nodeRepository.searchNodes(searchTerm);
    }
    
    // Relationship operations
    @Transactional(readOnly = true)
    public List<Relationship> getAllRelationships() {
        return relationshipRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public Optional<Relationship> getRelationshipById(Long id) {
        return relationshipRepository.findById(id);
    }
    
    @Transactional
    public Relationship createRelationship(Relationship relationship) {
        return relationshipRepository.save(relationship);
    }
    
    @Transactional
    public Relationship updateRelationship(Long id, Relationship relationshipDetails) {
        Optional<Relationship> optionalRelationship = relationshipRepository.findById(id);
        if (optionalRelationship.isPresent()) {
            Relationship existingRelationship = optionalRelationship.get();
            existingRelationship.setType(relationshipDetails.getType());
            existingRelationship.setTarget(relationshipDetails.getTarget());
            existingRelationship.setProperties(relationshipDetails.getProperties());
            return relationshipRepository.save(existingRelationship);
        } else {
            throw new RuntimeException("Relationship not found with id: " + id);
        }
    }
    
    @Transactional
    public void deleteRelationship(Long id) {
        relationshipRepository.deleteById(id);
    }
    
    @Transactional(readOnly = true)
    public List<Relationship> searchRelationships(String searchTerm) {
        return relationshipRepository.searchRelationships(searchTerm);
    }
    
    // Combined graph operations
    @Transactional(readOnly = true)
    public Map<String, Object> getGraphVisualizationData() {
        List<Node> nodes = nodeRepository.findAll();
        List<Relationship> relationships = relationshipRepository.findAllRelationshipsWithNodes();
        
        Map<String, Object> result = new HashMap<>();
        result.put("nodes", nodes);
        result.put("relationships", relationships);
        return result;
    }
    
    @Transactional(readOnly = true)
    public Map<String, Object> searchGraph(String searchTerm) {
        List<Node> nodes = nodeRepository.searchNodes(searchTerm);
        List<Relationship> relationships = relationshipRepository.searchRelationships(searchTerm);
        
        Map<String, Object> result = new HashMap<>();
        result.put("nodes", nodes);
        result.put("relationships", relationships);
        return result;
    }
}