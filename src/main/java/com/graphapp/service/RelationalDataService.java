package com.graphapp.service;

import com.graphapp.model.relational.User;
import com.graphapp.model.relational.Project;
import com.graphapp.repository.relational.UserRepository;
import com.graphapp.repository.relational.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
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

    // User methods
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public List<User> searchUsers(String query) {
        return userRepository.searchUsers(query);
    }

    // Project methods
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Optional<Project> getProjectById(Long id) {
        return projectRepository.findById(id);
    }

    public List<Project> getProjectsByUserId(Long userId) {
        return projectRepository.findByUserId(userId);
    }

    @Transactional
    public Project saveProject(Project project) {
        // Update timestamps
        if (project.getId() == null) {
            project.setCreatedAt(LocalDateTime.now());
        }
        project.setUpdatedAt(LocalDateTime.now());
        
        // Make sure user exists if it's specified
        if (project.getUser() != null && project.getUser().getId() != null) {
            Optional<User> user = userRepository.findById(project.getUser().getId());
            if (user.isPresent()) {
                project.setUser(user.get());
            } else {
                project.setUser(null);
            }
        }
        
        return projectRepository.save(project);
    }

    @Transactional
    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }

    public List<Project> searchProjects(String query) {
        return projectRepository.searchProjects(query);
    }

    // Complex operations
    @Transactional
    public Project addProjectToUser(Long userId, Project project) {
        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent()) {
            return null;
        }
        
        project.setUser(user.get());
        return saveProject(project);
    }

    @Transactional
    public List<Project> getRecentProjects() {
        return projectRepository.findAllOrderByCreatedAtDesc();
    }
}
