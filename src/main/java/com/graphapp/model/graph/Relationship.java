package com.graphapp.model.graph;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a relationship between nodes in the Neo4j graph database.
 */
@RelationshipProperties
public class Relationship {
    
    @Id
    @GeneratedValue
    private Long id;
    
    private String type;
    
    @TargetNode
    private Node sourceNode;
    
    @TargetNode
    private Node targetNode;
    
    private Map<String, Object> properties = new HashMap<>();

    public Relationship() {
    }

    public Relationship(String type, Node sourceNode, Node targetNode) {
        this.type = type;
        this.sourceNode = sourceNode;
        this.targetNode = targetNode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Node getSourceNode() {
        return sourceNode;
    }

    public void setSourceNode(Node sourceNode) {
        this.sourceNode = sourceNode;
    }

    public Node getTargetNode() {
        return targetNode;
    }

    public void setTargetNode(Node targetNode) {
        this.targetNode = targetNode;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    public void addProperty(String key, Object value) {
        this.properties.put(key, value);
    }

    public Object getProperty(String key) {
        return this.properties.get(key);
    }

    @Override
    public String toString() {
        return "Relationship{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", sourceNode=" + sourceNode +
                ", targetNode=" + targetNode +
                ", properties=" + properties +
                '}';
    }
}
