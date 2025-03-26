package com.graphapp.model.graph;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Represents a relationship between two nodes in the graph database
 */
@RelationshipProperties
public class Relationship {

    @Id
    @GeneratedValue
    private Long id;

    @Property("type")
    private String type;

    // Source node - needs to be handled differently in Neo4j
    private Node source;

    // Target node must be annotated with @TargetNode
    @TargetNode
    private Node target;

    // Dynamic properties stored as a map
    private Map<String, Object> properties = new HashMap<>();

    public Relationship() {
        // Default constructor required by Spring Data Neo4j
    }

    public Relationship(String type, Node source, Node target) {
        this.type = type;
        this.source = source;
        this.target = target;
    }

    public Relationship(String type, Node source, Node target, Map<String, Object> properties) {
        this.type = type;
        this.source = source;
        this.target = target;
        this.properties = properties != null ? properties : new HashMap<>();
    }

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

    public Node getSource() {
        return source;
    }

    public void setSource(Node source) {
        this.source = source;
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

    public void addProperty(String key, Object value) {
        this.properties.put(key, value);
    }

    public Object getProperty(String key) {
        return this.properties.get(key);
    }

    public void removeProperty(String key) {
        this.properties.remove(key);
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