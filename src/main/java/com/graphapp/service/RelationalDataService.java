package com.graphapp.service;

import com.graphapp.model.relational.Project;
import com.graphapp.model.relational.User;
import com.graphapp.repository.relational.ProjectRepository;
import com.graphapp.repository.relational.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service class for handling relational data operations
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
     * Get all users
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Get user by id
     */
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Get user by id with projects
     */
    public Optional<User> getUserWithProjects(Long id) {
        return userRepository.findByIdWithProjects(id);
    }

    /**
     * Get user by username
     */
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Get user by email
     */
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Create a new user
     */
    @Transactional
    public User createUser(User user) {
        if (user.getId() != null) {
            throw new IllegalArgumentException("New user cannot have an ID");
        }
        
        // Check if username is already taken
        if (user.getUsername() != null && userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username is already taken: " + user.getUsername());
        }
        
        // Check if email is already taken
        if (user.getEmail() != null && userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email is already taken: " + user.getEmail());
        }
        
        return userRepository.save(user);
    }

    /**
     * Update an existing user
     */
    @Transactional
    public User updateUser(Long id, User userDetails) {
        return userRepository.findById(id)
            .map(existingUser -> {
                // Check username uniqueness if it's being updated
                if (userDetails.getUsername() != null && !userDetails.getUsername().equals(existingUser.getUsername())) {
                    userRepository.findByUsername(userDetails.getUsername()).ifPresent(u -> {
                        throw new IllegalArgumentException("Username is already taken: " + userDetails.getUsername());
                    });
                    existingUser.setUsername(userDetails.getUsername());
                }
                
                // Check email uniqueness if it's being updated
                if (userDetails.getEmail() != null && !userDetails.getEmail().equals(existingUser.getEmail())) {
                    userRepository.findByEmail(userDetails.getEmail()).ifPresent(u -> {
                        throw new IllegalArgumentException("Email is already taken: " + userDetails.getEmail());
                    });
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
     * Delete a user
     */
    @Transactional
    public void deleteUser(Long id) {
        // First check if user exists
        userRepository.findById(id).orElseThrow(() -> 
            new RuntimeException("User not found with id: " + id));
            
        // Delete user (cascade will handle project deletion if configured)
        userRepository.deleteById(id);
    }

    /**
     * Search users
     */
    public List<User> searchUsers(String query) {
        return userRepository.searchUsers(query);
    }

    /**
     * Get all projects
     */
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    /**
     * Get project by id
     */
    public Optional<Project> getProjectById(Long id) {
        return projectRepository.findById(id);
    }

    /**
     * Get project with user details
     */
    public Optional<Project> getProjectWithUser(Long id) {
        return projectRepository.findByIdWithUser(id);
    }

    /**
     * Get projects by user id
     */
    public List<Project> getProjectsByUserId(Long userId) {
        // First check if user exists
        userRepository.findById(userId).orElseThrow(() -> 
            new RuntimeException("User not found with id: " + userId));
            
        return projectRepository.findByUserId(userId);
    }

    /**
     * Create a new project
     */
    @Transactional
    public Project createProject(Project project) {
        if (project.getId() != null) {
            throw new IllegalArgumentException("New project cannot have an ID");
        }
        
        // Check if user exists
        if (project.getUser() != null && project.getUser().getId() != null) {
            Long userId = project.getUser().getId();
            User user = userRepository.findById(userId).orElseThrow(() -> 
                new RuntimeException("User not found with id: " + userId));
                
            // Set the full user object
            project.setUser(user);
        }
        
        // Set creation and update timestamps
        if (project.getCreatedAt() == null) {
            project.setCreatedAt(LocalDateTime.now());
        }
        
        if (project.getUpdatedAt() == null) {
            project.setUpdatedAt(LocalDateTime.now());
        }
        
        return projectRepository.save(project);
    }

    /**
     * Update an existing project
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
                
                // Check if user is being updated
                if (projectDetails.getUser() != null && projectDetails.getUser().getId() != null) {
                    Long userId = projectDetails.getUser().getId();
                    User user = userRepository.findById(userId).orElseThrow(() -> 
                        new RuntimeException("User not found with id: " + userId));
                        
                    existingProject.setUser(user);
                }
                
                // Update the 'updatedAt' timestamp
                existingProject.setUpdatedAt(LocalDateTime.now());
                
                return projectRepository.save(existingProject);
            })
            .orElseThrow(() -> new RuntimeException("Project not found with id: " + id));
    }

    /**
     * Delete a project
     */
    @Transactional
    public void deleteProject(Long id) {
        // First check if project exists
        projectRepository.findById(id).orElseThrow(() -> 
            new RuntimeException("Project not found with id: " + id));
            
        // Delete project
        projectRepository.deleteById(id);
    }

    /**
     * Search projects
     */
    public List<Project> searchProjects(String query) {
        return projectRepository.searchProjects(query);
    }

    /**
     * Get recent projects
     */
    public List<Project> getRecentProjects(int limit) {
        return projectRepository.findRecentProjects(PageRequest.of(0, limit));
    }
}