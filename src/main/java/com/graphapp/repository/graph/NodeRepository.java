package com.graphapp.repository.graph;

import java.util.List;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import com.graphapp.model.graph.Node;

/**
 * Repository interface for Node entities.
 * This interface provides methods to perform CRUD operations on Neo4j nodes.
 */
@Repository
public interface NodeRepository extends Neo4jRepository<Node, String> {
    
    /**
     * Find nodes by type.
     * 
     * @param type The type of nodes to find.
     * @return A list of nodes with the specified type.
     */
    List<Node> findByType(String type);
    
    /**
     * Find nodes by label.
     * 
     * @param label The label of nodes to find.
     * @return A list of nodes with the specified label.
     */
    List<Node> findByLabel(String label);
    
    /**
     * Search for nodes by label or type containing the given text.
     * 
     * @param searchText The text to search for in labels or types.
     * @return A list of nodes matching the search criteria.
     */
    List<Node> findByLabelContainingIgnoreCaseOrTypeContainingIgnoreCase(String searchText, String searchText2);
    
    /**
     * Check if a node with the given label exists.
     * 
     * @param label The label to check.
     * @return True if a node with the label exists, false otherwise.
     */
    boolean existsByLabel(String label);
}