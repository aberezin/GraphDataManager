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
    public ResponseEntity<Node> getNodeById(@PathVariable String id) {
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
            return graphDataService.updateNode(id, nodeDetails)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @DeleteMapping("/nodes/{id}")
    public ResponseEntity<Void> deleteNode(@PathVariable String id) {
        return graphDataService.deleteNode(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
    
    // Relationship endpoints
    @GetMapping("/relationships")
    public ResponseEntity<List<Relationship>> getAllRelationships() {
        return ResponseEntity.ok(graphDataService.getAllRelationships());
    }
    
    @GetMapping("/relationships/{id}")
    public ResponseEntity<Relationship> getRelationshipById(@PathVariable String id) {
        return graphDataService.getRelationshipById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/relationships/type/{type}")
    public ResponseEntity<List<Relationship>> getRelationshipsByType(@PathVariable String type) {
        return ResponseEntity.ok(graphDataService.getRelationshipsByType(type));
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
            return graphDataService.updateRelationship(id, relationshipDetails)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @DeleteMapping("/relationships/{id}")
    public ResponseEntity<Void> deleteRelationship(@PathVariable String id) {
        return graphDataService.deleteRelationship(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
    
    // Visualization endpoint
    @GetMapping("/visualization")
    public ResponseEntity<Map<String, Object>> getGraphVisualizationData() {
        return ResponseEntity.ok(graphDataService.getGraphVisualizationData());
    }
    
    // Search endpoint
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchGraph(@RequestParam String query) {
        return ResponseEntity.ok(graphDataService.searchGraph(query));
    }
}