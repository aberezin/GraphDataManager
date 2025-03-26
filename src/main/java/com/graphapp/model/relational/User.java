package com.graphapp.model.relational;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a User in the relational database.
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
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Project> projects;
    
    /**
     * Default constructor.
     */
    public User() {
        this.projects = new ArrayList<>();
    }
    
    /**
     * Constructor with username and email.
     * 
     * @param username The username.
     * @param email The email.
     */
    public User(String username, String email) {
        this();
        this.username = username;
        this.email = email;
    }
    
    /**
     * Constructor with all fields.
     * 
     * @param username The username.
     * @param email The email.
     * @param firstName The first name.
     * @param lastName The last name.
     */
    public User(String username, String email, String firstName, String lastName) {
        this(username, email);
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    /**
     * Get the ID of the user.
     * 
     * @return The ID.
     */
    public Long getId() {
        return id;
    }
    
    /**
     * Set the ID of the user.
     * 
     * @param id The ID.
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    /**
     * Get the username of the user.
     * 
     * @return The username.
     */
    public String getUsername() {
        return username;
    }
    
    /**
     * Set the username of the user.
     * 
     * @param username The username.
     */
    public void setUsername(String username) {
        this.username = username;
    }
    
    /**
     * Get the email of the user.
     * 
     * @return The email.
     */
    public String getEmail() {
        return email;
    }
    
    /**
     * Set the email of the user.
     * 
     * @param email The email.
     */
    public void setEmail(String email) {
        this.email = email;
    }
    
    /**
     * Get the first name of the user.
     * 
     * @return The first name.
     */
    public String getFirstName() {
        return firstName;
    }
    
    /**
     * Set the first name of the user.
     * 
     * @param firstName The first name.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    /**
     * Get the last name of the user.
     * 
     * @return The last name.
     */
    public String getLastName() {
        return lastName;
    }
    
    /**
     * Set the last name of the user.
     * 
     * @param lastName The last name.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    /**
     * Get the full name of the user.
     * 
     * @return The full name.
     */
    public String getFullName() {
        return (firstName != null ? firstName : "") + 
               (firstName != null && lastName != null ? " " : "") + 
               (lastName != null ? lastName : "");
    }
    
    /**
     * Get the projects of the user.
     * 
     * @return The list of projects.
     */
    public List<Project> getProjects() {
        return projects;
    }
    
    /**
     * Set the projects of the user.
     * 
     * @param projects The list of projects.
     */
    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }
    
    /**
     * Add a project to the user.
     * 
     * @param project The project to add.
     */
    public void addProject(Project project) {
        if (this.projects == null) {
            this.projects = new ArrayList<>();
        }
        this.projects.add(project);
        project.setUser(this);
    }
    
    /**
     * Remove a project from the user.
     * 
     * @param project The project to remove.
     */
    public void removeProject(Project project) {
        if (this.projects != null) {
            this.projects.remove(project);
            project.setUser(null);
        }
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
        return id != null ? id.hashCode() : 0;
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