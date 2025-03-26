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
    
    @Transactional
    public User createUser(User user) {
        validateUser(user);
        
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Username already exists: " + user.getUsername());
        }
        
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already exists: " + user.getEmail());
        }
        
        return userRepository.save(user);
    }
    
    @Transactional
    public User updateUser(Long id, User userDetails) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    // Validate unique fields if they are being changed
                    if (userDetails.getUsername() != null && 
                            !existingUser.getUsername().equals(userDetails.getUsername()) &&
                            userRepository.existsByUsername(userDetails.getUsername())) {
                        throw new IllegalArgumentException("Username already exists: " + userDetails.getUsername());
                    }
                    
                    if (userDetails.getEmail() != null && 
                            !existingUser.getEmail().equals(userDetails.getEmail()) &&
                            userRepository.existsByEmail(userDetails.getEmail())) {
                        throw new IllegalArgumentException("Email already exists: " + userDetails.getEmail());
                    }
                    
                    // Update fields if provided
                    if (userDetails.getUsername() != null) {
                        existingUser.setUsername(userDetails.getUsername());
                    }
                    
                    if (userDetails.getEmail() != null) {
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
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
    }
    
    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
        
        userRepository.delete(user);
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
        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("User not found with id: " + userId);
        }
        
        return projectRepository.findByUserId(userId);
    }
    
    @Transactional
    public Project createProject(Project project) {
        validateProject(project);
        
        if (project.getUser() != null && project.getUser().getId() != null) {
            User user = userRepository.findById(project.getUser().getId())
                    .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + project.getUser().getId()));
            
            project.setUser(user);
        }
        
        return projectRepository.save(project);
    }
    
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
                    
                    // Update user if provided
                    if (projectDetails.getUser() != null && projectDetails.getUser().getId() != null) {
                        User user = userRepository.findById(projectDetails.getUser().getId())
                                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + projectDetails.getUser().getId()));
                        
                        existingProject.setUser(user);
                    }
                    
                    existingProject.setUpdatedAt(LocalDateTime.now());
                    
                    return projectRepository.save(existingProject);
                })
                .orElseThrow(() -> new IllegalArgumentException("Project not found with id: " + id));
    }
    
    @Transactional
    public void deleteProject(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Project not found with id: " + id));
        
        projectRepository.delete(project);
    }
    
    public List<Project> searchProjects(String query) {
        return projectRepository.searchProjects(query);
    }
    
    public List<Project> getRecentProjects() {
        return projectRepository.findRecentProjects();
    }
    
    // Validation methods
    private void validateUser(User user) {
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        
        // Simple email validation
        if (!user.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }
    
    private void validateProject(Project project) {
        if (project.getName() == null || project.getName().isEmpty()) {
            throw new IllegalArgumentException("Project name cannot be empty");
        }
    }
}