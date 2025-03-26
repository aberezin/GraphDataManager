package com.graphapp.controller;

import com.graphapp.model.relational.Project;
import com.graphapp.model.relational.User;
import com.graphapp.service.RelationalDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        return ResponseEntity.ok(relationalDataService.getAllUsers());
    }
    
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return relationalDataService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/users/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        return relationalDataService.getUserByUsername(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/users/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        return relationalDataService.getUserByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping("/users")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            User createdUser = relationalDataService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        try {
            return relationalDataService.updateUser(id, userDetails)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        return relationalDataService.deleteUser(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
    
    @GetMapping("/users/search")
    public ResponseEntity<List<User>> searchUsers(@RequestParam String query) {
        return ResponseEntity.ok(relationalDataService.searchUsers(query));
    }
    
    // Project endpoints
    @GetMapping("/projects")
    public ResponseEntity<List<Project>> getAllProjects() {
        return ResponseEntity.ok(relationalDataService.getAllProjects());
    }
    
    @GetMapping("/projects/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long id) {
        return relationalDataService.getProjectById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/projects/user/{userId}")
    public ResponseEntity<?> getProjectsByUserId(@PathVariable Long userId) {
        try {
            List<Project> projects = relationalDataService.getProjectsByUserId(userId);
            return ResponseEntity.ok(projects);
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    @PostMapping("/projects")
    public ResponseEntity<?> createProject(@RequestBody Project project) {
        try {
            Project createdProject = relationalDataService.createProject(project);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProject);
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    @PutMapping("/projects/{id}")
    public ResponseEntity<?> updateProject(@PathVariable Long id, @RequestBody Project projectDetails) {
        try {
            return relationalDataService.updateProject(id, projectDetails)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    @DeleteMapping("/projects/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        return relationalDataService.deleteProject(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
    
    @GetMapping("/projects/search")
    public ResponseEntity<List<Project>> searchProjects(@RequestParam String query) {
        return ResponseEntity.ok(relationalDataService.searchProjects(query));
    }
    
    @GetMapping("/projects/recent")
    public ResponseEntity<List<Project>> getRecentProjects() {
        return ResponseEntity.ok(relationalDataService.getRecentProjects());
    }
}