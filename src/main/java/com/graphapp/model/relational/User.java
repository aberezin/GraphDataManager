package com.graphapp.model.relational;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a user in the relational database.
 */
@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    @Column(unique = true, nullable = false, length = 50)
    private String username;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Column(unique = true, nullable = false, length = 100)
    private String email;
    
    @Column(name = "first_name", length = 50)
    private String firstName;
    
    @Column(name = "last_name", length = 50)
    private String lastName;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Project> projects = new ArrayList<>();
    
    /**
     * Default constructor.
     */
    public User() {
    }
    
    /**
     * Constructor with username and email.
     * 
     * @param username The username.
     * @param email The email.
     */
    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }
    
    /**
     * Constructor with username, email, first name and last name.
     * 
     * @param username The username.
     * @param email The email.
     * @param firstName The first name.
     * @param lastName The last name.
     */
    public User(String username, String email, String firstName, String lastName) {
        this.username = username;
        this.email = email;
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
     * Get the username.
     * 
     * @return The username.
     */
    public String getUsername() {
        return username;
    }
    
    /**
     * Set the username.
     * 
     * @param username The username.
     */
    public void setUsername(String username) {
        this.username = username;
    }
    
    /**
     * Get the email.
     * 
     * @return The email.
     */
    public String getEmail() {
        return email;
    }
    
    /**
     * Set the email.
     * 
     * @param email The email.
     */
    public void setEmail(String email) {
        this.email = email;
    }
    
    /**
     * Get the first name.
     * 
     * @return The first name.
     */
    public String getFirstName() {
        return firstName;
    }
    
    /**
     * Set the first name.
     * 
     * @param firstName The first name.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    /**
     * Get the last name.
     * 
     * @return The last name.
     */
    public String getLastName() {
        return lastName;
    }
    
    /**
     * Set the last name.
     * 
     * @param lastName The last name.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    /**
     * Get the projects of the user.
     * 
     * @return The projects.
     */
    public List<Project> getProjects() {
        return projects;
    }
    
    /**
     * Set the projects of the user.
     * 
     * @param projects The projects.
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
        projects.add(project);
        project.setUser(this);
    }
    
    /**
     * Remove a project from the user.
     * 
     * @param project The project to remove.
     */
    public void removeProject(Project project) {
        projects.remove(project);
        project.setUser(null);
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