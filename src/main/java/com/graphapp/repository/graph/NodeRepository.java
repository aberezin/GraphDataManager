package com.graphapp.repository.graph;

import com.graphapp.model.graph.Node;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NodeRepository extends Neo4jRepository<Node, Long> {
    
    Optional<Node> findByLabel(String label);
    
    List<Node> findByType(String type);
    
    @Query("MATCH (n) WHERE toLower(n.label) CONTAINS toLower($searchTerm) OR " +
           "toLower(n.type) CONTAINS toLower($searchTerm) RETURN n")
    List<Node> searchNodes(@Param("searchTerm") String searchTerm);
    
    @Query("MATCH (n)-[r]-(m) RETURN n, r, m")
    List<Node> findAllNodesWithRelationships();
    
    @Query("MATCH (n) WHERE id(n) = $nodeId MATCH (n)-[r]-(m) RETURN n, r, m")
    Optional<Node> findNodeWithRelationships(@Param("nodeId") Long nodeId);
    
    @Query("MATCH (n) WHERE id(n) = $nodeId " +
           "MATCH (n)-[r]->(m) RETURN n, r, m")
    List<Node> findOutgoingRelationships(@Param("nodeId") Long nodeId);
    
    @Query("MATCH (n) WHERE id(n) = $nodeId " +
           "MATCH (n)<-[r]-(m) RETURN n, r, m")
    List<Node> findIncomingRelationships(@Param("nodeId") Long nodeId);
}