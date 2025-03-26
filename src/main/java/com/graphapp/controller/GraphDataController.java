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
import java.util.Optional;

@RestController
@RequestMapping("/api/graph")
@CrossOrigin(origins = "*")
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
        Optional<Node> node = graphDataService.getNodeById(id);
        return node.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/nodes")
    public ResponseEntity<Node> createNode(@RequestBody Node node) {
        Node savedNode = graphDataService.saveNode(node);
        return new ResponseEntity<>(savedNode, HttpStatus.CREATED);
    }

    @PutMapping("/nodes/{id}")
    public ResponseEntity<Node> updateNode(@PathVariable Long id, @RequestBody Node node) {
        if (!graphDataService.getNodeById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        node.setId(id);
        return ResponseEntity.ok(graphDataService.saveNode(node));
    }

    @DeleteMapping("/nodes/{id}")
    public ResponseEntity<Void> deleteNode(@PathVariable Long id) {
        if (!graphDataService.getNodeById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        graphDataService.deleteNode(id);
        return ResponseEntity.noContent().build();
    }

    // Relationship endpoints
    @GetMapping("/relationships")
    public ResponseEntity<List<Relationship>> getAllRelationships() {
        return ResponseEntity.ok(graphDataService.getAllRelationships());
    }

    @GetMapping("/relationships/{id}")
    public ResponseEntity<Relationship> getRelationshipById(@PathVariable Long id) {
        Optional<Relationship> relationship = graphDataService.getRelationshipById(id);
        return relationship.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/relationships")
    public ResponseEntity<Relationship> createRelationship(@RequestBody Relationship relationship) {
        Relationship savedRelationship = graphDataService.saveRelationship(relationship);
        return new ResponseEntity<>(savedRelationship, HttpStatus.CREATED);
    }

    @PutMapping("/relationships/{id}")
    public ResponseEntity<Relationship> updateRelationship(@PathVariable Long id, @RequestBody Relationship relationship) {
        if (!graphDataService.getRelationshipById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        relationship.setId(id);
        return ResponseEntity.ok(graphDataService.saveRelationship(relationship));
    }

    @DeleteMapping("/relationships/{id}")
    public ResponseEntity<Void> deleteRelationship(@PathVariable Long id) {
        if (!graphDataService.getRelationshipById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        graphDataService.deleteRelationship(id);
        return ResponseEntity.noContent().build();
    }

    // Graph visualization endpoint
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
