package com.graphapp.model.graph;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;
import org.springframework.data.neo4j.core.schema.GeneratedValue;

import java.util.Map;

/**
 * Represents a relationship in the graph database.
 */
@RelationshipProperties
public class Relationship {
    
    @Id
    @GeneratedValue
    private Long id;
    
    @Property("type")
    private String type;
    
    @TargetNode
    private Node source;
    
    @TargetNode
    private Node target;
    
    @Property("properties")
    private Map<String, Object> properties;
    
    /**
     * Default constructor.
     */
    public Relationship() {
    }
    
    /**
     * Constructor with type, source and target.
     * 
     * @param type The type of the relationship.
     * @param source The source node.
     * @param target The target node.
     */
    public Relationship(String type, Node source, Node target) {
        this.type = type;
        this.source = source;
        this.target = target;
    }
    
    /**
     * Constructor with type, source, target and properties.
     * 
     * @param type The type of the relationship.
     * @param source The source node.
     * @param target The target node.
     * @param properties The properties of the relationship.
     */
    public Relationship(String type, Node source, Node target, Map<String, Object> properties) {
        this.type = type;
        this.source = source;
        this.target = target;
        this.properties = properties;
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
     * Get the source node.
     * 
     * @return The source node.
     */
    public Node getSource() {
        return source;
    }
    
    /**
     * Set the source node.
     * 
     * @param source The source node.
     */
    public void setSource(Node source) {
        this.source = source;
    }
    
    /**
     * Get the target node.
     * 
     * @return The target node.
     */
    public Node getTarget() {
        return target;
    }
    
    /**
     * Set the target node.
     * 
     * @param target The target node.
     */
    public void setTarget(Node target) {
        this.target = target;
    }
    
    /**
     * Get the properties of the relationship.
     * 
     * @return The properties.
     */
    public Map<String, Object> getProperties() {
        return properties;
    }
    
    /**
     * Set the properties of the relationship.
     * 
     * @param properties The properties.
     */
    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }
    
    /**
     * Add a property to the relationship.
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
        return "Relationship{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", source=" + (source != null ? source.getId() : null) +
                ", target=" + (target != null ? target.getId() : null) +
                ", properties=" + properties +
                '}';
    }
}