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
    
    @Query("MATCH (n) WHERE toLower(n.label) CONTAINS toLower($query) OR " +
           "toLower(n.type) CONTAINS toLower($query) OR " +
           "ANY(prop IN keys(n) WHERE toLower(toString(n[prop])) CONTAINS toLower($query)) " +
           "RETURN n")
    List<Node> searchNodes(@Param("query") String query);
}