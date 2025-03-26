package com.graphapp.service;

import com.graphapp.model.relational.Project;
import com.graphapp.model.relational.User;
import com.graphapp.repository.relational.ProjectRepository;
import com.graphapp.repository.relational.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Service for managing relational data (users and projects).
 */
@Service
public class RelationalDataService {
    
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    
    /**
     * Constructor for RelationalDataService.
     * 
     * @param userRepository The user repository.
     * @param projectRepository The project repository.
     */
    @Autowired
    public RelationalDataService(UserRepository userRepository, ProjectRepository projectRepository) {
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
    }
    
    /**
     * Get all users.
     * 
     * @return The list of users.
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    /**
     * Get a user by ID.
     * 
     * @param id The ID of the user.
     * @return An Optional containing the user if found.
     */
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
    
    /**
     * Create a new user.
     * 
     * @param user The user to create.
     * @return The created user.
     */
    @Transactional
    public User createUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already exists: " + user.getUsername());
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists: " + user.getEmail());
        }
        return userRepository.save(user);
    }
    
    /**
     * Update a user.
     * 
     * @param id The ID of the user to update.
     * @param userDetails The updated user details.
     * @return The updated user.
     * @throws RuntimeException if the user is not found.
     */
    @Transactional
    public User updateUser(Long id, User userDetails) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    if (userDetails.getUsername() != null && 
                        !userDetails.getUsername().equals(existingUser.getUsername())) {
                        if (userRepository.existsByUsername(userDetails.getUsername())) {
                            throw new RuntimeException("Username already exists: " + userDetails.getUsername());
                        }
                        existingUser.setUsername(userDetails.getUsername());
                    }
                    
                    if (userDetails.getEmail() != null && 
                        !userDetails.getEmail().equals(existingUser.getEmail())) {
                        if (userRepository.existsByEmail(userDetails.getEmail())) {
                            throw new RuntimeException("Email already exists: " + userDetails.getEmail());
                        }
                        existingUser.setEmail(userDetails.getEmail());
                    }
                    
                    if (userDetails.getFirstName() != null) {
                        existingUser.setFirstName(userDetails.getFirstName());
                    }
                    
                    if (userDetails.getLastName() != null) {
                        existingUser.setLastName(userDetails.getLastName());
                    }
                    
                    return userRepository.save(existingUser);
                })
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }
    
    /**
     * Delete a user.
     * 
     * @param id The ID of the user to delete.
     * @throws RuntimeException if the user is not found.
     */
    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        userRepository.delete(user);
    }
    
    /**
     * Find a user by username.
     * 
     * @param username The username.
     * @return An Optional containing the user if found.
     */
    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    /**
     * Find a user by email.
     * 
     * @param email The email.
     * @return An Optional containing the user if found.
     */
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    /**
     * Search users by various criteria.
     * 
     * @param query The search query.
     * @return The list of users.
     */
    public List<User> searchUsers(String query) {
        return userRepository.searchUsers(query);
    }
    
    /**
     * Get all projects.
     * 
     * @return The list of projects.
     */
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }
    
    /**
     * Get a project by ID.
     * 
     * @param id The ID of the project.
     * @return An Optional containing the project if found.
     */
    public Optional<Project> getProjectById(Long id) {
        return projectRepository.findById(id);
    }
    
    /**
     * Create a new project.
     * 
     * @param project The project to create.
     * @return The created project.
     */
    @Transactional
    public Project createProject(Project project) {
        if (project.getUser() != null && project.getUser().getId() != null) {
            User user = userRepository.findById(project.getUser().getId())
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + project.getUser().getId()));
            project.setUser(user);
        }
        
        project.setCreatedAt(LocalDateTime.now());
        project.setUpdatedAt(LocalDateTime.now());
        
        return projectRepository.save(project);
    }
    
    /**
     * Update a project.
     * 
     * @param id The ID of the project to update.
     * @param projectDetails The updated project details.
     * @return The updated project.
     * @throws RuntimeException if the project is not found.
     */
    @Transactional
    public Project updateProject(Long id, Project projectDetails) {
        return projectRepository.findById(id)
                .map(existingProject -> {
                    if (projectDetails.getName() != null) {
                        existingProject.setName(projectDetails.getName());
                    }
                    
                    if (projectDetails.getDescription() != null) {
                        existingProject.setDescription(projectDetails.getDescription());
                    }
                    
                    if (projectDetails.getUser() != null && projectDetails.getUser().getId() != null) {
                        User user = userRepository.findById(projectDetails.getUser().getId())
                                .orElseThrow(() -> new RuntimeException("User not found with id: " + projectDetails.getUser().getId()));
                        existingProject.setUser(user);
                    }
                    
                    existingProject.setUpdatedAt(LocalDateTime.now());
                    
                    return projectRepository.save(existingProject);
                })
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + id));
    }
    
    /**
     * Delete a project.
     * 
     * @param id The ID of the project to delete.
     * @throws RuntimeException if the project is not found.
     */
    @Transactional
    public void deleteProject(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + id));
        projectRepository.delete(project);
    }
    
    /**
     * Find projects by user ID.
     * 
     * @param userId The user ID.
     * @return The list of projects.
     */
    public List<Project> findProjectsByUserId(Long userId) {
        return projectRepository.findByUserId(userId);
    }
    
    /**
     * Search projects by name or description.
     * 
     * @param query The search query.
     * @return The list of projects.
     */
    public List<Project> searchProjects(String query) {
        return projectRepository.searchProjects(query);
    }
    
    /**
     * Get recent projects up to the given limit.
     * 
     * @param limit The maximum number of projects to return.
     * @return The list of projects.
     */
    public List<Project> getRecentProjects(int limit) {
        return projectRepository.findRecentProjects(limit);
    }
    
    /**
     * Get statistics about users and projects.
     * 
     * @return A map containing various statistics.
     */
    public Map<String, Object> getStatistics() {
        Map<String, Object> statistics = new HashMap<>();
        
        // User statistics
        statistics.put("totalUsers", userRepository.count());
        statistics.put("usersWithProjects", userRepository.findUsersWithProjects().size());
        
        // Project statistics
        statistics.put("totalProjects", projectRepository.count());
        
        // Projects per user
        Map<Long, Long> projectsPerUser = new HashMap<>();
        projectRepository.countProjectsByUser().forEach(result -> {
            Long userId = (Long) result[0];
            Long count = (Long) result[1];
            projectsPerUser.put(userId, count);
        });
        statistics.put("projectsPerUser", projectsPerUser);
        
        return statistics;
    }
}