package com.graphapp.repository.graph;

import com.graphapp.model.graph.Node;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NodeRepository extends Neo4jRepository<Node, Long> {
    
    List<Node> findByType(String type);
    
    List<Node> findByLabel(String label);
    
    @Query("MATCH (n) WHERE n.label CONTAINS $query OR n.type CONTAINS $query RETURN n")
    List<Node> searchNodes(@Param("query") String query);
    
    @Query("MATCH (n) RETURN n LIMIT 100")
    List<Node> findLimited();

    @Query("MATCH (n) RETURN count(n)")
    long countNodes();
}
