package com.graphapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.graphapp.model.relational.Project;
import com.graphapp.model.relational.User;
import com.graphapp.service.RelationalDataService;

/**
 * REST controller for relational data operations.
 */
@RestController
@RequestMapping("/api/relational")
public class RelationalDataController {
    
    private final RelationalDataService relationalDataService;
    
    @Autowired
    public RelationalDataController(RelationalDataService relationalDataService) {
        this.relationalDataService = relationalDataService;
    }
    
    // User endpoints
    
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = relationalDataService.getAllUsers();
        return ResponseEntity.ok(users);
    }
    
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = relationalDataService.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        try {
            User createdUser = relationalDataService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        try {
            User updatedUser = relationalDataService.updateUser(id, userDetails);
            if (updatedUser != null) {
                return ResponseEntity.ok(updatedUser);
            }
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        boolean deleted = relationalDataService.deleteUser(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/users/search")
    public ResponseEntity<List<User>> searchUsers(@RequestParam String query) {
        List<User> users = relationalDataService.searchUsers(query);
        return ResponseEntity.ok(users);
    }
    
    // Project endpoints
    
    @GetMapping("/projects")
    public ResponseEntity<List<Project>> getAllProjects() {
        List<Project> projects = relationalDataService.getAllProjects();
        return ResponseEntity.ok(projects);
    }
    
    @GetMapping("/projects/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long id) {
        Project project = relationalDataService.getProjectById(id);
        if (project != null) {
            return ResponseEntity.ok(project);
        }
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/users/{userId}/projects")
    public ResponseEntity<List<Project>> getProjectsByUserId(@PathVariable Long userId) {
        List<Project> projects = relationalDataService.getProjectsByUserId(userId);
        return ResponseEntity.ok(projects);
    }
    
    @PostMapping("/projects")
    public ResponseEntity<Project> createProject(@RequestBody Project project) {
        try {
            Project createdProject = relationalDataService.createProject(project);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProject);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/projects/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable Long id, @RequestBody Project projectDetails) {
        try {
            Project updatedProject = relationalDataService.updateProject(id, projectDetails);
            if (updatedProject != null) {
                return ResponseEntity.ok(updatedProject);
            }
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @DeleteMapping("/projects/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        boolean deleted = relationalDataService.deleteProject(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/projects/search")
    public ResponseEntity<List<Project>> searchProjects(@RequestParam String query) {
        List<Project> projects = relationalDataService.searchProjects(query);
        return ResponseEntity.ok(projects);
    }
    
    @GetMapping("/projects/recent")
    public ResponseEntity<List<Project>> getRecentProjects(@RequestParam(defaultValue = "5") int limit) {
        List<Project> projects = relationalDataService.getRecentProjects(limit);
        return ResponseEntity.ok(projects);
    }
}