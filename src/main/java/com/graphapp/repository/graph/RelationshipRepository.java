package com.graphapp.repository.graph;

import com.graphapp.model.graph.Relationship;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Relationship entities in Neo4j.
 */
@Repository
public interface RelationshipRepository extends Neo4jRepository<Relationship, Long> {

    /**
     * Find all relationships by type.
     * @param type Type of the relationships to find
     * @return List of relationships with the given type
     */
    List<Relationship> findByType(String type);

    /**
     * Find all relationships where a specific node is the target.
     * @param nodeId ID of the target node
     * @return List of relationships targeting the given node
     */
    @Query("MATCH (n:Node)-[r]->(m:Node) WHERE ID(m) = $nodeId RETURN r, n, m")
    List<Relationship> findByTargetNodeId(@Param("nodeId") Long nodeId);

    /**
     * Find all relationships where a specific node is the source.
     * @param nodeId ID of the source node
     * @return List of relationships originating from the given node
     */
    @Query("MATCH (n:Node)-[r]->(m:Node) WHERE ID(n) = $nodeId RETURN r, n, m")
    List<Relationship> findBySourceNodeId(@Param("nodeId") Long nodeId);

    /**
     * Find all relationships between two nodes.
     * @param sourceNodeId ID of the source node
     * @param targetNodeId ID of the target node
     * @return List of relationships between the given nodes
     */
    @Query("MATCH (n:Node)-[r]->(m:Node) WHERE ID(n) = $sourceNodeId AND ID(m) = $targetNodeId RETURN r, n, m")
    List<Relationship> findBySourceAndTargetNodeIds(@Param("sourceNodeId") Long sourceNodeId, @Param("targetNodeId") Long targetNodeId);

    /**
     * Find all relationships with a specific property value.
     * @param propertyKey The property key to check
     * @param propertyValue The property value to find
     * @return List of relationships with the given property value
     */
    @Query("MATCH (n:Node)-[r]->(m:Node) WHERE r[$propertyKey] = $propertyValue RETURN r, n, m")
    List<Relationship> findByProperty(@Param("propertyKey") String propertyKey, @Param("propertyValue") Object propertyValue);

    /**
     * Search relationships by a text query.
     * @param query Text to search for in relationship properties
     * @return List of relationships matching the search query
     */
    @Query("MATCH (n:Node)-[r]->(m:Node) WHERE r.type CONTAINS $query " +
           "OR ANY(key IN keys(r) WHERE r[key] CONTAINS $query) RETURN r, n, m")
    List<Relationship> searchRelationships(@Param("query") String query);
}