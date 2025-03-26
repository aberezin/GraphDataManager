package com.graphapp.model.graph;

import org.springframework.data.neo4j.core.schema.*;

import java.util.HashMap;
import java.util.Map;

@RelationshipProperties
public class Relationship {
    
    @Id
    @GeneratedValue
    private Long id;
    
    @Property("type")
    private String type;
    
    @TargetNode
    private Node target;
    
    @DynamicProperties
    private Map<String, Object> properties = new HashMap<>();
    
    // Default constructor
    public Relationship() {
    }
    
    public Relationship(String type, Node target) {
        this.type = type;
        this.target = target;
    }
    
    // Getters and Setters
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
}