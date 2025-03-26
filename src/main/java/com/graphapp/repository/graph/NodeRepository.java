package com.graphapp.repository.graph;

import com.graphapp.model.graph.Node;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for graph Node entities
 */
@Repository
public interface NodeRepository extends Neo4jRepository<Node, Long> {

    /**
     * Find nodes by type
     */
    @Query("MATCH (n) WHERE n.type = $type RETURN n")
    List<Node> findByType(@Param("type") String type);

    /**
     * Find nodes by label
     */
    @Query("MATCH (n) WHERE n.label = $label RETURN n")
    List<Node> findByLabel(@Param("label") String label);

    /**
     * Find nodes by property value
     */
    @Query("MATCH (n) WHERE n.properties[$key] = $value RETURN n")
    List<Node> findByProperty(@Param("key") String key, @Param("value") Object value);

    /**
     * Search nodes by text (searches in type, label and string properties)
     */
    @Query("MATCH (n) " +
           "WHERE toLower(n.type) CONTAINS toLower($text) OR " +
           "toLower(n.label) CONTAINS toLower($text) OR " +
           "ANY(k IN keys(n.properties) WHERE " +
           "  (n.properties[k] IS STRING AND toLower(n.properties[k]) CONTAINS toLower($text))) " +
           "RETURN n")
    List<Node> searchNodes(@Param("text") String text);

    /**
     * Get all nodes with their connected relationships
     */
    @Query("MATCH (n)-[r]-(m) RETURN n, r, m")
    List<Node> findAllWithRelationships();
}