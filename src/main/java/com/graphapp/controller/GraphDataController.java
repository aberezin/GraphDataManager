package com.graphapp.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.graphapp.model.graph.Node;
import com.graphapp.model.graph.Relationship;
import com.graphapp.service.GraphDataService;

/**
 * REST controller for graph data operations.
 */
@RestController
@RequestMapping("/api/graph")
public class GraphDataController {
    
    private final GraphDataService graphDataService;
    
    @Autowired
    public GraphDataController(GraphDataService graphDataService) {
        this.graphDataService = graphDataService;
    }
    
    // Node endpoints
    
    @GetMapping("/nodes")
    public ResponseEntity<List<Node>> getAllNodes() {
        List<Node> nodes = graphDataService.getAllNodes();
        return ResponseEntity.ok(nodes);
    }
    
    @GetMapping("/nodes/{id}")
    public ResponseEntity<Node> getNodeById(@PathVariable String id) {
        Node node = graphDataService.getNodeById(id);
        if (node != null) {
            return ResponseEntity.ok(node);
        }
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping("/nodes")
    public ResponseEntity<Node> createNode(@RequestBody Node node) {
        try {
            Node createdNode = graphDataService.createNode(node);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdNode);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/nodes/{id}")
    public ResponseEntity<Node> updateNode(@PathVariable String id, @RequestBody Node nodeDetails) {
        try {
            Node updatedNode = graphDataService.updateNode(id, nodeDetails);
            if (updatedNode != null) {
                return ResponseEntity.ok(updatedNode);
            }
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @DeleteMapping("/nodes/{id}")
    public ResponseEntity<Void> deleteNode(@PathVariable String id) {
        boolean deleted = graphDataService.deleteNode(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/nodes/search")
    public ResponseEntity<List<Node>> searchNodes(@RequestParam String query) {
        List<Node> nodes = graphDataService.searchNodes(query);
        return ResponseEntity.ok(nodes);
    }
    
    @GetMapping("/nodes/type/{type}")
    public ResponseEntity<List<Node>> getNodesByType(@PathVariable String type) {
        List<Node> nodes = graphDataService.findNodesByType(type);
        return ResponseEntity.ok(nodes);
    }
    
    // Relationship endpoints
    
    @GetMapping("/relationships")
    public ResponseEntity<List<Relationship>> getAllRelationships() {
        List<Relationship> relationships = graphDataService.getAllRelationships();
        return ResponseEntity.ok(relationships);
    }
    
    @GetMapping("/relationships/{id}")
    public ResponseEntity<Relationship> getRelationshipById(@PathVariable String id) {
        Relationship relationship = graphDataService.getRelationshipById(id);
        if (relationship != null) {
            return ResponseEntity.ok(relationship);
        }
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping("/relationships")
    public ResponseEntity<Relationship> createRelationship(@RequestBody Relationship relationship) {
        try {
            Relationship createdRelationship = graphDataService.createRelationship(relationship);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdRelationship);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/relationships/{id}")
    public ResponseEntity<Relationship> updateRelationship(@PathVariable String id, @RequestBody Relationship relationshipDetails) {
        try {
            Relationship updatedRelationship = graphDataService.updateRelationship(id, relationshipDetails);
            if (updatedRelationship != null) {
                return ResponseEntity.ok(updatedRelationship);
            }
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @DeleteMapping("/relationships/{id}")
    public ResponseEntity<Void> deleteRelationship(@PathVariable String id) {
        boolean deleted = graphDataService.deleteRelationship(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/relationships/search")
    public ResponseEntity<List<Relationship>> searchRelationships(@RequestParam String query) {
        List<Relationship> relationships = graphDataService.searchRelationships(query);
        return ResponseEntity.ok(relationships);
    }
    
    @GetMapping("/relationships/type/{type}")
    public ResponseEntity<List<Relationship>> getRelationshipsByType(@PathVariable String type) {
        List<Relationship> relationships = graphDataService.findRelationshipsByType(type);
        return ResponseEntity.ok(relationships);
    }
    
    // Visualization endpoints
    
    @GetMapping("/visualization")
    public ResponseEntity<Map<String, Object>> getGraphVisualizationData() {
        Map<String, Object> visualizationData = graphDataService.getGraphVisualizationData();
        return ResponseEntity.ok(visualizationData);
    }
    
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchGraph(@RequestParam String query) {
        Map<String, Object> searchResults = graphDataService.searchGraph(query);
        return ResponseEntity.ok(searchResults);
    }
}