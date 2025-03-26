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

/**
 * REST Controller for graph data operations
 */
@RestController
@RequestMapping("/api/graph")
public class GraphDataController {

    private final GraphDataService graphDataService;

    @Autowired
    public GraphDataController(GraphDataService graphDataService) {
        this.graphDataService = graphDataService;
    }

    /**
     * Get all nodes
     */
    @GetMapping("/nodes")
    public ResponseEntity<List<Node>> getAllNodes() {
        List<Node> nodes = graphDataService.getAllNodes();
        return ResponseEntity.ok(nodes);
    }

    /**
     * Get node by id
     */
    @GetMapping("/nodes/{id}")
    public ResponseEntity<Node> getNodeById(@PathVariable Long id) {
        return graphDataService.getNodeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Create a new node
     */
    @PostMapping("/nodes")
    public ResponseEntity<Node> createNode(@RequestBody Node node) {
        try {
            Node createdNode = graphDataService.createNode(node);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdNode);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Update an existing node
     */
    @PutMapping("/nodes/{id}")
    public ResponseEntity<Node> updateNode(@PathVariable Long id, @RequestBody Node node) {
        try {
            Node updatedNode = graphDataService.updateNode(id, node);
            return ResponseEntity.ok(updatedNode);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Delete a node
     */
    @DeleteMapping("/nodes/{id}")
    public ResponseEntity<Void> deleteNode(@PathVariable Long id) {
        try {
            graphDataService.deleteNode(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Get nodes by type
     */
    @GetMapping("/nodes/type/{type}")
    public ResponseEntity<List<Node>> getNodesByType(@PathVariable String type) {
        List<Node> nodes = graphDataService.getNodesByType(type);
        return ResponseEntity.ok(nodes);
    }

    /**
     * Get nodes by label
     */
    @GetMapping("/nodes/label/{label}")
    public ResponseEntity<List<Node>> getNodesByLabel(@PathVariable String label) {
        List<Node> nodes = graphDataService.getNodesByLabel(label);
        return ResponseEntity.ok(nodes);
    }

    /**
     * Search nodes by text
     */
    @GetMapping("/nodes/search")
    public ResponseEntity<List<Node>> searchNodes(@RequestParam String query) {
        List<Node> nodes = graphDataService.searchNodes(query);
        return ResponseEntity.ok(nodes);
    }

    /**
     * Get all relationships
     */
    @GetMapping("/relationships")
    public ResponseEntity<List<Relationship>> getAllRelationships() {
        List<Relationship> relationships = graphDataService.getAllRelationships();
        return ResponseEntity.ok(relationships);
    }

    /**
     * Get relationship by id
     */
    @GetMapping("/relationships/{id}")
    public ResponseEntity<Relationship> getRelationshipById(@PathVariable Long id) {
        return graphDataService.getRelationshipById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Create a new relationship
     */
    @PostMapping("/relationships")
    public ResponseEntity<Relationship> createRelationship(@RequestBody Relationship relationship) {
        try {
            Relationship createdRelationship = graphDataService.createRelationship(relationship);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdRelationship);
        } catch (IllegalArgumentException | RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * Update an existing relationship
     */
    @PutMapping("/relationships/{id}")
    public ResponseEntity<Relationship> updateRelationship(@PathVariable Long id, @RequestBody Relationship relationship) {
        try {
            Relationship updatedRelationship = graphDataService.updateRelationship(id, relationship);
            return ResponseEntity.ok(updatedRelationship);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Delete a relationship
     */
    @DeleteMapping("/relationships/{id}")
    public ResponseEntity<Void> deleteRelationship(@PathVariable Long id) {
        try {
            graphDataService.deleteRelationship(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Get relationships by type
     */
    @GetMapping("/relationships/type/{type}")
    public ResponseEntity<List<Relationship>> getRelationshipsByType(@PathVariable String type) {
        List<Relationship> relationships = graphDataService.getRelationshipsByType(type);
        return ResponseEntity.ok(relationships);
    }

    /**
     * Get relationships by source node id
     */
    @GetMapping("/relationships/source/{nodeId}")
    public ResponseEntity<List<Relationship>> getRelationshipsBySourceNodeId(@PathVariable Long nodeId) {
        List<Relationship> relationships = graphDataService.getRelationshipsBySourceNodeId(nodeId);
        return ResponseEntity.ok(relationships);
    }

    /**
     * Get relationships by target node id
     */
    @GetMapping("/relationships/target/{nodeId}")
    public ResponseEntity<List<Relationship>> getRelationshipsByTargetNodeId(@PathVariable Long nodeId) {
        List<Relationship> relationships = graphDataService.getRelationshipsByTargetNodeId(nodeId);
        return ResponseEntity.ok(relationships);
    }

    /**
     * Get relationships connected to a node (either as source or target)
     */
    @GetMapping("/relationships/node/{nodeId}")
    public ResponseEntity<List<Relationship>> getRelationshipsByNodeId(@PathVariable Long nodeId) {
        List<Relationship> relationships = graphDataService.getRelationshipsByNodeId(nodeId);
        return ResponseEntity.ok(relationships);
    }

    /**
     * Search relationships by text
     */
    @GetMapping("/relationships/search")
    public ResponseEntity<List<Relationship>> searchRelationships(@RequestParam String query) {
        List<Relationship> relationships = graphDataService.searchRelationships(query);
        return ResponseEntity.ok(relationships);
    }

    /**
     * Get data for graph visualization (nodes and relationships)
     */
    @GetMapping("/visualization")
    public ResponseEntity<Map<String, Object>> getGraphVisualizationData() {
        Map<String, Object> data = graphDataService.getGraphVisualizationData();
        return ResponseEntity.ok(data);
    }

    /**
     * Search in the graph (nodes and relationships)
     */
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchGraph(@RequestParam String query) {
        Map<String, Object> results = graphDataService.searchGraph(query);
        return ResponseEntity.ok(results);
    }
}