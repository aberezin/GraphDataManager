package com.graphapp.repository.graph;

import com.graphapp.model.graph.Node;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Node entities in Neo4j.
 */
@Repository
public interface NodeRepository extends Neo4jRepository<Node, Long> {

    /**
     * Find all nodes by type.
     * @param type Type of the nodes to find
     * @return List of nodes with the given type
     */
    List<Node> findByType(String type);

    /**
     * Find all nodes by label.
     * @param label Label of the nodes to find
     * @return List of nodes with the given label
     */
    List<Node> findByLabel(String label);

    /**
     * Find a node by label and type.
     * @param label Label of the node to find
     * @param type Type of the node to find
     * @return Optional containing the node if found
     */
    Optional<Node> findByLabelAndType(String label, String type);

    /**
     * Search nodes by a text query.
     * @param query Text to search for in node properties
     * @return List of nodes matching the search query
     */
    @Query("MATCH (n:Node) WHERE n.label CONTAINS $query OR n.type CONTAINS $query " +
           "OR ANY(key IN keys(n) WHERE n[key] CONTAINS $query) RETURN n")
    List<Node> searchNodes(@Param("query") String query);

    /**
     * Find all nodes that have a relationship with a given node.
     * @param nodeId ID of the node to find related nodes for
     * @return List of nodes related to the given node
     */
    @Query("MATCH (n:Node)-[r]-(m:Node) WHERE ID(n) = $nodeId RETURN m")
    List<Node> findRelatedNodes(@Param("nodeId") Long nodeId);

    /**
     * Find all nodes with a specific property value.
     * @param propertyKey The property key to check
     * @param propertyValue The property value to find
     * @return List of nodes with the given property value
     */
    @Query("MATCH (n:Node) WHERE n[$propertyKey] = $propertyValue RETURN n")
    List<Node> findByProperty(@Param("propertyKey") String propertyKey, @Param("propertyValue") Object propertyValue);
}