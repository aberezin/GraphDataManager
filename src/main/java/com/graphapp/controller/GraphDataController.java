package com.graphapp.controller;

import com.graphapp.model.graph.GraphNode;
import com.graphapp.model.graph.Relationship;
import com.graphapp.service.GraphDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * REST controller for graph data operations.
 */
@RestController
@RequestMapping("/api/graph")
public class GraphDataController {

    private final GraphDataService graphDataService;

    /**
     * Constructor for GraphDataController.
     * 
     * @param graphDataService The graph data service.
     */
    @Autowired
    public GraphDataController(GraphDataService graphDataService) {
        this.graphDataService = graphDataService;
    }

    /**
     * Get all nodes.
     * 
     * @return The list of nodes.
     */
    @GetMapping("/nodes")
    public ResponseEntity<List<GraphNode>> getAllNodes() {
        return ResponseEntity.ok(graphDataService.getAllNodes());
    }

    /**
     * Get a node by ID.
     * 
     * @param id The ID of the node.
     * @return The node if found, or a 404 response.
     */
    @GetMapping("/nodes/{id}")
    public ResponseEntity<GraphNode> getNodeById(@PathVariable Long id) {
        Optional<GraphNode> node = graphDataService.getNodeById(id);
        return node.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Create a new node.
     * 
     * @param node The node to create.
     * @return The created node.
     */
    @PostMapping("/nodes")
    public ResponseEntity<GraphNode> createNode(@RequestBody GraphNode node) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(graphDataService.createNode(node));
    }

    /**
     * Update a node.
     * 
     * @param id The ID of the node to update.
     * @param node The updated node details.
     * @return The updated node, or a 404 response.
     */
    @PutMapping("/nodes/{id}")
    public ResponseEntity<GraphNode> updateNode(@PathVariable Long id, @RequestBody GraphNode node) {
        try {
            GraphNode updatedNode = graphDataService.updateNode(id, node);
            return ResponseEntity.ok(updatedNode);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Delete a node.
     * 
     * @param id The ID of the node to delete.
     * @return A 204 response if successful, or a 404 response.
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
     * Find nodes by type.
     * 
     * @param type The type of the nodes.
     * @return The list of nodes.
     */
    @GetMapping("/nodes/type/{type}")
    public ResponseEntity<List<GraphNode>> findNodesByType(@PathVariable String type) {
        return ResponseEntity.ok(graphDataService.findNodesByType(type));
    }

    /**
     * Find nodes by label.
     * 
     * @param label The label of the nodes.
     * @return The list of nodes.
     */
    @GetMapping("/nodes/label/{label}")
    public ResponseEntity<List<GraphNode>> findNodesByLabel(@PathVariable String label) {
        return ResponseEntity.ok(graphDataService.findNodesByLabel(label));
    }

    /**
     * Search nodes by various criteria.
     * 
     * @param query The search query.
     * @return The list of nodes.
     */
    @GetMapping("/nodes/search")
    public ResponseEntity<List<GraphNode>> searchNodes(@RequestParam String query) {
        return ResponseEntity.ok(graphDataService.searchNodes(query));
    }

    /**
     * Get all relationships.
     * 
     * @return The list of relationships.
     */
    @GetMapping("/relationships")
    public ResponseEntity<List<Relationship>> getAllRelationships() {
        return ResponseEntity.ok(graphDataService.getAllRelationships());
    }

    /**
     * Get a relationship by ID.
     * 
     * @param id The ID of the relationship.
     * @return The relationship if found, or a 404 response.
     */
    @GetMapping("/relationships/{id}")
    public ResponseEntity<Relationship> getRelationshipById(@PathVariable Long id) {
        Optional<Relationship> relationship = graphDataService.getRelationshipById(id);
        return relationship.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Create a new relationship.
     * 
     * @param relationship The relationship to create.
     * @return The created relationship.
     */
    @PostMapping("/relationships")
    public ResponseEntity<Relationship> createRelationship(@RequestBody Relationship relationship) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(graphDataService.createRelationship(relationship));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Update a relationship.
     * 
     * @param id The ID of the relationship to update.
     * @param relationship The updated relationship details.
     * @return The updated relationship, or a 404 response.
     */
    @PutMapping("/relationships/{id}")
    public ResponseEntity<Relationship> updateRelationship(@PathVariable Long id, @RequestBody Relationship relationship) {
        try {
            Relationship updatedRelationship = graphDataService.updateRelationship(id, relationship);
            return ResponseEntity.ok(updatedRelationship);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Delete a relationship.
     * 
     * @param id The ID of the relationship to delete.
     * @return A 204 response if successful, or a 404 response.
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
     * Find relationships by type.
     * 
     * @param type The type of the relationships.
     * @return The list of relationships.
     */
    @GetMapping("/relationships/type/{type}")
    public ResponseEntity<List<Relationship>> findRelationshipsByType(@PathVariable String type) {
        return ResponseEntity.ok(graphDataService.findRelationshipsByType(type));
    }

    /**
     * Find relationships by node ID.
     * 
     * @param nodeId The ID of the source or target node.
     * @return The list of relationships.
     */
    @GetMapping("/relationships/node/{nodeId}")
    public ResponseEntity<List<Relationship>> findRelationshipsByNodeId(@PathVariable Long nodeId) {
        return ResponseEntity.ok(graphDataService.findRelationshipsByNodeId(nodeId));
    }

    /**
     * Search relationships by type or properties.
     * 
     * @param query The search query.
     * @return The list of relationships.
     */
    @GetMapping("/relationships/search")
    public ResponseEntity<List<Relationship>> searchRelationships(@RequestParam String query) {
        return ResponseEntity.ok(graphDataService.searchRelationships(query));
    }

    /**
     * Get visualization data for graph rendering.
     * 
     * @return A map containing nodes and relationships.
     */
    @GetMapping("/visualization")
    public ResponseEntity<Map<String, Object>> getVisualizationData() {
        return ResponseEntity.ok(graphDataService.getVisualizationData());
    }

    /**
     * Search for nodes and relationships based on a query.
     * 
     * @param query The search query.
     * @return A map containing matching nodes and relationships.
     */
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchGraph(@RequestParam String query) {
        return ResponseEntity.ok(graphDataService.searchGraph(query));
    }
}