package com.graphapp.controller;

import com.graphapp.model.graph.Node;
import com.graphapp.model.graph.Relationship;
import com.graphapp.service.GraphDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST controller for graph data operations.
 */
@RestController
@RequestMapping("/api/graph")
@CrossOrigin(origins = "*")
public class GraphDataController {

    private final GraphDataService graphDataService;

    @Autowired
    public GraphDataController(GraphDataService graphDataService) {
        this.graphDataService = graphDataService;
    }

    /**
     * Get all nodes.
     * @return ResponseEntity containing a list of all nodes
     */
    @GetMapping("/nodes")
    public ResponseEntity<List<Node>> getAllNodes() {
        List<Node> nodes = graphDataService.getAllNodes();
        return new ResponseEntity<>(nodes, HttpStatus.OK);
    }

    /**
     * Get a node by ID.
     * @param id ID of the node to find
     * @return ResponseEntity containing the node if found
     */
    @GetMapping("/nodes/{id}")
    public ResponseEntity<Node> getNodeById(@PathVariable Long id) {
        return graphDataService.getNodeById(id)
                .map(node -> new ResponseEntity<>(node, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Create a new node.
     * @param node Node to create
     * @return ResponseEntity containing the created node
     */
    @PostMapping("/nodes")
    public ResponseEntity<Node> createNode(@RequestBody Node node) {
        try {
            Node createdNode = graphDataService.createNode(node);
            return new ResponseEntity<>(createdNode, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Update an existing node.
     * @param id ID of the node to update
     * @param node Updated node details
     * @return ResponseEntity containing the updated node
     */
    @PutMapping("/nodes/{id}")
    public ResponseEntity<Node> updateNode(@PathVariable Long id, @RequestBody Node node) {
        try {
            Node updatedNode = graphDataService.updateNode(id, node);
            return new ResponseEntity<>(updatedNode, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Delete a node by ID.
     * @param id ID of the node to delete
     * @return ResponseEntity with no content
     */
    @DeleteMapping("/nodes/{id}")
    public ResponseEntity<Void> deleteNode(@PathVariable Long id) {
        try {
            graphDataService.deleteNode(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Search nodes by a query string.
     * @param query Query string to search for
     * @return ResponseEntity containing a list of nodes matching the search query
     */
    @GetMapping("/nodes/search")
    public ResponseEntity<List<Node>> searchNodes(@RequestParam String query) {
        List<Node> nodes = graphDataService.searchNodes(query);
        return new ResponseEntity<>(nodes, HttpStatus.OK);
    }

    /**
     * Get all relationships.
     * @return ResponseEntity containing a list of all relationships
     */
    @GetMapping("/relationships")
    public ResponseEntity<List<Relationship>> getAllRelationships() {
        List<Relationship> relationships = graphDataService.getAllRelationships();
        return new ResponseEntity<>(relationships, HttpStatus.OK);
    }

    /**
     * Get a relationship by ID.
     * @param id ID of the relationship to find
     * @return ResponseEntity containing the relationship if found
     */
    @GetMapping("/relationships/{id}")
    public ResponseEntity<Relationship> getRelationshipById(@PathVariable Long id) {
        return graphDataService.getRelationshipById(id)
                .map(relationship -> new ResponseEntity<>(relationship, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Create a new relationship.
     * @param relationship Relationship to create
     * @return ResponseEntity containing the created relationship
     */
    @PostMapping("/relationships")
    public ResponseEntity<Relationship> createRelationship(@RequestBody Relationship relationship) {
        try {
            Relationship createdRelationship = graphDataService.createRelationship(relationship);
            return new ResponseEntity<>(createdRelationship, HttpStatus.CREATED);
        } catch (RuntimeException | IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Update an existing relationship.
     * @param id ID of the relationship to update
     * @param relationship Updated relationship details
     * @return ResponseEntity containing the updated relationship
     */
    @PutMapping("/relationships/{id}")
    public ResponseEntity<Relationship> updateRelationship(@PathVariable Long id, @RequestBody Relationship relationship) {
        try {
            Relationship updatedRelationship = graphDataService.updateRelationship(id, relationship);
            return new ResponseEntity<>(updatedRelationship, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Delete a relationship by ID.
     * @param id ID of the relationship to delete
     * @return ResponseEntity with no content
     */
    @DeleteMapping("/relationships/{id}")
    public ResponseEntity<Void> deleteRelationship(@PathVariable Long id) {
        try {
            graphDataService.deleteRelationship(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Search relationships by a query string.
     * @param query Query string to search for
     * @return ResponseEntity containing a list of relationships matching the search query
     */
    @GetMapping("/relationships/search")
    public ResponseEntity<List<Relationship>> searchRelationships(@RequestParam String query) {
        List<Relationship> relationships = graphDataService.searchRelationships(query);
        return new ResponseEntity<>(relationships, HttpStatus.OK);
    }

    /**
     * Get data for graph visualization.
     * @return ResponseEntity containing nodes and relationships for visualization
     */
    @GetMapping("/visualization")
    public ResponseEntity<Map<String, Object>> getGraphVisualizationData() {
        Map<String, Object> visualizationData = graphDataService.getGraphVisualizationData();
        return new ResponseEntity<>(visualizationData, HttpStatus.OK);
    }

    /**
     * Search for nodes and relationships.
     * @param query Query string to search for
     * @return ResponseEntity containing nodes and relationships matching the search query
     */
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchGraph(@RequestParam String query) {
        List<Node> nodes = graphDataService.searchNodes(query);
        List<Relationship> relationships = graphDataService.searchRelationships(query);
        
        Map<String, Object> result = new HashMap<>();
        result.put("nodes", nodes);
        result.put("relationships", relationships);
        
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}