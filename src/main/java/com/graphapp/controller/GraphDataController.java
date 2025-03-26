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
    public ResponseEntity<Node> getNodeById(@PathVariable Long id) {
        return graphDataService.getNodeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/nodes")
    public ResponseEntity<Node> createNode(@RequestBody Node node) {
        return ResponseEntity.status(HttpStatus.CREATED).body(graphDataService.createNode(node));
    }

    @PutMapping("/nodes/{id}")
    public ResponseEntity<Node> updateNode(@PathVariable Long id, @RequestBody Node node) {
        try {
            return ResponseEntity.ok(graphDataService.updateNode(id, node));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/nodes/{id}")
    public ResponseEntity<Void> deleteNode(@PathVariable Long id) {
        graphDataService.deleteNode(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/nodes/search")
    public ResponseEntity<List<Node>> searchNodes(@RequestParam String query) {
        return ResponseEntity.ok(graphDataService.searchNodes(query));
    }

    // Relationship endpoints
    @GetMapping("/relationships")
    public ResponseEntity<List<Relationship>> getAllRelationships() {
        return ResponseEntity.ok(graphDataService.getAllRelationships());
    }

    @GetMapping("/relationships/{id}")
    public ResponseEntity<Relationship> getRelationshipById(@PathVariable Long id) {
        return graphDataService.getRelationshipById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/relationships")
    public ResponseEntity<Relationship> createRelationship(@RequestBody Relationship relationship) {
        return ResponseEntity.status(HttpStatus.CREATED).body(graphDataService.createRelationship(relationship));
    }

    @PutMapping("/relationships/{id}")
    public ResponseEntity<Relationship> updateRelationship(@PathVariable Long id, @RequestBody Relationship relationship) {
        try {
            return ResponseEntity.ok(graphDataService.updateRelationship(id, relationship));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/relationships/{id}")
    public ResponseEntity<Void> deleteRelationship(@PathVariable Long id) {
        graphDataService.deleteRelationship(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/relationships/search")
    public ResponseEntity<List<Relationship>> searchRelationships(@RequestParam String query) {
        return ResponseEntity.ok(graphDataService.searchRelationships(query));
    }

    // Combined graph endpoints
    @GetMapping("/visualization")
    public ResponseEntity<Map<String, Object>> getGraphVisualizationData() {
        return ResponseEntity.ok(graphDataService.getGraphVisualizationData());
    }

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchGraph(@RequestParam String query) {
        return ResponseEntity.ok(graphDataService.searchGraph(query));
    }
}