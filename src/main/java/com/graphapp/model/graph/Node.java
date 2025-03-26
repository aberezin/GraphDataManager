package com.graphapp.model.graph;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.DynamicLabels;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@org.springframework.data.neo4j.core.schema.Node
public class Node {
    
    @Id
    @GeneratedValue(UUIDStringGenerator.class)
    private String id;
    
    private String label;
    
    private String type;
    
    @DynamicLabels
    private Set<String> additionalLabels = new HashSet<>();
    
    private Map<String, Object> properties = new HashMap<>();
    
    // Default constructor
    public Node() {
    }
    
    public Node(String label, String type) {
        this.label = label;
        this.type = type;
    }
    
    public Node(String label, String type, Map<String, Object> properties) {
        this.label = label;
        this.type = type;
        this.properties = properties;
    }
    
    // Getters and Setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getLabel() {
        return label;
    }
    
    public void setLabel(String label) {
        this.label = label;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public Set<String> getAdditionalLabels() {
        return additionalLabels;
    }
    
    public void setAdditionalLabels(Set<String> additionalLabels) {
        this.additionalLabels = additionalLabels;
    }
    
    public void addLabel(String label) {
        this.additionalLabels.add(label);
    }
    
    public void removeLabel(String label) {
        this.additionalLabels.remove(label);
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