package com.graphapp.model.graph;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a Relationship in the graph database.
 */
@RelationshipProperties
public class Relationship {
    
    @Id
    @GeneratedValue
    private Long id;
    
    @Property("type")
    private String type;
    
    private GraphNode source;
    
    @TargetNode
    private GraphNode target;
    
    // Use a transient map for properties in memory
    @JsonIgnore
    private transient Map<String, Object> propertiesMap;
    
    // Store properties as a JSON string in Neo4j
    @Property("prop_json")
    private String propertyJson;
    
    /**
     * Default constructor.
     */
    public Relationship() {
        this.propertiesMap = new HashMap<>();
    }
    
    /**
     * Constructor with type, source, and target.
     * 
     * @param type The type of the relationship.
     * @param source The source node.
     * @param target The target node.
     */
    public Relationship(String type, GraphNode source, GraphNode target) {
        this();
        this.type = type;
        this.source = source;
        this.target = target;
    }
    
    /**
     * Constructor with all fields.
     * 
     * @param type The type of the relationship.
     * @param source The source node.
     * @param target The target node.
     * @param properties The map of properties.
     */
    public Relationship(String type, GraphNode source, GraphNode target, Map<String, Object> properties) {
        this.type = type;
        this.source = source;
        this.target = target;
        this.propertiesMap = properties != null ? properties : new HashMap<>();
    }
    
    /**
     * Get the ID of the relationship.
     * 
     * @return The ID.
     */
    public Long getId() {
        return id;
    }
    
    /**
     * Set the ID of the relationship.
     * 
     * @param id The ID.
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    /**
     * Get the type of the relationship.
     * 
     * @return The type.
     */
    public String getType() {
        return type;
    }
    
    /**
     * Set the type of the relationship.
     * 
     * @param type The type.
     */
    public void setType(String type) {
        this.type = type;
    }
    
    /**
     * Get the source node of the relationship.
     * 
     * @return The source node.
     */
    public GraphNode getSource() {
        return source;
    }
    
    /**
     * Set the source node of the relationship.
     * 
     * @param source The source node.
     */
    public void setSource(GraphNode source) {
        this.source = source;
    }
    
    /**
     * Get the target node of the relationship.
     * 
     * @return The target node.
     */
    public GraphNode getTarget() {
        return target;
    }
    
    /**
     * Set the target node of the relationship.
     * 
     * @param target The target node.
     */
    public void setTarget(GraphNode target) {
        this.target = target;
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
     * Check if the relationship has a property.
     * 
     * @param key The key of the property.
     * @return True if the relationship has the property, false otherwise.
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
    
    /**
     * This method is called before saving to Neo4j to convert the properties map to JSON
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
        
        Relationship that = (Relationship) o;
        
        return id != null ? id.equals(that.id) : that.id == null;
    }
    
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
    
    @Override
    public String toString() {
        return "Relationship{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", source=" + (source != null ? source.getId() : null) +
                ", target=" + (target != null ? target.getId() : null) +
                ", properties=" + propertiesMap +
                '}';
    }
}