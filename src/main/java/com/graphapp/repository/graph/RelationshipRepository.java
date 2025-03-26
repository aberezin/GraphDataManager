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
    
    @Query("MATCH (source)-[r]->(target) RETURN source, r, target")
    List<Relationship> findAllRelationshipsWithNodes();
    
    @Query("MATCH (source)-[r]->(target) WHERE " +
           "toLower(r.type) CONTAINS toLower($query) OR " +
           "ANY(prop IN keys(r) WHERE toLower(toString(r[prop])) CONTAINS toLower($query)) OR " +
           "toLower(source.label) CONTAINS toLower($query) OR " +
           "toLower(target.label) CONTAINS toLower($query) " +
           "RETURN source, r, target")
    List<Relationship> searchRelationships(@Param("query") String query);
}