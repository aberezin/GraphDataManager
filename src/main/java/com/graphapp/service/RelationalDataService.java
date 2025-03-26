package com.graphapp.service;

import com.graphapp.model.relational.Project;
import com.graphapp.model.relational.User;
import com.graphapp.repository.relational.ProjectRepository;
import com.graphapp.repository.relational.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
    
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    @Transactional
    public User createUser(User user) {
        // Validation logic
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        
        // Check for duplicate username or email
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Username already exists: " + user.getUsername());
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already exists: " + user.getEmail());
        }
        
        return userRepository.save(user);
    }
    
    @Transactional
    public Optional<User> updateUser(Long id, User userDetails) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    // Update only provided fields
                    if (userDetails.getUsername() != null 
                            && !userDetails.getUsername().equals(existingUser.getUsername())) {
                        if (userRepository.existsByUsername(userDetails.getUsername())) {
                            throw new IllegalArgumentException("Username already exists: " + userDetails.getUsername());
                        }
                        existingUser.setUsername(userDetails.getUsername());
                    }
                    
                    if (userDetails.getEmail() != null 
                            && !userDetails.getEmail().equals(existingUser.getEmail())) {
                        if (userRepository.existsByEmail(userDetails.getEmail())) {
                            throw new IllegalArgumentException("Email already exists: " + userDetails.getEmail());
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
                });
    }
    
    @Transactional
    public boolean deleteUser(Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    userRepository.delete(user);
                    return true;
                })
                .orElse(false);
    }
    
    public List<User> searchUsers(String query) {
        return userRepository.searchUsers(query);
    }
    
    // Project operations
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }
    
    public Optional<Project> getProjectById(Long id) {
        return projectRepository.findById(id);
    }
    
    public List<Project> getProjectsByUserId(Long userId) {
        // Validate user exists
        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("User not found: " + userId);
        }
        return projectRepository.findByUserId(userId);
    }
    
    @Transactional
    public Project createProject(Project project) {
        // Validation logic
        if (project.getName() == null || project.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Project name cannot be empty");
        }
        
        // Validate user exists if provided
        if (project.getUser() != null && project.getUser().getId() != null) {
            Long userId = project.getUser().getId();
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));
            project.setUser(user);
        }
        
        // Set timestamps
        LocalDateTime now = LocalDateTime.now();
        project.setCreatedAt(now);
        project.setUpdatedAt(now);
        
        return projectRepository.save(project);
    }
    
    @Transactional
    public Optional<Project> updateProject(Long id, Project projectDetails) {
        return projectRepository.findById(id)
                .map(existingProject -> {
                    // Update only provided fields
                    if (projectDetails.getName() != null) {
                        existingProject.setName(projectDetails.getName());
                    }
                    
                    if (projectDetails.getDescription() != null) {
                        existingProject.setDescription(projectDetails.getDescription());
                    }
                    
                    // Update user if provided
                    if (projectDetails.getUser() != null && projectDetails.getUser().getId() != null) {
                        Long userId = projectDetails.getUser().getId();
                        User user = userRepository.findById(userId)
                                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));
                        existingProject.setUser(user);
                    }
                    
                    // Update timestamp
                    existingProject.setUpdatedAt(LocalDateTime.now());
                    
                    return projectRepository.save(existingProject);
                });
    }
    
    @Transactional
    public boolean deleteProject(Long id) {
        return projectRepository.findById(id)
                .map(project -> {
                    projectRepository.delete(project);
                    return true;
                })
                .orElse(false);
    }
    
    public List<Project> searchProjects(String query) {
        return projectRepository.searchProjects(query);
    }
    
    public List<Project> getRecentProjects() {
        return projectRepository.findRecentProjects();
    }
}