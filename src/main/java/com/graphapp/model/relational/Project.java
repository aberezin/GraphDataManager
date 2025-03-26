package com.graphapp.model.relational;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Model class representing a Project in the relational database.
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    // Default constructor required by JPA
    public Project() {
    }

    /**
     * Constructor with required fields.
     * @param name Name of the project
     */
    public Project(String name) {
        this.name = name;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Full constructor.
     * @param id ID of the project
     * @param name Name of the project
     * @param description Description of the project
     * @param createdAt Creation timestamp of the project
     * @param updatedAt Last update timestamp of the project
     * @param user User who owns the project
     */
    public Project(Long id, String name, String description, LocalDateTime createdAt, LocalDateTime updatedAt, User user) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.user = user;
    }

    /**
     * Get the project ID.
     * @return ID of the project
     */
    public Long getId() {
        return id;
    }

    /**
     * Set the project ID.
     * @param id ID of the project
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the project name.
     * @return Name of the project
     */
    public String getName() {
        return name;
    }

    /**
     * Set the project name.
     * @param name Name of the project
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the project description.
     * @return Description of the project
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the project description.
     * @param description Description of the project
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get the creation timestamp.
     * @return Creation timestamp of the project
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Set the creation timestamp.
     * @param createdAt Creation timestamp of the project
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Get the last update timestamp.
     * @return Last update timestamp of the project
     */
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Set the last update timestamp.
     * @param updatedAt Last update timestamp of the project
     */
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * Get the user who owns the project.
     * @return User who owns the project
     */
    public User getUser() {
        return user;
    }

    /**
     * Set the user who owns the project.
     * @param user User who owns the project
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
        return Objects.hash(id);
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