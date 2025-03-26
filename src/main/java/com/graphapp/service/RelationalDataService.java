package com.graphapp.service;

import com.graphapp.model.relational.Project;
import com.graphapp.model.relational.User;
import com.graphapp.repository.relational.ProjectRepository;
import com.graphapp.repository.relational.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service class for operations related to relational data.
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

    /**
     * Get all users.
     * @return List of all users
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Get a user by ID.
     * @param id ID of the user to find
     * @return Optional containing the user if found
     */
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Create a new user.
     * @param user User to create
     * @return The created user
     */
    @Transactional
    public User createUser(User user) {
        validateUser(user);
        
        // Check if username is already taken
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username is already taken");
        }
        
        // Check if email is already taken
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email is already taken");
        }
        
        return userRepository.save(user);
    }

    /**
     * Update an existing user.
     * @param id ID of the user to update
     * @param userDetails Updated user details
     * @return The updated user
     * @throws RuntimeException if the user does not exist
     */
    @Transactional
    public User updateUser(Long id, User userDetails) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        
        validateUser(userDetails);
        
        // Check if username is already taken by another user
        Optional<User> existingUserWithUsername = userRepository.findByUsername(userDetails.getUsername());
        if (existingUserWithUsername.isPresent() && !existingUserWithUsername.get().getId().equals(id)) {
            throw new IllegalArgumentException("Username is already taken");
        }
        
        // Check if email is already taken by another user
        Optional<User> existingUserWithEmail = userRepository.findByEmail(userDetails.getEmail());
        if (existingUserWithEmail.isPresent() && !existingUserWithEmail.get().getId().equals(id)) {
            throw new IllegalArgumentException("Email is already taken");
        }
        
        user.setUsername(userDetails.getUsername());
        user.setEmail(userDetails.getEmail());
        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        
        return userRepository.save(user);
    }

    /**
     * Delete a user by ID.
     * @param id ID of the user to delete
     * @throws RuntimeException if the user does not exist
     */
    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        
        userRepository.delete(user);
    }

    /**
     * Search users by a query string.
     * @param query Query string to search for
     * @return List of users matching the search query
     */
    public List<User> searchUsers(String query) {
        return userRepository.searchUsers(query);
    }

    /**
     * Get all projects.
     * @return List of all projects
     */
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    /**
     * Get a project by ID.
     * @param id ID of the project to find
     * @return Optional containing the project if found
     */
    public Optional<Project> getProjectById(Long id) {
        return projectRepository.findById(id);
    }

    /**
     * Create a new project.
     * @param project Project to create
     * @return The created project
     * @throws RuntimeException if the project's user does not exist
     */
    @Transactional
    public Project createProject(Project project) {
        validateProject(project);
        
        // Ensure the user exists if specified
        if (project.getUser() != null && project.getUser().getId() != null) {
            User user = userRepository.findById(project.getUser().getId())
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + project.getUser().getId()));
            project.setUser(user);
        }
        
        // Set creation and update timestamps
        LocalDateTime now = LocalDateTime.now();
        project.setCreatedAt(now);
        project.setUpdatedAt(now);
        
        return projectRepository.save(project);
    }

    /**
     * Update an existing project.
     * @param id ID of the project to update
     * @param projectDetails Updated project details
     * @return The updated project
     * @throws RuntimeException if the project or user does not exist
     */
    @Transactional
    public Project updateProject(Long id, Project projectDetails) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + id));
        
        validateProject(projectDetails);
        
        // Ensure the user exists if being updated
        if (projectDetails.getUser() != null && projectDetails.getUser().getId() != null) {
            User user = userRepository.findById(projectDetails.getUser().getId())
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + projectDetails.getUser().getId()));
            project.setUser(user);
        }
        
        project.setName(projectDetails.getName());
        project.setDescription(projectDetails.getDescription());
        project.setUpdatedAt(LocalDateTime.now());
        
        return projectRepository.save(project);
    }

    /**
     * Delete a project by ID.
     * @param id ID of the project to delete
     * @throws RuntimeException if the project does not exist
     */
    @Transactional
    public void deleteProject(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + id));
        
        projectRepository.delete(project);
    }

    /**
     * Search projects by a query string.
     * @param query Query string to search for
     * @return List of projects matching the search query
     */
    public List<Project> searchProjects(String query) {
        return projectRepository.searchProjects(query);
    }

    /**
     * Get projects for a specific user.
     * @param userId ID of the user to get projects for
     * @return List of projects owned by the given user
     */
    public List<Project> getProjectsByUserId(Long userId) {
        return projectRepository.findByUserId(userId);
    }

    /**
     * Get the most recent projects.
     * @param limit Maximum number of projects to return
     * @return List of the most recent projects
     */
    public List<Project> getRecentProjects(int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return projectRepository.findMostRecentProjects(pageable);
    }

    /**
     * Validate a user.
     * @param user User to validate
     * @throws IllegalArgumentException if validation fails
     */
    private void validateUser(User user) {
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        
        if (!isValidEmail(user.getEmail())) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }

    /**
     * Validate a project.
     * @param project Project to validate
     * @throws IllegalArgumentException if validation fails
     */
    private void validateProject(Project project) {
        if (project.getName() == null || project.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Project name cannot be empty");
        }
    }

    /**
     * Check if an email is valid.
     * @param email Email to check
     * @return True if the email is valid, false otherwise
     */
    private boolean isValidEmail(String email) {
        // Simple email validation using regex
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }
}