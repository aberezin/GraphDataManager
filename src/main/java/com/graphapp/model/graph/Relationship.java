package com.graphapp.model.graph;

import org.springframework.data.neo4j.core.schema.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Relationship entity class for Neo4j graph database.
 */
@RelationshipProperties
public class Relationship {

    @Id
    @GeneratedValue
    private Long id;

    @Property("type")
    private String type;

    @TargetNode
    private Node target;

    @DynamicProperties
    private Map<String, Object> properties = new HashMap<>();

    /**
     * Default constructor.
     */
    public Relationship() {
    }

    /**
     * Parameterized constructor.
     * @param type Type of the relationship
     * @param target Target node of the relationship
     */
    public Relationship(String type, Node target) {
        this.type = type;
        this.target = target;
    }

    /**
     * Parameterized constructor with properties.
     * @param type Type of the relationship
     * @param target Target node of the relationship
     * @param properties Properties of the relationship
     */
    public Relationship(String type, Node target, Map<String, Object> properties) {
        this.type = type;
        this.target = target;
        this.properties = properties;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Node getTarget() {
        return target;
    }

    public void setTarget(Node target) {
        this.target = target;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    /**
     * Add a property to this relationship.
     * @param key The property key
     * @param value The property value
     */
    public void addProperty(String key, Object value) {
        properties.put(key, value);
    }

    /**
     * Remove a property from this relationship.
     * @param key The property key
     */
    public void removeProperty(String key) {
        properties.remove(key);
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
}