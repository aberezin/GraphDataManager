package com.graphapp.model.graph;

import org.springframework.data.neo4j.core.schema.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Node entity class for Neo4j graph database.
 */
@org.springframework.data.neo4j.core.schema.Node("Node")
public class Node {

    @Id
    @GeneratedValue
    private Long id;

    @Property("label")
    private String label;

    @Property("type")
    private String type;

    @DynamicProperties
    private Map<String, Object> properties = new HashMap<>();

    @Relationship(type = "RELATED_TO", direction = Relationship.Direction.OUTGOING)
    private Set<Relationship> outgoingRelationships = new HashSet<>();

    @Relationship(type = "RELATED_TO", direction = Relationship.Direction.INCOMING)
    private Set<Relationship> incomingRelationships = new HashSet<>();

    /**
     * Default constructor.
     */
    public Node() {
    }

    /**
     * Parameterized constructor.
     * @param label Label of the node
     * @param type Type of the node
     */
    public Node(String label, String type) {
        this.label = label;
        this.type = type;
    }

    /**
     * Parameterized constructor with properties.
     * @param label Label of the node
     * @param type Type of the node
     * @param properties Properties of the node
     */
    public Node(String label, String type, Map<String, Object> properties) {
        this.label = label;
        this.type = type;
        this.properties = properties;
    }

    // Getters and Setters

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

    public Set<Relationship> getOutgoingRelationships() {
        return outgoingRelationships;
    }

    public void setOutgoingRelationships(Set<Relationship> outgoingRelationships) {
        this.outgoingRelationships = outgoingRelationships;
    }

    public Set<Relationship> getIncomingRelationships() {
        return incomingRelationships;
    }

    public void setIncomingRelationships(Set<Relationship> incomingRelationships) {
        this.incomingRelationships = incomingRelationships;
    }

    /**
     * Add a property to this node.
     * @param key The property key
     * @param value The property value
     */
    public void addProperty(String key, Object value) {
        properties.put(key, value);
    }

    /**
     * Remove a property from this node.
     * @param key The property key
     */
    public void removeProperty(String key) {
        properties.remove(key);
    }

    /**
     * Add an outgoing relationship from this node to another node.
     * @param relationship The relationship to add
     */
    public void addOutgoingRelationship(Relationship relationship) {
        outgoingRelationships.add(relationship);
    }

    /**
     * Add an incoming relationship to this node from another node.
     * @param relationship The relationship to add
     */
    public void addIncomingRelationship(Relationship relationship) {
        incomingRelationships.add(relationship);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        return id != null ? id.equals(node.id) : node.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}