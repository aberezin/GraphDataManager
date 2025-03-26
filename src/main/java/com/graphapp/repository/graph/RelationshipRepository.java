package com.graphapp.repository.graph;

import com.graphapp.model.graph.Relationship;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RelationshipRepository extends Neo4jRepository<Relationship, Long> {
    
    List<Relationship> findByType(String type);
    
    @Query("MATCH (n)-[r]->(m) WHERE id(r) = $relationshipId RETURN r")
    Relationship findRelationshipById(@Param("relationshipId") Long relationshipId);
    
    @Query("MATCH (n)-[r]->(m) WHERE id(n) = $sourceId AND id(m) = $targetId RETURN r")
    List<Relationship> findRelationshipsBetweenNodes(@Param("sourceId") Long sourceId, @Param("targetId") Long targetId);
    
    @Query("MATCH (n)-[r]->(m) WHERE toLower(r.type) CONTAINS toLower($searchTerm) RETURN r, n, m")
    List<Relationship> searchRelationships(@Param("searchTerm") String searchTerm);
    
    @Query("MATCH (n)-[r]->(m) RETURN r, n, m")
    List<Relationship> findAllRelationshipsWithNodes();
    
    @Query("MATCH (n)-[r]->(m) WHERE id(n) = $nodeId RETURN r, n, m")
    List<Relationship> findRelationshipsWithSourceNode(@Param("nodeId") Long nodeId);
    
    @Query("MATCH (n)-[r]->(m) WHERE id(m) = $nodeId RETURN r, n, m")
    List<Relationship> findRelationshipsWithTargetNode(@Param("nodeId") Long nodeId);
}