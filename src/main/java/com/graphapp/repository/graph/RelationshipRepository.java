package com.graphapp.repository.graph;

import com.graphapp.model.graph.Relationship;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RelationshipRepository extends Neo4jRepository<Relationship, String> {
    
    List<Relationship> findByType(String type);
    
    @Query("MATCH (s)-[r]->(t) WHERE r.type CONTAINS $searchTerm RETURN r, s, t")
    List<Relationship> searchRelationships(@Param("searchTerm") String searchTerm);
    
    @Query("MATCH (s)-[r]->(t) RETURN r, s, t")
    List<Relationship> findAllRelationshipsWithNodes();
    
    @Query("MATCH (s)-[r]->(t) WHERE id(s) = $sourceId AND id(t) = $targetId RETURN r")
    List<Relationship> findRelationshipsBetweenNodes(
            @Param("sourceId") String sourceId, 
            @Param("targetId") String targetId);
}