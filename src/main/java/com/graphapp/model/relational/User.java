package com.graphapp.model.relational;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Model class representing a User in the relational database.
 */
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Project> projects = new ArrayList<>();

    // Default constructor required by JPA
    public User() {
    }

    /**
     * Constructor with required fields.
     * @param username Username of the user
     * @param email Email of the user
     */
    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    /**
     * Full constructor.
     * @param id ID of the user
     * @param username Username of the user
     * @param email Email of the user
     * @param firstName First name of the user
     * @param lastName Last name of the user
     */
    public User(Long id, String username, String email, String firstName, String lastName) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Get the user ID.
     * @return ID of the user
     */
    public Long getId() {
        return id;
    }

    /**
     * Set the user ID.
     * @param id ID of the user
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the username.
     * @return Username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set the username.
     * @param username Username of the user
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get the email.
     * @return Email of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set the email.
     * @param email Email of the user
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Get the first name.
     * @return First name of the user
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Set the first name.
     * @param firstName First name of the user
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Get the last name.
     * @return Last name of the user
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Set the last name.
     * @param lastName Last name of the user
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Get the projects.
     * @return List of projects owned by the user
     */
    public List<Project> getProjects() {
        return projects;
    }

    /**
     * Set the projects.
     * @param projects List of projects owned by the user
     */
    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    /**
     * Add a project to the user.
     * @param project Project to add
     */
    public void addProject(Project project) {
        projects.add(project);
        project.setUser(this);
    }

    /**
     * Remove a project from the user.
     * @param project Project to remove
     */
    public void removeProject(Project project) {
        projects.remove(project);
        project.setUser(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}