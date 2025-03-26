package com.graphapp.repository.graph;

import com.graphapp.model.graph.Relationship;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for graph Relationship entities
 */
@Repository
public interface RelationshipRepository extends Neo4jRepository<Relationship, Long> {

    /**
     * Find relationships by type
     */
    @Query("MATCH (n)-[r]->(m) WHERE type(r) = $type RETURN r, n, m")
    List<Relationship> findByType(@Param("type") String type);

    /**
     * Find relationships by source node id
     */
    @Query("MATCH (n)-[r]->(m) WHERE id(n) = $nodeId RETURN r, n, m")
    List<Relationship> findBySourceNodeId(@Param("nodeId") Long nodeId);

    /**
     * Find relationships by target node id
     */
    @Query("MATCH (n)-[r]->(m) WHERE id(m) = $nodeId RETURN r, n, m")
    List<Relationship> findByTargetNodeId(@Param("nodeId") Long nodeId);

    /**
     * Find relationships where node appears as either source or target
     */
    @Query("MATCH (n)-[r]-(m) WHERE id(n) = $nodeId RETURN r, n, m")
    List<Relationship> findByNodeId(@Param("nodeId") Long nodeId);

    /**
     * Find relationships by property value
     */
    @Query("MATCH (n)-[r]->(m) WHERE r.properties[$key] = $value RETURN r, n, m")
    List<Relationship> findByProperty(@Param("key") String key, @Param("value") Object value);

    /**
     * Search relationships by text (searches in type and string properties)
     */
    @Query("MATCH (n)-[r]->(m) " +
           "WHERE toLower(type(r)) CONTAINS toLower($text) OR " +
           "ANY(k IN keys(r.properties) WHERE " +
           "  (r.properties[k] IS STRING AND toLower(r.properties[k]) CONTAINS toLower($text))) " +
           "RETURN r, n, m")
    List<Relationship> searchRelationships(@Param("text") String text);

    /**
     * Get all relationships with their connected nodes
     */
    @Query("MATCH (n)-[r]->(m) RETURN r, n, m")
    List<Relationship> findAllWithNodes();
}