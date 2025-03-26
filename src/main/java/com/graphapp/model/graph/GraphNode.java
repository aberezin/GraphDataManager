package com.graphapp.model.graph;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Node;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
    
    // Use a transient map for properties in memory
    @JsonIgnore
    private transient Map<String, Object> propertiesMap;
    
    // Store properties as a JSON string in Neo4j
    @Property("prop_json")
    private String propertyJson;
    
    /**
     * Default constructor.
     */
    public GraphNode() {
        this.labels = new ArrayList<>();
        this.propertiesMap = new HashMap<>();
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
        this.propertiesMap = properties != null ? properties : new HashMap<>();
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
        return propertiesMap;
    }
    
    /**
     * Set the map of properties.
     * 
     * @param properties The map of properties.
     */
    public void setProperties(Map<String, Object> properties) {
        this.propertiesMap = properties;
    }
    
    /**
     * Add a property to the map of properties.
     * 
     * @param key The key of the property.
     * @param value The value of the property.
     */
    public void addProperty(String key, Object value) {
        if (this.propertiesMap == null) {
            this.propertiesMap = new HashMap<>();
        }
        // Only store primitive values or String values
        if (value == null || value instanceof String || value instanceof Number || value instanceof Boolean) {
            this.propertiesMap.put(key, value);
        } else {
            // Convert non-primitive objects to string representation
            this.propertiesMap.put(key, value.toString());
        }
    }
    
    /**
     * Get a property from the map of properties.
     * 
     * @param key The key of the property.
     * @return The value of the property.
     */
    public Object getProperty(String key) {
        return this.propertiesMap != null ? this.propertiesMap.get(key) : null;
    }
    
    /**
     * Check if the node has a property.
     * 
     * @param key The key of the property.
     * @return True if the node has the property, false otherwise.
     */
    public boolean hasProperty(String key) {
        return this.propertiesMap != null && this.propertiesMap.containsKey(key);
    }
    
    /**
     * Remove a property from the map of properties.
     * 
     * @param key The key of the property.
     * @return The removed value, or null if the property was not found.
     */
    public Object removeProperty(String key) {
        return this.propertiesMap != null ? this.propertiesMap.remove(key) : null;
    }
    
    // For Neo4j storage, we'll use a flattened property structure
    // These fields will be directly stored in Neo4j
    
    /**
     * This method is called before saving to Neo4j to convert the properties map to JSON
     * We need to handle this carefully to ensure we don't lose data
     */
    @JsonIgnore
    public String getPropertyJson() {
        if (propertiesMap == null || propertiesMap.isEmpty()) {
            return "{}";
        }
        
        StringBuilder json = new StringBuilder("{");
        boolean first = true;
        
        for (Map.Entry<String, Object> entry : propertiesMap.entrySet()) {
            if (!first) {
                json.append(",");
            }
            
            String key = entry.getKey().replace("\"", "\\\"");
            Object value = entry.getValue();
            
            json.append("\"").append(key).append("\":");
            
            if (value == null) {
                json.append("null");
            } else if (value instanceof String) {
                String strValue = ((String) value).replace("\"", "\\\"");
                json.append("\"").append(strValue).append("\"");
            } else if (value instanceof Number || value instanceof Boolean) {
                json.append(value);
            } else {
                // Convert to string for any other type
                String strValue = value.toString().replace("\"", "\\\"");
                json.append("\"").append(strValue).append("\"");
            }
            
            first = false;
        }
        
        json.append("}");
        return json.toString();
    }
    
    /**
     * This method is called after loading from Neo4j to convert the JSON back to properties map
     */
    @JsonIgnore
    public void setPropertyJson(String json) {
        this.propertyJson = json;
        
        // We'll use a simple approach to parse the JSON since it's a flat structure
        if (json == null || json.trim().isEmpty() || json.equals("{}")) {
            return;
        }
        
        if (propertiesMap == null) {
            propertiesMap = new HashMap<>();
        }
        
        // This is a simplistic parser just to demonstrate the concept
        // In a real app, you'd use a proper JSON parser like Jackson or Gson
        try {
            String content = json.trim();
            if (content.startsWith("{")) content = content.substring(1);
            if (content.endsWith("}")) content = content.substring(0, content.length() - 1);
            
            if (content.trim().isEmpty()) {
                return;
            }
            
            // Split by commas that are not inside quotes
            String[] pairs = content.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
            
            for (String pair : pairs) {
                String[] keyValue = pair.split(":");
                if (keyValue.length == 2) {
                    String key = keyValue[0].trim();
                    String value = keyValue[1].trim();
                    
                    // Remove quotes from key
                    if (key.startsWith("\"") && key.endsWith("\"")) {
                        key = key.substring(1, key.length() - 1);
                    }
                    
                    // Parse value based on type
                    if (value.equals("null")) {
                        propertiesMap.put(key, null);
                    } else if (value.startsWith("\"") && value.endsWith("\"")) {
                        // String value
                        propertiesMap.put(key, value.substring(1, value.length() - 1));
                    } else if (value.equals("true") || value.equals("false")) {
                        // Boolean value
                        propertiesMap.put(key, Boolean.parseBoolean(value));
                    } else {
                        try {
                            // Try to parse as a number
                            if (value.contains(".")) {
                                propertiesMap.put(key, Double.parseDouble(value));
                            } else {
                                propertiesMap.put(key, Long.parseLong(value));
                            }
                        } catch (NumberFormatException e) {
                            // Fallback to string
                            propertiesMap.put(key, value);
                        }
                    }
                }
            }
        } catch (Exception e) {
            // If anything goes wrong, reset the properties map
            propertiesMap.clear();
        }
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
                ", properties=" + propertiesMap +
                '}';
    }
}