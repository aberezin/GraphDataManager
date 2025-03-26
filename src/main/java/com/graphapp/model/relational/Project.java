package com.graphapp.model.relational;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * Represents a project in the relational database.
 */
@Entity
@Table(name = "projects")
public class Project {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Project name is required")
    @Size(min = 3, max = 100, message = "Project name must be between 3 and 100 characters")
    @Column(nullable = false, length = 100)
    private String name;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
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
        this.name = name;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    /**
     * Constructor with name and description.
     * 
     * @param name The name of the project.
     * @param description The description of the project.
     */
    public Project(String name, String description) {
        this.name = name;
        this.description = description;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    /**
     * Constructor with name, description and user.
     * 
     * @param name The name of the project.
     * @param description The description of the project.
     * @param user The user who owns the project.
     */
    public Project(String name, String description, User user) {
        this.name = name;
        this.description = description;
        this.user = user;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
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
     * Get the creation date and time of the project.
     * 
     * @return The creation date and time.
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    /**
     * Set the creation date and time of the project.
     * 
     * @param createdAt The creation date and time.
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    /**
     * Get the last update date and time of the project.
     * 
     * @return The last update date and time.
     */
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    /**
     * Set the last update date and time of the project.
     * 
     * @param updatedAt The last update date and time.
     */
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    /**
     * Update the last update date and time to now.
     */
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
    
    /**
     * Get the user who owns the project.
     * 
     * @return The user.
     */
    public User getUser() {
        return user;
    }
    
    /**
     * Set the user who owns the project.
     * 
     * @param user The user.
     */
    public void setUser(User user) {
        this.user = user;
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