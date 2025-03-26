package com.graphapp.repository.graph;

import com.graphapp.model.graph.Node;
import com.graphapp.model.graph.Relationship;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RelationshipRepository extends Neo4jRepository<Relationship, Long> {
    
    List<Relationship> findByType(String type);
    
    List<Relationship> findBySourceNode(Node sourceNode);
    
    List<Relationship> findByTargetNode(Node targetNode);
    
    @Query("MATCH ()-[r]->() WHERE r.type CONTAINS $query RETURN r")
    List<Relationship> searchRelationships(@Param("query") String query);
    
    @Query("MATCH ()-[r]->() RETURN r LIMIT 100")
    List<Relationship> findLimited();
    
    @Query("MATCH (source)-[r]->(target) WHERE ID(source) = $sourceId AND ID(target) = $targetId RETURN r")
    List<Relationship> findRelationshipsBetweenNodes(@Param("sourceId") Long sourceId, @Param("targetId") Long targetId);
    
    @Query("MATCH ()-[r]->() RETURN count(r)")
    long countRelationships();
}
