package com.graphapp.repository.graph;

import com.graphapp.model.graph.GraphNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Repository for Node entities in Neo4j.
 */
@Repository
public interface NodeRepository extends Neo4jRepository<GraphNode, Long> {
    
    /**
     * Find nodes by type.
     * 
     * @param type The type of the nodes.
     * @return The list of nodes.
     */
    List<GraphNode> findByType(String type);
    
    /**
     * Find nodes by label.
     * 
     * @param label The label of the nodes.
     * @return The list of nodes.
     */
    List<GraphNode> findByLabel(String label);
    
    /**
     * Find nodes by type and label.
     * 
     * @param type The type of the nodes.
     * @param label The label of the nodes.
     * @return The list of nodes.
     */
    List<GraphNode> findByTypeAndLabel(String type, String label);
    
    /**
     * Find nodes by a specific property value.
     * 
     * @param propertyName The name of the property.
     * @param propertyValue The value of the property.
     * @return The list of nodes.
     */
    @Query("MATCH (n) WHERE n.properties[$propertyName] = $propertyValue RETURN n")
    List<GraphNode> findByProperty(@Param("propertyName") String propertyName, 
                              @Param("propertyValue") Object propertyValue);
    
    /**
     * Search nodes by label, type, or properties.
     * 
     * @param query The search query.
     * @return The list of nodes.
     */
    @Query("MATCH (n) WHERE n.label CONTAINS $query OR n.type CONTAINS $query RETURN n")
    List<GraphNode> searchNodes(@Param("query") String query);
    
    /**
     * Find nodes with a specific label in their labels list.
     * 
     * @param label The label to search for.
     * @return The list of nodes.
     */
    @Query("MATCH (n) WHERE $label IN n.labels RETURN n")
    List<GraphNode> findByLabelInList(@Param("label") String label);
    
    /**
     * Get the count of nodes by type.
     * 
     * @return The map of type to count.
     */
    @Query("MATCH (n) RETURN n.type AS type, COUNT(n) AS count")
    List<Map<String, Object>> countByType();
    
    /**
     * Find nodes that are connected to the given node through any relationship.
     * 
     * @param nodeId The ID of the node.
     * @return The list of nodes.
     */
    @Query("MATCH (n)-[r]-(m) WHERE ID(m) = $nodeId RETURN DISTINCT n")
    List<GraphNode> findConnectedNodes(@Param("nodeId") Long nodeId);
    
    /**
     * Find nodes that are connected to the given node through a specific relationship type.
     * 
     * @param nodeId The ID of the node.
     * @param relationshipType The type of the relationship.
     * @return The list of nodes.
     */
    @Query("MATCH (n)-[r:$relationshipType]-(m) WHERE ID(m) = $nodeId RETURN DISTINCT n")
    List<GraphNode> findConnectedNodesByRelationshipType(@Param("nodeId") Long nodeId, 
                                                  @Param("relationshipType") String relationshipType);
}