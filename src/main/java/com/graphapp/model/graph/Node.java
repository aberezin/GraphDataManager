package com.graphapp.model.graph;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Model class representing a Node in the graph database.
 */
@org.springframework.data.neo4j.core.schema.Node("Node")
public class Node {

    @Id
    @GeneratedValue(UUIDStringGenerator.class)
    private Long id;

    @Property("label")
    private String label;

    @Property("type")
    private String type;

    // Dynamic properties stored as a map
    private Map<String, Object> properties = new HashMap<>();

    // Default constructor required by Neo4j
    public Node() {
    }

    /**
     * Constructor with required fields.
     * @param label Label of the node
     * @param type Type of the node
     */
    public Node(String label, String type) {
        this.label = label;
        this.type = type;
    }

    /**
     * Full constructor.
     * @param id ID of the node
     * @param label Label of the node
     * @param type Type of the node
     * @param properties Properties of the node
     */
    public Node(Long id, String label, String type, Map<String, Object> properties) {
        this.id = id;
        this.label = label;
        this.type = type;
        this.properties = properties != null ? properties : new HashMap<>();
    }

    /**
     * Get the node ID.
     * @return ID of the node
     */
    public Long getId() {
        return id;
    }

    /**
     * Set the node ID.
     * @param id ID of the node
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the node label.
     * @return Label of the node
     */
    public String getLabel() {
        return label;
    }

    /**
     * Set the node label.
     * @param label Label of the node
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Get the node type.
     * @return Type of the node
     */
    public String getType() {
        return type;
    }

    /**
     * Set the node type.
     * @param type Type of the node
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Get the node properties.
     * @return Properties of the node
     */
    public Map<String, Object> getProperties() {
        return properties;
    }

    /**
     * Set the node properties.
     * @param properties Properties of the node
     */
    public void setProperties(Map<String, Object> properties) {
        this.properties = properties != null ? properties : new HashMap<>();
    }

    /**
     * Add a property to the node.
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
     * Remove a property from the node.
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