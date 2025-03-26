package com.graphapp.model.graph;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Represents a node in the graph database
 */
@org.springframework.data.neo4j.core.schema.Node
public class Node {

    @Id
    @GeneratedValue(UUIDStringGenerator.class)
    private Long id;

    private String label;

    private String type;

    private Map<String, Object> properties;

    public Node() {
        // Default constructor required by Neo4j
        this.properties = new HashMap<>();
    }

    public Node(String type) {
        this.type = type;
        this.properties = new HashMap<>();
    }

    public Node(String type, String label) {
        this.type = type;
        this.label = label;
        this.properties = new HashMap<>();
    }

    public Node(String type, String label, Map<String, Object> properties) {
        this.type = type;
        this.label = label;
        this.properties = properties != null ? properties : new HashMap<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    /**
     * Add a property to the node
     */
    public void addProperty(String key, Object value) {
        if (this.properties == null) {
            this.properties = new HashMap<>();
        }
        this.properties.put(key, value);
    }

    /**
     * Get a property value
     */
    public Object getProperty(String key) {
        if (this.properties == null) {
            return null;
        }
        return this.properties.get(key);
    }

    /**
     * Remove a property
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
        Node node = (Node) o;
        return Objects.equals(id, node.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Node{" +
                "id=" + id +
                ", label='" + label + '\'' +
                ", type='" + type + '\'' +
                ", properties=" + properties +
                '}';
    }
}