package com.graphapp.model.graph;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Model class representing a Relationship in the graph database.
 */
@RelationshipProperties
public class Relationship {

    @Id
    @GeneratedValue(UUIDStringGenerator.class)
    private Long id;

    private String type;
    
    // Source node (not annotated, handled by Neo4j)
    private Node source;
    
    // Target node (annotated with @TargetNode)
    @TargetNode
    private Node target;
    
    // Dynamic properties stored as a map
    private Map<String, Object> properties = new HashMap<>();

    // Default constructor required by Neo4j
    public Relationship() {
    }

    /**
     * Constructor with required fields.
     * @param type Type of the relationship
     * @param source Source node of the relationship
     * @param target Target node of the relationship
     */
    public Relationship(String type, Node source, Node target) {
        this.type = type;
        this.source = source;
        this.target = target;
    }

    /**
     * Full constructor.
     * @param id ID of the relationship
     * @param type Type of the relationship
     * @param source Source node of the relationship
     * @param target Target node of the relationship
     * @param properties Properties of the relationship
     */
    public Relationship(Long id, String type, Node source, Node target, Map<String, Object> properties) {
        this.id = id;
        this.type = type;
        this.source = source;
        this.target = target;
        this.properties = properties != null ? properties : new HashMap<>();
    }

    /**
     * Get the relationship ID.
     * @return ID of the relationship
     */
    public Long getId() {
        return id;
    }

    /**
     * Set the relationship ID.
     * @param id ID of the relationship
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the relationship type.
     * @return Type of the relationship
     */
    public String getType() {
        return type;
    }

    /**
     * Set the relationship type.
     * @param type Type of the relationship
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Get the source node of the relationship.
     * @return Source node of the relationship
     */
    public Node getSource() {
        return source;
    }

    /**
     * Set the source node of the relationship.
     * @param source Source node of the relationship
     */
    public void setSource(Node source) {
        this.source = source;
    }

    /**
     * Get the target node of the relationship.
     * @return Target node of the relationship
     */
    public Node getTarget() {
        return target;
    }

    /**
     * Set the target node of the relationship.
     * @param target Target node of the relationship
     */
    public void setTarget(Node target) {
        this.target = target;
    }

    /**
     * Get the relationship properties.
     * @return Properties of the relationship
     */
    public Map<String, Object> getProperties() {
        return properties;
    }

    /**
     * Set the relationship properties.
     * @param properties Properties of the relationship
     */
    public void setProperties(Map<String, Object> properties) {
        this.properties = properties != null ? properties : new HashMap<>();
    }

    /**
     * Add a property to the relationship.
     * @param key Key of the property
     * @param value Value of the property
     */
    public void addProperty(String key, Object value) {
        if (this.properties == null) {
            this.properties = new HashMap<>();
        }
        this.properties.put(key, value);
    }

    /**
     * Get a property value by key.
     * @param key Key of the property
     * @return Value of the property, or null if not found
     */
    public Object getProperty(String key) {
        return this.properties != null ? this.properties.get(key) : null;
    }

    /**
     * Remove a property from the relationship.
     * @param key Key of the property to remove
     */
    public void removeProperty(String key) {
        if (this.properties != null) {
            this.properties.remove(key);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Relationship that = (Relationship) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
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