package com.graphapp.repository.graph;

import com.graphapp.model.graph.Node;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NodeRepository extends Neo4jRepository<Node, String> {
    
    List<Node> findByType(String type);
    
    List<Node> findByLabel(String label);
    
    @Query("MATCH (n) WHERE n.label CONTAINS $searchTerm OR n.type CONTAINS $searchTerm RETURN n")
    List<Node> searchNodes(@Param("searchTerm") String searchTerm);
    
    @Query("MATCH (n)-[r]-() RETURN DISTINCT n")
    List<Node> findNodesWithRelationships();
}