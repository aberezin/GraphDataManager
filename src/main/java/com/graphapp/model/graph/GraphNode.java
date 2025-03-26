package com.graphapp.model.graph;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a Node in the graph database.
 */
@Node
public class GraphNode {
    
    @Id
    @GeneratedValue
    private Long id;
    
    @Property("label")
    private String label;
    
    @Property("type")
    private String type;
    
    @Property("labels")
    private List<String> labels;
    
    @Property("properties")
    private Map<String, Object> properties;
    
    /**
     * Default constructor.
     */
    public GraphNode() {
        this.labels = new ArrayList<>();
        this.properties = new HashMap<>();
    }
    
    /**
     * Constructor with label and type.
     * 
     * @param label The label of the node.
     * @param type The type of the node.
     */
    public GraphNode(String label, String type) {
        this();
        this.label = label;
        this.type = type;
    }
    
    /**
     * Constructor with all fields.
     * 
     * @param label The label of the node.
     * @param type The type of the node.
     * @param labels The list of labels.
     * @param properties The map of properties.
     */
    public GraphNode(String label, String type, List<String> labels, Map<String, Object> properties) {
        this.label = label;
        this.type = type;
        this.labels = labels != null ? labels : new ArrayList<>();
        this.properties = properties != null ? properties : new HashMap<>();
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
    }
    
    /**
     * Get the list of labels.
     * 
     * @return The list of labels.
     */
    public List<String> getLabels() {
        return labels;
    }
    
    /**
     * Set the list of labels.
     * 
     * @param labels The list of labels.
     */
    public void setLabels(List<String> labels) {
        this.labels = labels;
    }
    
    /**
     * Add a label to the list of labels.
     * 
     * @param label The label to add.
     */
    public void addLabel(String label) {
        if (this.labels == null) {
            this.labels = new ArrayList<>();
        }
        this.labels.add(label);
    }
    
    /**
     * Get the map of properties.
     * 
     * @return The map of properties.
     */
    public Map<String, Object> getProperties() {
        return properties;
    }
    
    /**
     * Set the map of properties.
     * 
     * @param properties The map of properties.
     */
    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }
    
    /**
     * Add a property to the map of properties.
     * 
     * @param key The key of the property.
     * @param value The value of the property.
     */
    public void addProperty(String key, Object value) {
        if (this.properties == null) {
            this.properties = new HashMap<>();
        }
        this.properties.put(key, value);
    }
    
    /**
     * Get a property from the map of properties.
     * 
     * @param key The key of the property.
     * @return The value of the property.
     */
    public Object getProperty(String key) {
        return this.properties != null ? this.properties.get(key) : null;
    }
    
    /**
     * Check if the node has a property.
     * 
     * @param key The key of the property.
     * @return True if the node has the property, false otherwise.
     */
    public boolean hasProperty(String key) {
        return this.properties != null && this.properties.containsKey(key);
    }
    
    /**
     * Remove a property from the map of properties.
     * 
     * @param key The key of the property.
     * @return The removed value, or null if the property was not found.
     */
    public Object removeProperty(String key) {
        return this.properties != null ? this.properties.remove(key) : null;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        GraphNode node = (GraphNode) o;
        
        return id != null ? id.equals(node.id) : node.id == null;
    }
    
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
    
    @Override
    public String toString() {
        return "GraphNode{" +
                "id=" + id +
                ", label='" + label + '\'' +
                ", type='" + type + '\'' +
                ", labels=" + labels +
                ", properties=" + properties +
                '}';
    }
}