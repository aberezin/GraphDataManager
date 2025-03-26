package com.graphapp.controller;

import com.graphapp.model.graph.Node;
import com.graphapp.model.graph.Relationship;
import com.graphapp.service.GraphDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
        return ResponseEntity.ok(graphDataService.getAllNodes());
    }
    
    @GetMapping("/nodes/{id}")
    public ResponseEntity<?> getNodeById(@PathVariable String id) {
        return graphDataService.getNodeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/nodes/type/{type}")
    public ResponseEntity<List<Node>> getNodesByType(@PathVariable String type) {
        return ResponseEntity.ok(graphDataService.getNodesByType(type));
    }
    
    @GetMapping("/nodes/label/{label}")
    public ResponseEntity<List<Node>> getNodesByLabel(@PathVariable String label) {
        return ResponseEntity.ok(graphDataService.getNodesByLabel(label));
    }
    
    @PostMapping("/nodes")
    public ResponseEntity<?> createNode(@RequestBody Node node) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(graphDataService.createNode(node));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PutMapping("/nodes/{id}")
    public ResponseEntity<?> updateNode(@PathVariable String id, @RequestBody Node nodeDetails) {
        try {
            return ResponseEntity.ok(graphDataService.updateNode(id, nodeDetails));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @DeleteMapping("/nodes/{id}")
    public ResponseEntity<?> deleteNode(@PathVariable String id) {
        try {
            graphDataService.deleteNode(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    // Relationship endpoints
    @GetMapping("/relationships")
    public ResponseEntity<List<Relationship>> getAllRelationships() {
        return ResponseEntity.ok(graphDataService.getAllRelationships());
    }
    
    @GetMapping("/relationships/{id}")
    public ResponseEntity<?> getRelationshipById(@PathVariable String id) {
        return graphDataService.getRelationshipById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/relationships/type/{type}")
    public ResponseEntity<List<Relationship>> getRelationshipsByType(@PathVariable String type) {
        return ResponseEntity.ok(graphDataService.getRelationshipsByType(type));
    }
    
    @PostMapping("/relationships")
    public ResponseEntity<?> createRelationship(@RequestBody Relationship relationship) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(graphDataService.createRelationship(relationship));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PutMapping("/relationships/{id}")
    public ResponseEntity<?> updateRelationship(@PathVariable String id, @RequestBody Relationship relationshipDetails) {
        try {
            return ResponseEntity.ok(graphDataService.updateRelationship(id, relationshipDetails));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @DeleteMapping("/relationships/{id}")
    public ResponseEntity<?> deleteRelationship(@PathVariable String id) {
        try {
            graphDataService.deleteRelationship(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    // Search and visualization endpoints
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchGraph(@RequestParam String query) {
        List<Node> nodes = graphDataService.searchNodes(query);
        List<Relationship> relationships = graphDataService.searchRelationships(query);
        
        return ResponseEntity.ok(Map.of(
                "nodes", nodes,
                "relationships", relationships
        ));
    }
    
    @GetMapping("/visualization")
    public ResponseEntity<Map<String, Object>> getGraphVisualizationData() {
        return ResponseEntity.ok(graphDataService.getGraphVisualizationData());
    }
}