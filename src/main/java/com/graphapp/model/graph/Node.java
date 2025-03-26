package com.graphapp.model.graph;

import org.springframework.data.neo4j.core.schema.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Node
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
    
    @Relationship(type = "CONNECTS_TO", direction = Relationship.Direction.OUTGOING)
    private Set<Relationship> outgoingRelationships = new HashSet<>();
    
    @Relationship(type = "CONNECTS_TO", direction = Relationship.Direction.INCOMING)
    private Set<Relationship> incomingRelationships = new HashSet<>();
    
    // Default constructor
    public Node() {
    }
    
    public Node(String label, String type) {
        this.label = label;
        this.type = type;
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
    
    public void addProperty(String key, Object value) {
        this.properties.put(key, value);
    }
    
    public Object getProperty(String key) {
        return this.properties.get(key);
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
    
    // Helper methods
    public void addOutgoingRelationship(Relationship relationship) {
        this.outgoingRelationships.add(relationship);
    }
    
    public void addIncomingRelationship(Relationship relationship) {
        this.incomingRelationships.add(relationship);
    }
}