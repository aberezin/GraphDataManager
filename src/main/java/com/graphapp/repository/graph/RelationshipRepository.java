package com.graphapp.repository.graph;

import com.graphapp.model.graph.GraphNode;
import com.graphapp.model.graph.Relationship;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Repository for Relationship entities in Neo4j.
 */
@Repository
public interface RelationshipRepository extends Neo4jRepository<Relationship, Long> {
    
    /**
     * Find relationships by type.
     * 
     * @param type The type of the relationships.
     * @return The list of relationships.
     */
    List<Relationship> findByType(String type);
    
    /**
     * Find relationships by source node.
     * 
     * @param source The source node.
     * @return The list of relationships.
     */
    List<Relationship> findBySource(GraphNode source);
    
    /**
     * Find relationships by target node.
     * 
     * @param target The target node.
     * @return The list of relationships.
     */
    List<Relationship> findByTarget(GraphNode target);
    
    /**
     * Find relationships by source and target nodes.
     * 
     * @param source The source node.
     * @param target The target node.
     * @return The list of relationships.
     */
    List<Relationship> findBySourceAndTarget(GraphNode source, GraphNode target);
    
    /**
     * Find relationships by source node ID or target node ID.
     * 
     * @param nodeId The ID of the source or target node.
     * @return The list of relationships.
     */
    @Query("MATCH (a)-[r]->(b) WHERE ID(a) = $nodeId OR ID(b) = $nodeId RETURN r")
    List<Relationship> findByNodeId(@Param("nodeId") Long nodeId);
    
    /**
     * Search relationships by type or properties.
     * 
     * @param query The search query.
     * @return The list of relationships.
     */
    @Query("MATCH (a)-[r]->(b) WHERE r.type CONTAINS $query RETURN r, a, b")
    List<Relationship> searchRelationships(@Param("query") String query);
    
    /**
     * Find relationships with a specific property value.
     * 
     * @param propertyName The name of the property.
     * @param propertyValue The value of the property.
     * @return The list of relationships.
     */
    @Query("MATCH (a)-[r]->(b) WHERE r.properties[$propertyName] = $propertyValue RETURN r, a, b")
    List<Relationship> findByProperty(@Param("propertyName") String propertyName, 
                                     @Param("propertyValue") Object propertyValue);
    
    /**
     * Get the count of relationships by type.
     * 
     * @return The map of type to count.
     */
    @Query("MATCH ()-[r]->() RETURN r.type AS type, COUNT(r) AS count")
    List<Map<String, Object>> countByType();
    
    /**
     * Get all relationships with their source and target nodes.
     * 
     * @return The list of relationships.
     */
    @Query("MATCH (a)-[r]->(b) RETURN r, a, b")
    List<Relationship> findAllWithNodes();
}