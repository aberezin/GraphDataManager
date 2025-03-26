package com.graphapp.model.graph;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@org.springframework.data.neo4j.core.schema.Node
public class Node {
    
    @Id
    @GeneratedValue(UUIDStringGenerator.class)
    private String id;
    
    private String type;
    
    private String label;
    
    private List<String> additionalLabels = new ArrayList<>();
    
    @Property
    private Map<String, Object> properties = new HashMap<>();
    
    public Node() {
    }
    
    public Node(String type) {
        this.type = type;
    }
    
    public Node(String type, String label) {
        this.type = type;
        this.label = label;
    }
    
    public Node(String type, String label, Map<String, Object> properties) {
        this.type = type;
        this.label = label;
        this.properties = properties;
    }

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

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<String> getAdditionalLabels() {
        return additionalLabels;
    }

    public void setAdditionalLabels(List<String> additionalLabels) {
        this.additionalLabels = additionalLabels;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }
    
    public void addProperty(String key, Object value) {
        if (this.properties == null) {
            this.properties = new HashMap<>();
        }
        this.properties.put(key, value);
    }
    
    public void addLabel(String label) {
        if (this.additionalLabels == null) {
            this.additionalLabels = new ArrayList<>();
        }
        this.additionalLabels.add(label);
    }
    
    @Override
    public String toString() {
        return "Node{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", label='" + label + '\'' +
                ", additionalLabels=" + additionalLabels +
                ", properties=" + properties +
                '}';
    }
}