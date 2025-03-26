package com.graphapp.model.graph;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

import java.util.HashMap;
import java.util.Map;

@RelationshipProperties
public class Relationship {
    
    @Id
    @GeneratedValue(UUIDStringGenerator.class)
    private String id;
    
    private String type;
    
    @TargetNode
    private Node target;
    
    private Node source;
    
    private Map<String, Object> properties = new HashMap<>();
    
    // Default constructor
    public Relationship() {
    }
    
    public Relationship(String type, Node source, Node target) {
        this.type = type;
        this.source = source;
        this.target = target;
    }
    
    public Relationship(String type, Node source, Node target, Map<String, Object> properties) {
        this.type = type;
        this.source = source;
        this.target = target;
        this.properties = properties;
    }
    
    // Getters and Setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public Node getSource() {
        return source;
    }
    
    public void setSource(Node source) {
        this.source = source;
    }
    
    public Node getTarget() {
        return target;
    }
    
    public void setTarget(Node target) {
        this.target = target;
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
    
    public void removeProperty(String key) {
        this.properties.remove(key);
    }
}