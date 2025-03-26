package com.graphapp.repository.graph;

import java.util.List;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.graphapp.model.graph.Relationship;

/**
 * Repository interface for Relationship entities.
 * This interface provides methods to perform CRUD operations on Neo4j relationships.
 */
@Repository
public interface RelationshipRepository extends Neo4jRepository<Relationship, String> {
    
    /**
     * Find relationships by type.
     * 
     * @param type The type of relationships to find.
     * @return A list of relationships with the specified type.
     */
    List<Relationship> findByType(String type);
    
    /**
     * Find relationships by source node ID.
     * 
     * @param sourceId The ID of the source node.
     * @return A list of relationships with the specified source node.
     */
    @Query("MATCH (s)-[r]->(t) WHERE ID(s) = $sourceId RETURN r, s, t")
    List<Relationship> findBySourceId(@Param("sourceId") String sourceId);
    
    /**
     * Find relationships by target node ID.
     * 
     * @param targetId The ID of the target node.
     * @return A list of relationships with the specified target node.
     */
    @Query("MATCH (s)-[r]->(t) WHERE ID(t) = $targetId RETURN r, s, t")
    List<Relationship> findByTargetId(@Param("targetId") String targetId);
    
    /**
     * Find relationships by source and target node IDs.
     * 
     * @param sourceId The ID of the source node.
     * @param targetId The ID of the target node.
     * @return A list of relationships between the specified source and target nodes.
     */
    @Query("MATCH (s)-[r]->(t) WHERE ID(s) = $sourceId AND ID(t) = $targetId RETURN r, s, t")
    List<Relationship> findBySourceIdAndTargetId(
            @Param("sourceId") String sourceId, 
            @Param("targetId") String targetId);
    
    /**
     * Search for relationships by type containing the given text.
     * 
     * @param searchText The text to search for in relationship types.
     * @return A list of relationships matching the search criteria.
     */
    List<Relationship> findByTypeContainingIgnoreCase(String searchText);
}