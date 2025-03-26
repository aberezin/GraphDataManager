package com.graphapp.model.graph;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Property;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Represents a node in the graph database
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

    // Dynamic properties stored as a map
    private Map<String, Object> properties = new HashMap<>();

    public Node() {
        // Default constructor required by Spring Data Neo4j
    }

    public Node(String label, String type) {
        this.label = label;
        this.type = type;
    }

    public Node(String label, String type, Map<String, Object> properties) {
        this.label = label;
        this.type = type;
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