package com.graphapp.model.graph;

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
    
    @Property("properties")
    private Map<String, Object> properties;
    
    /**
     * Default constructor.
     */
    public Relationship() {
        this.properties = new HashMap<>();
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
        this.properties = properties != null ? properties : new HashMap<>();
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
     * Check if the relationship has a property.
     * 
     * @param key The key of the property.
     * @return True if the relationship has the property, false otherwise.
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
                ", properties=" + properties +
                '}';
    }
}