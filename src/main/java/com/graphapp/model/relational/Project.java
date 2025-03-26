package com.graphapp.model.relational;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents a Project in the relational database.
 */
@Entity
@Table(name = "projects")
public class Project {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties("projects")
    private User user;
    
    /**
     * Default constructor.
     */
    public Project() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    /**
     * Constructor with name.
     * 
     * @param name The name of the project.
     */
    public Project(String name) {
        this();
        this.name = name;
    }
    
    /**
     * Constructor with name and description.
     * 
     * @param name The name of the project.
     * @param description The description of the project.
     */
    public Project(String name, String description) {
        this(name);
        this.description = description;
    }
    
    /**
     * Constructor with name, description, and user.
     * 
     * @param name The name of the project.
     * @param description The description of the project.
     * @param user The user associated with the project.
     */
    public Project(String name, String description, User user) {
        this(name, description);
        this.user = user;
    }
    
    /**
     * Get the ID of the project.
     * 
     * @return The ID.
     */
    public Long getId() {
        return id;
    }
    
    /**
     * Set the ID of the project.
     * 
     * @param id The ID.
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    /**
     * Get the name of the project.
     * 
     * @return The name.
     */
    public String getName() {
        return name;
    }
    
    /**
     * Set the name of the project.
     * 
     * @param name The name.
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Get the description of the project.
     * 
     * @return The description.
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * Set the description of the project.
     * 
     * @param description The description.
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * Get the creation date of the project.
     * 
     * @return The creation date.
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    /**
     * Set the creation date of the project.
     * 
     * @param createdAt The creation date.
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    /**
     * Get the update date of the project.
     * 
     * @return The update date.
     */
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    /**
     * Set the update date of the project.
     * 
     * @param updatedAt The update date.
     */
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    /**
     * Get the user associated with the project.
     * 
     * @return The user.
     */
    public User getUser() {
        return user;
    }
    
    /**
     * Set the user associated with the project.
     * 
     * @param user The user.
     */
    public void setUser(User user) {
        this.user = user;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        Project project = (Project) o;
        
        return Objects.equals(id, project.id);
    }
    
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
    
    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", userId=" + (user != null ? user.getId() : null) +
                '}';
    }
}