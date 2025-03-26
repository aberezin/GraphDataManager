package com.graphapp.model.graph;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.DynamicLabels;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Represents a node in the graph database.
 */
@org.springframework.data.neo4j.core.schema.Node
public class Node {
    
    @Id
    @GeneratedValue
    private Long id;
    
    @Property("label")
    private String label;
    
    @Property("type")
    private String type;
    
    @DynamicLabels
    private Set<String> labels = new HashSet<>();
    
    @Property("properties")
    private Map<String, Object> properties;
    
    /**
     * Default constructor.
     */
    public Node() {
    }
    
    /**
     * Constructor with label and type.
     * 
     * @param label The label of the node.
     * @param type The type of the node.
     */
    public Node(String label, String type) {
        this.label = label;
        this.type = type;
        if (type != null) {
            this.labels.add(type);
        }
    }
    
    /**
     * Constructor with label, type and properties.
     * 
     * @param label The label of the node.
     * @param type The type of the node.
     * @param properties The properties of the node.
     */
    public Node(String label, String type, Map<String, Object> properties) {
        this.label = label;
        this.type = type;
        this.properties = properties;
        if (type != null) {
            this.labels.add(type);
        }
    }
    
    /**
     * Get the ID of the node.
     * 
     * @return The ID.
     */
    public Long getId() {
        return id;
    }
    
    /**
     * Set the ID of the node.
     * 
     * @param id The ID.
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    /**
     * Get the label of the node.
     * 
     * @return The label.
     */
    public String getLabel() {
        return label;
    }
    
    /**
     * Set the label of the node.
     * 
     * @param label The label.
     */
    public void setLabel(String label) {
        this.label = label;
    }
    
    /**
     * Get the type of the node.
     * 
     * @return The type.
     */
    public String getType() {
        return type;
    }
    
    /**
     * Set the type of the node.
     * 
     * @param type The type.
     */
    public void setType(String type) {
        this.type = type;
        if (this.labels == null) {
            this.labels = new HashSet<>();
        }
        if (type != null) {
            this.labels.add(type);
        }
    }
    
    /**
     * Get the labels of the node.
     * 
     * @return The labels.
     */
    public Set<String> getLabels() {
        return labels;
    }
    
    /**
     * Set the labels of the node.
     * 
     * @param labels The labels.
     */
    public void setLabels(Set<String> labels) {
        this.labels = labels;
    }
    
    /**
     * Add a label to the node.
     * 
     * @param label The label to add.
     */
    public void addLabel(String label) {
        if (this.labels == null) {
            this.labels = new HashSet<>();
        }
        this.labels.add(label);
    }
    
    /**
     * Get the properties of the node.
     * 
     * @return The properties.
     */
    public Map<String, Object> getProperties() {
        return properties;
    }
    
    /**
     * Set the properties of the node.
     * 
     * @param properties The properties.
     */
    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }
    
    /**
     * Add a property to the node.
     * 
     * @param key The property key.
     * @param value The property value.
     */
    public void addProperty(String key, Object value) {
        if (this.properties != null) {
            this.properties.put(key, value);
        }
    }
    
    @Override
    public String toString() {
        return "Node{" +
                "id=" + id +
                ", label='" + label + '\'' +
                ", type='" + type + '\'' +
                ", labels=" + labels +
                ", properties=" + properties +
                '}';
    }
}