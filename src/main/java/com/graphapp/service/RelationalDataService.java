package com.graphapp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.graphapp.model.relational.Project;
import com.graphapp.model.relational.User;
import com.graphapp.repository.relational.ProjectRepository;
import com.graphapp.repository.relational.UserRepository;

/**
 * Service for managing relational data (users and projects).
 */
@Service
public class RelationalDataService {
    
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    
    @Autowired
    public RelationalDataService(UserRepository userRepository, ProjectRepository projectRepository) {
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
    }
    
    // User operations
    
    /**
     * Get all users.
     * 
     * @return A list of all users.
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    /**
     * Get a user by ID.
     * 
     * @param id The ID of the user.
     * @return The user, or null if not found.
     */
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
    
    /**
     * Create a new user.
     * 
     * @param user The user to create.
     * @return The created user.
     */
    @Transactional
    public User createUser(User user) {
        // Validation
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        
        // Check for duplicate username or email
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }
        
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        
        return userRepository.save(user);
    }
    
    /**
     * Update an existing user.
     * 
     * @param id The ID of the user to update.
     * @param userDetails The updated user details.
     * @return The updated user, or null if not found.
     */
    @Transactional
    public User updateUser(Long id, User userDetails) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            
            // Update username if provided and not duplicate
            if (userDetails.getUsername() != null && !userDetails.getUsername().equals(user.getUsername())) {
                if (userRepository.existsByUsername(userDetails.getUsername())) {
                    throw new IllegalArgumentException("Username already exists");
                }
                user.setUsername(userDetails.getUsername());
            }
            
            // Update email if provided and not duplicate
            if (userDetails.getEmail() != null && !userDetails.getEmail().equals(user.getEmail())) {
                if (userRepository.existsByEmail(userDetails.getEmail())) {
                    throw new IllegalArgumentException("Email already exists");
                }
                user.setEmail(userDetails.getEmail());
            }
            
            // Update other fields if provided
            if (userDetails.getFirstName() != null) {
                user.setFirstName(userDetails.getFirstName());
            }
            
            if (userDetails.getLastName() != null) {
                user.setLastName(userDetails.getLastName());
            }
            
            return userRepository.save(user);
        }
        return null;
    }
    
    /**
     * Delete a user by ID.
     * 
     * @param id The ID of the user to delete.
     * @return True if the user was deleted, false otherwise.
     */
    @Transactional
    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    /**
     * Search for users by username, email, first name, or last name.
     * 
     * @param query The search query.
     * @return A list of users matching the search criteria.
     */
    public List<User> searchUsers(String query) {
        return userRepository.findByUsernameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
                query, query, query, query);
    }
    
    // Project operations
    
    /**
     * Get all projects.
     * 
     * @return A list of all projects.
     */
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }
    
    /**
     * Get a project by ID.
     * 
     * @param id The ID of the project.
     * @return The project, or null if not found.
     */
    public Project getProjectById(Long id) {
        return projectRepository.findById(id).orElse(null);
    }
    
    /**
     * Get projects by user ID.
     * 
     * @param userId The ID of the user.
     * @return A list of projects belonging to the user.
     */
    public List<Project> getProjectsByUserId(Long userId) {
        return projectRepository.findByUserId(userId);
    }
    
    /**
     * Create a new project.
     * 
     * @param project The project to create.
     * @return The created project.
     */
    @Transactional
    public Project createProject(Project project) {
        // Validation
        if (project.getName() == null || project.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Project name cannot be empty");
        }
        
        if (project.getUser() == null || project.getUser().getId() == null) {
            throw new IllegalArgumentException("Project must have a user");
        }
        
        // Ensure the user exists
        User user = userRepository.findById(project.getUser().getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        
        // Set timestamps
        project.setCreatedAt(LocalDateTime.now());
        project.setUpdatedAt(LocalDateTime.now());
        
        // Set user and save
        project.setUser(user);
        return projectRepository.save(project);
    }
    
    /**
     * Update an existing project.
     * 
     * @param id The ID of the project to update.
     * @param projectDetails The updated project details.
     * @return The updated project, or null if not found.
     */
    @Transactional
    public Project updateProject(Long id, Project projectDetails) {
        Optional<Project> optionalProject = projectRepository.findById(id);
        if (optionalProject.isPresent()) {
            Project project = optionalProject.get();
            
            if (projectDetails.getName() != null) {
                project.setName(projectDetails.getName());
            }
            
            if (projectDetails.getDescription() != null) {
                project.setDescription(projectDetails.getDescription());
            }
            
            // Update timestamp
            project.setUpdatedAt(LocalDateTime.now());
            
            return projectRepository.save(project);
        }
        return null;
    }
    
    /**
     * Delete a project by ID.
     * 
     * @param id The ID of the project to delete.
     * @return True if the project was deleted, false otherwise.
     */
    @Transactional
    public boolean deleteProject(Long id) {
        if (projectRepository.existsById(id)) {
            projectRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    /**
     * Search for projects by name or description.
     * 
     * @param query The search query.
     * @return A list of projects matching the search criteria.
     */
    public List<Project> searchProjects(String query) {
        return projectRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
                query, query);
    }
    
    /**
     * Get recent projects.
     * 
     * @param limit The maximum number of projects to return.
     * @return A list of recent projects.
     */
    public List<Project> getRecentProjects(int limit) {
        return projectRepository.findRecentProjects(limit);
    }
}