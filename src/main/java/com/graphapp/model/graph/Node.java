package com.graphapp.model.graph;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Property;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a node in the Neo4j graph database.
 */
@org.springframework.data.neo4j.core.schema.Node("Node")
public class Node {
    
    @Id
    @GeneratedValue
    private Long id;
    
    @Property("label")
    private String label;
    
    @Property("type")
    private String type;
    
    @Property("properties")
    private Map<String, Object> properties = new HashMap<>();

    public Node() {
    }

    public Node(String label, String type) {
        this.label = label;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
        return "Node{" +
                "id=" + id +
                ", label='" + label + '\'' +
                ", type='" + type + '\'' +
                ", properties=" + properties +
                '}';
    }
}
